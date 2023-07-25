import styled from 'styled-components';
import ProfileImage from '../ProfileImage';
import Overlay from './Overlay/Overlay';
import type { Celeb } from '~/@types/celeb.types';
import type { Coordinate } from '~/@types/map.types';

interface OverlayMarkerProps {
  celeb: Celeb;
  position: Coordinate;
  onClick: ({ lat, lng }: Coordinate) => void;
  map?: google.maps.Map;
}

function OverlayMarker({ celeb, position, map, onClick }: OverlayMarkerProps) {
  return (
    map && (
      <Overlay position={position} map={map}>
        <StyledMarker type="button" onClick={() => onClick(position)}>
          <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size={32} border />
        </StyledMarker>
      </Overlay>
    )
  );
}

const StyledMarker = styled.button`
  border: none;
  background-color: transparent;

  transition: all 0.2s ease-in-out;

  &:hover {
    transform: scale(1.1);
  }
`;

export default OverlayMarker;
