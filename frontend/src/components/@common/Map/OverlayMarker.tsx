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
        <StyledMarker onClick={clickMarker} isClicked={isClicked}>
          <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} border />
        </StyledMarker>
        <StyledModal isClicked={isClicked} quadrant={quadrant}>
          <RestaurantCard restaurant={restaurant} onClick={() => {}} type="map" />
        </StyledModal>
      </Overlay>
    )
  );
}

const StyledMarker = styled.div<{ isClicked: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 36px;
  height: 36px;

  border: ${({ isClicked }) => (isClicked ? '3px solid var(--orange-2)' : '3px solid transparent')};
  border-radius: 50%;

  transition: transform 0.2s ease-in-out;
  transform: ${({ isClicked }) => (isClicked ? 'scale(1.5)' : 'scale(1)')};

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
  top: ${({ quadrant }) => (quadrant === 1 || quadrant === 2 ? '40px' : '-280px')};
  right: ${({ quadrant }) => (quadrant === 1 || quadrant === 4 ? '45px' : '-210px')};

  width: 200px;

  border-radius: 12px;
  background-color: #fff;

  animation: ${fadeInAnimation} 100ms ease-in;
  box-shadow: 0 4px 6px rgb(0 0 0 / 20%);
`;

export default OverlayMarker;
