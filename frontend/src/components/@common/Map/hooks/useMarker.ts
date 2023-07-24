/* eslint-disable react-hooks/exhaustive-deps */
import { useEffect, useState } from 'react';
import { RestaurantData } from '~/@types/api.types';
import { Coordinate } from '~/@types/map.types';

interface UseMarkerProps {
  map: google.maps.Map;
  restaurants: RestaurantData[];
  clickMarker: ({ lat, lng }: Coordinate) => void;
}

const useMarker = ({ map, restaurants, clickMarker }: UseMarkerProps) => {
  const [markers, setMarkers] = useState([]);
  // 마커를 지도에 표시하는 로직
  useEffect(() => {
    if (!map) return;

    const newMarkers = restaurants.map(restaurant => {
      const { lat, lng } = restaurant;
      const newMarker = new window.google.maps.Marker({
        position: { lat, lng },
        map,
      });
      newMarker.setMap(map);

      newMarker.addListener('click', () => {
        clickMarker({ lat, lng });
      });

      return newMarker;
    });

    setMarkers(newMarkers);
  }, [map, restaurants]);

  return { markers };
};

export default useMarker;
