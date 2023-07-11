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
    const container = document.getElementById('map'); // 지도를 담을 영역의 DOM 레퍼런스

    if ('latitude' in searchWord && 'longitude' in searchWord) {
      const options = {
        // 지도를 생성할 때 필요한 기본 옵션
        center: new window.kakao.maps.LatLng(searchWord.longitude, searchWord.latitude), // 지도의 중심좌표. 디폴트 선릉캠퍼스
        level, // 지도의 레벨(확대, 축소 정도)
      };

      const map = new window.kakao.maps.Map(container, options);
      const position = new window.kakao.maps.LatLng(searchWord.longitude, searchWord.latitude);

      new window.kakao.maps.Marker({ map, position });
    }

    if ('address' in searchWord) {
      const options = {
        // 지도를 생성할 때 필요한 기본 옵션
        center: new window.kakao.maps.LatLng(127.050727, 37.5057482), // 지도의 중심좌표. 디폴트 선릉캠퍼스
        level, // 지도의 레벨(확대, 축소 정도)
      };
      const map = new window.kakao.maps.Map(container, options);
      const geocoder = new window.kakao.maps.services.Geocoder();

      geocoder.addressSearch(searchWord.address, (result: any, status: any) => {
        // 정상적으로 검색이 완료됐으면
        if (status === window.kakao.maps.services.Status.OK) {
          const position = new window.kakao.maps.LatLng(result[0].y, result[0].x);

          // 결과값으로 받은 위치를 마커로 표시합니다
          new window.kakao.maps.Marker({ map, position });

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          map.setCenter(position);
        }
      });
    }
  }, [searchWord, level]);

  return <div id="map" style={{ width, height }} />;
}

export default Map;
