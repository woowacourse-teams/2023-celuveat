import styled from 'styled-components';
import { Celeb } from '~/@types/celeb.types';
import ProfileImage from '../ProfileImage';
import OverlayView from './Overlay/Overlay';
import { Coordinate } from '~/@types/map.types';

interface OverlayMarkerProps {
  celeb: Celeb;
  position: Coordinate;
  onClick: ({ lat, lng }: Coordinate) => void;
  map?: google.maps.Map;
}

function OverlayMarker({ celeb, position, map, onClick }: OverlayMarkerProps) {
  return (
    map && (
      <OverlayView position={position} map={map}>
        <StyledMarker type="button" onClick={() => onClick(position)}>
          <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size={24} border />
        </StyledMarker>
      </OverlayView>
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
