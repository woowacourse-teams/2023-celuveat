import { useRef } from 'react';
import { styled } from 'styled-components';
import { RestaurantData } from '~/@types/api.types';
import useDrawMap from './hooks/useDrawMap';
import useMarker from './hooks/useMarker';

interface MapProps {
  center: google.maps.LatLngLiteral;
  zoom: number;
  size: { width: string; height: string };
  restaurants: RestaurantData[];
}

function Map({ center, zoom, size, restaurants }: MapProps) {
  const googleMapRef = useRef();
  const { googleMap } = useDrawMap({ mapRef: googleMapRef, center, zoom });
  const { markers } = useMarker({ map: googleMap, restaurants });

  return <StyledMap ref={googleMapRef} size={size} id="map" />;
}

export default Map;

const StyledMap = styled.div<{ size: { width: string; height: string } }>`
  width: ${({ size }) => size.width};
  height: ${({ size }) => size.height};
`;
