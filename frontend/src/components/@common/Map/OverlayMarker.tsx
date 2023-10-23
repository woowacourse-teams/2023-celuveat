import { styled, keyframes } from 'styled-components';
import { MouseEvent, useRef, useState } from 'react';
import Overlay from './Overlay/Overlay';
import RestaurantCard from '~/components/RestaurantCard';
import useOnClickOutside from '~/hooks/useOnClickOutside';

import type { Quadrant } from '~/utils/getQuadrant';
import type { Restaurant } from '~/@types/restaurant.types';
import type { Celeb } from '~/@types/celeb.types';
import useHoveredRestaurantState from '~/hooks/store/useHoveredRestaurantState';
import useMediaQuery from '~/hooks/useMediaQuery';
import Marker from './Marker/Marker';
import useMapState from '~/hooks/store/useMapState';

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
  const setPreview = useMapState(state => state.setPreview);
  useOnClickOutside(ref, () => setIsClicked(false));

  const clickMarker = () => {
    setIsClicked(true);
    setPreview({ ...restaurant, celebs: [celeb] });
  };

  const clickModal = (e: MouseEvent) => {
    e.stopPropagation();
  };

  return (
    map && (
      <Overlay position={{ lat, lng }} map={map} zIndex={isClicked || hoveredId === restaurantId ? 18 : 0}>
        <StyledMarkerContainer ref={ref} data-cy={`${restaurant.name} 오버레이`}>
          <Marker
            clickMarker={clickMarker}
            isClicked={isClicked}
            restaurant={restaurant}
            celeb={celeb}
            hoveredId={hoveredId}
          />

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
  box-shadow: var(--map-shadow);
`;
