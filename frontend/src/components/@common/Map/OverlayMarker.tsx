import styled from 'styled-components';
import { useState } from 'react';
import ProfileImage from '../ProfileImage';
import Overlay from './Overlay/Overlay';
import type { Celeb } from '~/@types/celeb.types';
import type { Coordinate } from '~/@types/map.types';
import { Restaurant } from '~/@types/restaurant.types';
import RestaurantCard from '~/components/RestaurantCard';
import type { Quadrant } from '~/utils/getQuadrant';

interface OverlayMarkerProps {
  celeb: Celeb;
  onClick: ({ lat, lng }: Coordinate) => void;
  map?: google.maps.Map;
  restaurant: Restaurant;
  quadrant: Quadrant;
}

function OverlayMarker({ celeb, restaurant, map, quadrant, onClick }: OverlayMarkerProps) {
  const { lat, lng } = restaurant;
  const [isClicked, setIsClicked] = useState(false);

  const clickMarker = () => {
    onClick({ lat, lng });
    setIsClicked(prev => !prev);
  };

  console.log(quadrant);

  return (
    map && (
      <Overlay position={{ lat, lng }} map={map}>
        <StyledMarker type="button" onClick={clickMarker}>
          <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size={32} border />
        </StyledMarker>
        <StyledModal isClicked={isClicked} quadrant={quadrant}>
          <RestaurantCard restaurant={restaurant} onClick={() => {}} />
        </StyledModal>
      </Overlay>
    )
  );
}

const StyledMarker = styled.button`
  border: none;
  background-color: transparent;

  transition: all 0.2s ease-in-out;

  &:hover {
    transform: scale(1.5);
  }
`;

const StyledModal = styled.div<{ isClicked: boolean; quadrant: Quadrant }>`
  display: ${({ isClicked }) => (isClicked ? 'block' : 'none')};

  position: absolute;
  top: ${({ quadrant }) => (quadrant === 1 || quadrant === 2 ? '30px' : '-280px')};
  right: ${({ quadrant }) => (quadrant === 1 || quadrant === 4 ? '30px' : '-200px')};
  z-index: 1;

  width: 200px;

  padding: 1.2rem;

  border-radius: 12px;
  background-color: #fff;
`;

export default OverlayMarker;
