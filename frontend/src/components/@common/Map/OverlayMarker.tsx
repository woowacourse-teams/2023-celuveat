import styled from 'styled-components';
import { useState } from 'react';
import ProfileImage from '../ProfileImage';
import Overlay from './Overlay/Overlay';
import type { Celeb } from '~/@types/celeb.types';
import type { Coordinate } from '~/@types/map.types';
import { Restaurant } from '~/@types/restaurant.types';
import RestaurantCard from '~/components/RestaurantCard';

interface OverlayMarkerProps {
  celeb: Celeb;
  onClick: ({ lat, lng }: Coordinate) => void;
  map?: google.maps.Map;
  restaurant: Restaurant;
}

function OverlayMarker({ celeb, restaurant, map, onClick }: OverlayMarkerProps) {
  const { lat, lng } = restaurant;
  const [isClicked, setIsClicked] = useState(false);

  const clickMarker = () => {
    onClick({ lat, lng });
    setIsClicked(prev => !prev);
  };

  return (
    map && (
      <Overlay position={{ lat, lng }} map={map}>
        <StyledMarker type="button" onClick={clickMarker}>
          <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size={32} border />
        </StyledMarker>
        <StyledModal isClicked={isClicked}>
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

const StyledModal = styled.div<{ isClicked: boolean }>`
  display: ${({ isClicked }) => (isClicked ? 'block' : 'none')};

  width: 200px;

  padding: 1.2rem;

  border-radius: 12px;
  background-color: #fff;
`;

export default OverlayMarker;
