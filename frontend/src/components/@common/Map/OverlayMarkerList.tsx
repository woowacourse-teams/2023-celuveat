import { shallow } from 'zustand/shallow';
import { useQuery } from '@tanstack/react-query';
import getQuadrant from '~/utils/getQuadrant';
import OverlayMarker from './OverlayMarker';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';
import type { RestaurantListData } from '~/@types/api.types';
import type { Coordinate } from '~/@types/map.types';
import useRestaurant from '~/hooks/server/useRestaurant';

interface OverlayMarkerListProps {
  center: Coordinate;
  map?: google.maps.Map;
}

function OverlayMarkerList({ center, map }: OverlayMarkerListProps) {
  const [boundary, celebId, currentPage, restaurantCategory] = useRestaurantsQueryStringState(
    state => [state.boundary, state.celebId, state.currentPage, state.restaurantCategory],
    shallow,
  );
  const { getRestaurants } = useRestaurant();

  const { data, isLoading } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', boundary, celebId, restaurantCategory, currentPage],
    queryFn: () => getRestaurants({ boundary, celebId, category: restaurantCategory, page: currentPage }),
  });

  if (isLoading) return <div>asd</div>;

  return (
    map &&
    data.content?.map(({ celebs, ...restaurant }) => (
      <OverlayMarker
        map={map}
        restaurant={restaurant}
        celeb={celebs[0]}
        quadrant={getQuadrant(center, { lat: restaurant.lat, lng: restaurant.lng })}
      />
    ))
  );
}

export default OverlayMarkerList;
