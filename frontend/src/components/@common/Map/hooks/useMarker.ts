import { useEffect, useState } from 'react';
import { RestaurantData } from '~/@types/api.types';

interface UseMarkerProps {
  map: google.maps.Map;
  restaurants: RestaurantData[];
}

const useMarker = ({ map, restaurants }: UseMarkerProps) => {
  const [markers, setMarkers] = useState([]);
  // 마커를 지도에 표시하는 로직
  useEffect(() => {
    if (!map) return;

    const newMarkers = restaurants.map(restaurant => {
      const newMarker = new window.google.maps.Marker({
        position: { lat: restaurant.lat, lng: restaurant.lng },
        map,
      });
      newMarker.setMap(map);

      return newMarker;
    });

    setMarkers(newMarkers);
  }, [map, restaurants]);

  return { markers };
};

export default useMarker;
