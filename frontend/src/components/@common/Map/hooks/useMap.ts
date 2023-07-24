/* eslint-disable react-hooks/exhaustive-deps */
import { useEffect, useRef, useState } from 'react';

interface UseDrawMapProps {
  zoom: number;
  center: google.maps.LatLngLiteral;
  onIdle?: (map: google.maps.Map) => void;
  onClick?: (e: google.maps.MapMouseEvent) => void;
}

const useMap = ({ center, zoom, onClick, onIdle }: UseDrawMapProps) => {
  const ref = useRef<HTMLDivElement>(null);
  const [map, setMap] = useState<google.maps.Map>(null);

  useEffect(() => {
    if (ref.current && !map) {
      const newMap = new window.google.maps.Map(ref.current, {
        center,
        zoom,
        disableDefaultUI: true,
        gestureHandling: 'greedy',
      });
      setMap(newMap);
      const transitLayer = new google.maps.TransitLayer();

      transitLayer.setMap(map);
    }
  }, [ref]);

  useEffect(() => {
    if (map) map.panTo(center);
  }, [center]);

  useEffect(() => {
    if (map) {
      ['click', 'idle'].forEach(eventName => google.maps.event.clearListeners(map, eventName));

      if (onClick) map.addListener('click', onClick);
      if (onIdle) map.addListener('idle', () => onIdle(map));
    }
  }, [map, onClick, onIdle]);

  return { ref, map };
};

export default useMap;
