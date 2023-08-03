import styled, { css, keyframes } from 'styled-components';
import { MouseEvent, useRef, useState } from 'react';
import ProfileImage from '../ProfileImage';
import Overlay from './Overlay/Overlay';
import RestaurantCard from '~/components/RestaurantCard';
import useOnClickOutside from '~/hooks/useOnClickOutside';

import type { Quadrant } from '~/utils/getQuadrant';
import type { Restaurant } from '~/@types/restaurant.types';
import type { Celeb } from '~/@types/celeb.types';

interface OverlayMarkerProps {
  celeb: Celeb;
  map?: google.maps.Map;
  restaurant: Restaurant;
  quadrant: Quadrant;
  isRestaurantHovered: boolean;
}

function OverlayMarker({ celeb, restaurant, map, quadrant, isRestaurantHovered }: OverlayMarkerProps) {
  const { lat, lng } = restaurant;
  const [isClicked, setIsClicked] = useState(false);
  const ref = useRef();
  useOnClickOutside(ref, () => setIsClicked(false));

  const clickMarker = () => {
    setIsClicked(true);
  };

  const clickModal = (e: MouseEvent) => {
    e.stopPropagation();
  };

  return (
    map && (
      <Overlay position={{ lat, lng }} map={map} zIndex={isClicked || isRestaurantHovered ? 18 : 0}>
        <StyledMarkerContainer ref={ref}>
          <StyledMarker onClick={clickMarker} isClicked={isClicked} isRestaurantHovered={isRestaurantHovered}>
            <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} border size="100%" />
          </StyledMarker>
          {isClicked && (
            <StyledModal quadrant={quadrant} onClick={clickModal}>
              <RestaurantCard restaurant={restaurant} type="map" />
            </StyledModal>
          )}
        </StyledMarkerContainer>
      </Overlay>
    )
  );
}

export default OverlayMarker;

const StyledMarkerContainer = styled.div`
  position: relative;
`;

const scaleUp = keyframes`
  0% {
    transform: scale(1);
  }
  100% {
    transform: scale(1.5);
  }
`;

const StyledMarker = styled.div<{ isClicked: boolean; isRestaurantHovered: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 36px;
  height: 36px;

  border: ${({ isClicked, isRestaurantHovered }) =>
    isClicked || isRestaurantHovered ? '3px solid var(--orange-2)' : '3px solid transparent'};
  border-radius: 50%;

  transition: transform 0.2s ease-in-out;
  transform: ${({ isClicked }) => (isClicked ? 'scale(1.5)' : 'scale(1)')};

  &:hover {
    transform: scale(1.5);
  }

  ${({ isRestaurantHovered }) =>
    isRestaurantHovered &&
    css`
      animation: ${scaleUp} 0.2s ease-in-out forwards;
    `}
`;

const fadeInAnimation = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const StyledModal = styled.div<{ quadrant: Quadrant }>`
  position: absolute;
  top: ${({ quadrant }) => (quadrant === 1 || quadrant === 2 ? '48px' : '-288px')};
  right: ${({ quadrant }) => (quadrant === 1 || quadrant === 4 ? '0px' : '-210px')};

  width: 248px;

  border-radius: 12px;
  background-color: #fff;

  animation: ${fadeInAnimation} 100ms ease-in;
  box-shadow: 0 4px 6px rgb(0 0 0 / 20%);
`;
