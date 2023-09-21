import { shallow } from 'zustand/shallow';
import { useQuery } from '@tanstack/react-query';
import { useEffect } from 'react';
import getQuadrant from '~/utils/getQuadrant';
import OverlayMarker from './OverlayMarker';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';
import { getRestaurants } from '~/api/restaurant';
import type { RestaurantListData } from '~/@types/api.types';
import type { Coordinate } from '~/@types/map.types';

interface OverlayMarkerListProps {
  center: Coordinate;
  map?: google.maps.Map;
  setMapLoadingState: (state: boolean) => void;
}

function OverlayMarkerList({ center, map, setMapLoadingState }: OverlayMarkerListProps) {
  const [boundary, celebId, currentPage, restaurantCategory, sort] = useRestaurantsQueryStringState(
    state => [state.boundary, state.celebId, state.currentPage, state.restaurantCategory, state.sort],
    shallow,
  );

  const { data, isFetching } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', boundary, celebId, restaurantCategory, currentPage, sort],
    queryFn: () => getRestaurants({ boundary, celebId, category: restaurantCategory, page: currentPage, sort }),
    keepPreviousData: true,
  });

  useEffect(() => {
    if (isFetching) {
      setMapLoadingState(true);
      return;
    }
    setMapLoadingState(false);
  }, [isFetching]);

  return (
    map &&
    data?.content?.map(({ celebs, ...restaurant }) => (
      <OverlayMarker
        key={restaurant.id}
        map={map}
        restaurant={restaurant}
        celeb={celebs[0]}
        quadrant={getQuadrant(center, { lat: restaurant.lat, lng: restaurant.lng })}
      />
    ))
  );
}

export default OverlayMarkerList;
