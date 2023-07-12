/* eslint-disable no-new */
/* eslint-disable @typescript-eslint/no-explicit-any */
import { useEffect } from 'react';

interface Coordinate {
  longitude: number;
  latitude: number;
}

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
      center: new window.kakao.maps.LatLng(mainPosition.longitude, mainPosition.latitude),
      level,
    };
    const map = new window.kakao.maps.Map(container, options);

    markers.forEach(marker => {
      const position = new window.kakao.maps.LatLng(marker.longitude, marker.latitude);

      new window.kakao.maps.Marker({ map, position });
    });
  }, [level, mainPosition, markers]);

  return <div id="map" style={{ width, height }} />;
}

export default Map;
