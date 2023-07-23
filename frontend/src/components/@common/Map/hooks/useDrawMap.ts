import { useEffect, useState } from 'react';

interface UseDrawMapProps {
  mapRef: React.MutableRefObject<HTMLDivElement>;
  center: google.maps.LatLngLiteral;
  zoom: number;
}

const useDrawMap = ({ mapRef, center, zoom }: UseDrawMapProps) => {
  const [googleMap, setGoogleMap] = useState<google.maps.Map>(null);

  // center를 중심으로 지도 그리기 로직
  useEffect(() => {
    const newMap = new window.google.maps.Map(mapRef.current, { center, zoom });

    setGoogleMap(newMap);
  }, [mapRef, center, zoom]);

  return { googleMap };
};

export default useDrawMap;
