import styled, { css, keyframes } from 'styled-components';
import { memo } from 'react';
import Love from '~/assets/icons/love.svg';
import ProfileImage from '../../ProfileImage';
import { Restaurant } from '~/@types/restaurant.types';
import { Celeb } from '~/@types/celeb.types';

interface MarkerProps {
  clickMarker: () => void;
  isClicked: boolean;
  restaurant: Restaurant;
  celeb: Celeb;
  hoveredId: number;
}

function Marker({ clickMarker, isClicked, restaurant, celeb, hoveredId }: MarkerProps) {
  return (
    <StyledMarker
      onClick={clickMarker}
      isClicked={isClicked}
      isRestaurantHovered={hoveredId === restaurant.id}
      data-cy={`${restaurant.name} 마커`}
    >
      <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size="32px" />

      {false && (
        <StyledLikeButton aria-label="좋아요" type="button">
          <Love width={20} fill="red" fillOpacity={0.8} aria-hidden="true" />
        </StyledLikeButton>
      )}
    </StyledMarker>
  );
}

export default Marker;

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

  position: relative;

  ${({ isClicked, isRestaurantHovered }) => css`
    width: 36px;
    height: 36px;

    border: ${isClicked || isRestaurantHovered ? '3px solid var(--orange-2)' : '3px solid var(--white)'};
    border-radius: 50%;

    transition: transform 0.2s ease-in-out;
    transform: ${isClicked ? 'scale(1.4)' : 'scale(1)'};
    box-shadow: var(--map-shadow);

    &:hover {
      transform: scale(1.4);
    }

    ${isRestaurantHovered &&
    css`
      animation: ${scaleUp} 0.2s ease-in-out forwards;
    `}
  `}
`;

const StyledLikeButton = styled.button`
  position: absolute;
  right: -12px;
  bottom: -12px;

  border: none;
  background-color: transparent;
`;
