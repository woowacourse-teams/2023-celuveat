/* eslint-disable react-hooks/exhaustive-deps */

import { useEffect, useState } from 'react';
import { Coordinate } from '~/@types/map.types';

const useCreateMap = ({
  mainPosition,
  level,
  container,
}: {
  mainPosition: Coordinate;
  level: number;
  container: React.RefObject<HTMLDivElement>;
}) => {
  const { latitude, longitude } = mainPosition;
  const [kakaoMap, setKakaoMap] = useState(null);

  useEffect(() => {
    const options = {
      center: new window.kakao.maps.LatLng(latitude, longitude),
      level,
    };
    const map = new window.kakao.maps.Map(container.current, options);
    setKakaoMap(map);
  }, []);

  return { kakaoMap };
};

export default useCreateMap;
