import styled from 'styled-components';
import Overlay from './Overlay/Overlay';
import type { Coordinate } from '~/@types/map.types';

interface OverlayMyLocationProps {
  position: Coordinate;
  map?: google.maps.Map;
}

function OverlayMyLocation({ position, map }: OverlayMyLocationProps) {
  return (
    map && (
      <Overlay position={position} map={map}>
        <StyledMyLocation>
          <StyledMyLocationRound />
          <StyledMyLocationBorder />
        </StyledMyLocation>
      </Overlay>
    )
  );
}

export default OverlayMyLocation;

const StyledMyLocationRound = styled.div`
  position: absolute;
  top: 6px;
  left: 6px;
  z-index: 10;

  width: 12px;
  height: 12px;

  border: 1px solid var(--white);
  border-radius: 50%;
  background-color: var(--primary-5);
`;

const StyledMyLocationBorder = styled.div`
  position: absolute;

  width: 24px;
  height: 24px;

  border-radius: 50%;
  background-color: var(--primary-5);

  opacity: 0.5;
`;

const StyledMyLocation = styled.div`
  display: relative;

  width: 24px;
  height: 24px;
`;
