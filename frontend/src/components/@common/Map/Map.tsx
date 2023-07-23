import { useEffect, useRef } from 'react';
import { styled } from 'styled-components';

interface MapProps {
  center: google.maps.LatLngLiteral;
  zoom: number;
  size: { width: string; height: string };
}

function Map({ center, zoom, size }: MapProps) {
  const ref = useRef();

  useEffect(() => {
    new window.google.maps.Map(ref.current, { center, zoom });
  });

  return <StyledMap ref={ref} size={size} id="map" />;
}

const StyledMap = styled.div<{ size: { width: string; height: string } }>`
  width: ${({ size }) => size.width};
  height: ${({ size }) => size.height};
`;

export default Map;
