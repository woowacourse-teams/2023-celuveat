/* eslint-disable react-hooks/exhaustive-deps */

import { useEffect, useState } from 'react';
import { Coordinate } from '~/@types/map.types';

const useCreateMap = ({
  center,
  level,
  container,
}: {
  center: Coordinate;
  level: number;
  container: React.RefObject<HTMLDivElement>;
}) => {
  const { lat, lng } = center;
  const [kakaoMap, setKakaoMap] = useState(null);

  useEffect(() => {
    const options = {
      center: new window.kakao.maps.LatLng(lat, lng),
      level,
    };
    const map = new window.kakao.maps.Map(container.current, options);
    setKakaoMap(map);
  }, []);

  return { kakaoMap };
};

export default useCreateMap;
