/* eslint-disable no-new */
/* eslint-disable @typescript-eslint/no-explicit-any */
import { useEffect, useRef, useState } from 'react';
import { Coordinate } from '~/@types/map.types';
import useCreateMap from './hooks/useCreateMap';

interface MapProps {
  width: string;
  height: string;
  level: number;
  mainPosition: Coordinate;
  markers: Coordinate[];
}

function Map({ width, height, level, mainPosition, markers }: MapProps) {
  const container = useRef();
  const { kakaoMap } = useCreateMap({ mainPosition, level, container });
  const { latitude, longitude } = mainPosition;
  const [currentMarker, setCurrentMarker] = useState(null);

  useEffect(() => {
    if (!kakaoMap) return;

    markers.forEach(marker => {
      const position = new window.kakao.maps.LatLng(marker.latitude, marker.longitude);
      const imageSize = new window.kakao.maps.Size(24, 35);
      const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
      const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);

      new window.kakao.maps.Marker({
        map: kakaoMap,
        position,
        image: markerImage,
      });
    });
  }, [kakaoMap]);

  useEffect(() => {
    if (!kakaoMap) return;
    if (currentMarker) currentMarker.setMap(null);
    // 이동할 위도 경도 위치를 생성합니다
    const moveLatLon = new window.kakao.maps.LatLng(latitude, longitude);

    // 지도 중심을 이동 시킵니다
    kakaoMap.setCenter(moveLatLon);

    const position = new window.kakao.maps.LatLng(latitude, longitude);

    // 마커를 생성합니다
    const newMarker = new window.kakao.maps.Marker({
      map: kakaoMap,
      position,
    });

    newMarker.setMap(kakaoMap);

    setCurrentMarker(newMarker);
  }, [mainPosition]);

  return <div ref={container} style={{ width, height }} />;
}

export default Map;
