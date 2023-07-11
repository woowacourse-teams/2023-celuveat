/* eslint-disable no-new */
/* eslint-disable @typescript-eslint/no-explicit-any */
import { useEffect } from 'react';

interface Address {
  address: string;
}

interface Coordinate {
  longitude: number;
  latitude: number;
}

interface MapProps {
  width: string;
  height: string;
  level: number;
  searchWord: Address | Coordinate;
}

function Map({ width, height, level, searchWord }: MapProps) {
  useEffect(() => {
    const container = document.getElementById('map');

    if ('latitude' in searchWord && 'longitude' in searchWord) {
      const options = {
        center: new window.kakao.maps.LatLng(searchWord.longitude, searchWord.latitude),
        level,
      };
      const map = new window.kakao.maps.Map(container, options);
      const position = new window.kakao.maps.LatLng(searchWord.longitude, searchWord.latitude);

      new window.kakao.maps.Marker({ map, position });
    }

    if ('address' in searchWord) {
      const options = {
        center: new window.kakao.maps.LatLng(127.050727, 37.5057482),
        level,
      };
      const map = new window.kakao.maps.Map(container, options);
      const geocoder = new window.kakao.maps.services.Geocoder();

      geocoder.addressSearch(searchWord.address, (result: any, status: any) => {
        if (status === window.kakao.maps.services.Status.OK) {
          const position = new window.kakao.maps.LatLng(result[0].y, result[0].x);

          new window.kakao.maps.Marker({ map, position });
          map.setCenter(position);
        }
      });
    }
  }, [searchWord, level]);

  return <div id="map" style={{ width, height }} />;
}

export default Map;
