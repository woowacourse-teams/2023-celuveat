import styled, { keyframes } from 'styled-components';
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
    setIsClicked(!isClicked);
  };

  return (
    map && (
      <Overlay position={{ lat, lng }} map={map} zIndex={isClicked ? 18 : 0}>
        <StyledMarker type="button" onClick={clickMarker}>
          <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size={32} border />
        </StyledMarker>
        <StyledModal isClicked={isClicked} quadrant={quadrant}>
          <RestaurantCard restaurant={restaurant} onClick={() => {}} type="map" />
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

const fadeInAnimation = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const StyledModal = styled.div<{ isClicked: boolean; quadrant: Quadrant }>`
  display: ${({ isClicked }) => (isClicked ? 'block' : 'none')};

  position: absolute;
  top: ${({ quadrant }) => (quadrant === 1 || quadrant === 2 ? '30px' : '-280px')};
  right: ${({ quadrant }) => (quadrant === 1 || quadrant === 4 ? '45px' : '-200px')};

  width: 200px;

  border-radius: 12px;
  background-color: #fff;

  animation: ${fadeInAnimation} 100ms ease-in;
  box-shadow: 0 4px 6px rgb(0 0 0 / 20%);
`;

export default OverlayMarker;
