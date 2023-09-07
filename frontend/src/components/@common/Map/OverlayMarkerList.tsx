import { shallow } from 'zustand/shallow';
import { useQuery } from '@tanstack/react-query';
import { getRestaurants } from '~/api';
import getQuadrant from '~/utils/getQuadrant';
import OverlayMarker from './OverlayMarker';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';
import type { RestaurantListData } from '~/@types/api.types';
import type { Coordinate } from '~/@types/map.types';

interface OverlayMarkerListProps {
  center: Coordinate;
  hoveredId: number | null;
  map?: google.maps.Map;
}

function OverlayMarkerList({ center, hoveredId, map }: OverlayMarkerListProps) {
  const [boundary, celebId, currentPage, restaurantCategory] = useRestaurantsQueryStringState(
    state => [state.boundary, state.celebId, state.currentPage, state.restaurantCategory],
    shallow,
  );

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
        isRestaurantHovered={restaurant.id === hoveredId}
      />
    ))
  );
}

export default OverlayMarkerList;
