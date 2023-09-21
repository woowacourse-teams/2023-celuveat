import styled, { css, keyframes } from 'styled-components';
import { MouseEvent, useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import ProfileImage from '../ProfileImage';
import Overlay from './Overlay/Overlay';
import RestaurantCard from '~/components/RestaurantCard';
import useOnClickOutside from '~/hooks/useOnClickOutside';

import Love from '~/assets/icons/love.svg';

import type { Quadrant } from '~/utils/getQuadrant';
import type { Restaurant } from '~/@types/restaurant.types';
import type { Celeb } from '~/@types/celeb.types';
import useHoveredRestaurantState from '~/hooks/store/useHoveredRestaurantState';
import useMediaQuery from '~/hooks/useMediaQuery';
import { FONT_SIZE } from '~/styles/common';

interface OverlayMarkerProps {
  celeb: Celeb;
  map: google.maps.Map;
  restaurant: Restaurant;
  quadrant: Quadrant;
}

function OverlayMarker({ celeb, restaurant, map, quadrant }: OverlayMarkerProps) {
  const { lat, lng, id: restaurantId } = restaurant;
  const [isClicked, setIsClicked] = useState(false);
  const ref = useRef();
  const [hoveredId] = useHoveredRestaurantState(state => [state.id]);
  const { isMobile } = useMediaQuery();
  useOnClickOutside(ref, () => setIsClicked(false));

  const clickMarker = () => {
    setIsClicked(true);
  };

  const clickModal = (e: MouseEvent) => {
    e.stopPropagation();
  };

  return (
    map && (
      <Overlay position={{ lat, lng }} map={map} zIndex={isClicked || hoveredId === restaurantId ? 18 : 0}>
        <StyledMarkerContainer ref={ref} data-cy={`${restaurant.name} 오버레이`}>
          <StyledMarker
            onClick={clickMarker}
            isClicked={isClicked}
            isRestaurantHovered={hoveredId === restaurantId}
            isMobile={isMobile}
            data-cy={`${restaurant.name} 마커`}
          >
            {isMobile &&
              (isClicked ? (
                <>
                  <Link to={`/restaurants/${restaurant.id}?celebId=${celeb.id}`}>
                    <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size="24px" />
                  </Link>
                  <Link to={`/restaurants/${restaurant.id}?celebId=${celeb.id}`}>
                    <StyledRestaurantInfo>
                      <span>{restaurant.name}</span>
                      <span>{restaurant.category}</span>
                    </StyledRestaurantInfo>
                  </Link>
                </>
              ) : (
                <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size="24px" />
              ))}

            {!isMobile && <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size="32px" />}

            {false && (
              <StyledLikeButton aria-label="좋아요" type="button">
                <Love width={20} fill="red" fillOpacity={0.8} aria-hidden="true" />
              </StyledLikeButton>
            )}
          </StyledMarker>

          {isClicked && !isMobile && (
            <StyledModal quadrant={quadrant} onClick={clickModal}>
              <RestaurantCard restaurant={restaurant} type="map" celebs={[celeb]} size="0" />
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

const fadeInAnimation = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const StyledMarker = styled.div<{ isClicked: boolean; isRestaurantHovered: boolean; isMobile: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;

  position: relative;

  ${({ isMobile, isClicked, isRestaurantHovered }) =>
    isMobile
      ? css`
          gap: 0 0.4rem;

          padding: ${isClicked ? '0.4rem 1.2rem 0.4rem 0.4rem' : '0'};

          ${isClicked
            ? css`
                border: 1.8px solid var(--white);
              `
            : css`
                border: 1.8px solid var(--white);
              `};

          border-radius: 50px;
          background-color: var(--white);
          box-shadow: var(--map-shadow);

          & > a:last-child {
            font-size: ${FONT_SIZE.sm};
            font-weight: 700;
            text-decoration: none;
          }
        `
      : css`
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

const StyledModal = styled.div<{ quadrant: Quadrant }>`
  position: absolute;
  top: ${({ quadrant }) => (quadrant === 1 || quadrant === 2 ? '48px' : '-288px')};
  right: ${({ quadrant }) => (quadrant === 1 || quadrant === 4 ? '0px' : '-210px')};

  width: 248px;

  border-radius: 12px;
  background-color: #fff;

  animation: ${fadeInAnimation} 100ms ease-in;
  box-shadow: var(--map-shadow);
`;

const StyledLikeButton = styled.button`
  position: absolute;
  right: -12px;
  bottom: -12px;

  border: none;
  background-color: transparent;
`;

const StyledRestaurantInfo = styled.div`
  display: flex;
  flex-direction: column;

  & > :last-child {
    color: var(--gray-3);
    font-size: 1rem;
    font-weight: 400;
  }
`;
