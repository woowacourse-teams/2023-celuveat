/* eslint-disable no-new */
/* eslint-disable @typescript-eslint/no-explicit-any */
import { useEffect } from 'react';
import { Coordinate } from '~/@types/map.types';

interface MapProps {
  width: string;
  height: string;
  level: number;
  mainPosition: Coordinate;
  markers: Coordinate[];
}

function Map({ width, height, level, mainPosition, markers }: MapProps) {
  useEffect(() => {
    const container = document.getElementById('map');
    const options = {
      center: new window.kakao.maps.LatLng(mainPosition.latitude, mainPosition.longitude),
      level,
    };
    const map = new window.kakao.maps.Map(container, options);

    markers.forEach(marker => {
      const position = new window.kakao.maps.LatLng(marker.latitude, marker.longitude);

      new window.kakao.maps.Marker({ map, position });
    });
  }, [level, mainPosition, markers]);

  return <div id="map" style={{ width, height }} />;
}

export default Map;
