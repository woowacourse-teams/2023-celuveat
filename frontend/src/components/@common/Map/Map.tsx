import { useState } from 'react';
import { Wrapper, Status } from '@googlemaps/react-wrapper';
import { styled, keyframes } from 'styled-components';
import { shallow } from 'zustand/shallow';
import MapContent from './MapContent';
import OverlayMyLocation from './OverlayMyLocation';
import LoadingDots from '../LoadingDots';
import { mapUIBase } from '~/styles/common';
import MyLocation from '~/assets/icons/my-location.svg';
import LeftBracket from '~/assets/icons/left-bracket.svg';
import RightBracket from '~/assets/icons/right-bracket.svg';

import type { Coordinate } from '~/@types/map.types';

import useMediaQuery from '~/hooks/useMediaQuery';
import useMapState from '~/hooks/store/useMapState';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';
import OverlayMarkerList from './OverlayMarkerList';

interface MapProps {
  toggleMapExpand?: () => void;
}

const render = (status: Status) => {
  if (status === Status.FAILURE)
    return <div>지도를 불러올 수 없습니다. 페이지를 새로고침 하거나 네트워크 연결을 다시 한 번 확인해주세요.</div>;
  return (
    <StyledMapLoadingContainer>
      <LoadingDots />
    </StyledMapLoadingContainer>
  );
};

const StyledMapLoadingContainer = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;

  height: 100%;

  background-color: var(--gray-2);
`;

function Map({ toggleMapExpand }: MapProps) {
  const [center, setCenter, zoom, setZoom] = useMapState(state => [
    state.center,
    state.setCenter,
    state.zoom,
    state.setZoom,
  ]);
  const { isMobile } = useMediaQuery();
  const [clicks, setClicks] = useState<google.maps.LatLng[]>([]);
  const [myPosition, setMyPosition] = useState<Coordinate | null>(null);
  const [isMapExpanded, setIsMapExpanded] = useState(false);
  const [loading, setLoading] = useState(false);
  const [currentCenter, setCurrentCenter] = useState<Coordinate>(center);
  const [setBoundary, setCurrentPage] = useRestaurantsQueryStringState(
    state => [state.setBoundary, state.setCurrentPage],
    shallow,
  );

  const onClick = (e: google.maps.MapMouseEvent) => {
    setClicks([...clicks, e.latLng!]);
  };

  const onIdle = (m: google.maps.Map) => {
    setZoom(m.getZoom()!);
    setCurrentCenter({ lat: m.getCenter().lat(), lng: m.getCenter().lng() });

    const lowLatitude = String(m.getBounds().getSouthWest().lat());
    const highLatitude = String(m.getBounds().getNorthEast().lat());
    const lowLongitude = String(m.getBounds().getSouthWest().lng());
    const highLongitude = String(m.getBounds().getNorthEast().lng());
    const coordinateBoundary = { lowLatitude, highLatitude, lowLongitude, highLongitude };

    setBoundary(coordinateBoundary);
    setCurrentPage(0);
    if (!isMobile) window.scrollTo(0, 0);
    setCenter({ lat: m.getCenter().lat(), lng: m.getCenter().lng() });
  };

  const clickMyLocationButton = () => {
    setLoading(true);
    navigator.geolocation.getCurrentPosition((position: GeolocationPosition) => {
      setMyPosition({ lat: position.coords.latitude, lng: position.coords.longitude });
      setLoading(false);
      setCenter({ lat: position.coords.latitude, lng: position.coords.longitude });
      setZoom(16);
    });
  };

  const clickMapExpand = () => {
    setIsMapExpanded(prev => !prev);
    toggleMapExpand();
  };

  const setMapLoadingState = (state: boolean) => setLoading(state);

  return (
    <Wrapper apiKey={process.env.GOOGLE_MAP_API_KEY} render={render} language="ko" libraries={['places']}>
      <MapContent
        onClick={onClick}
        onIdle={onIdle}
        style={{ width: '100%', height: '100%' }}
        zoom={zoom}
        center={center}
      >
        <OverlayMarkerList center={currentCenter} setMapLoadingState={setMapLoadingState} />
        {myPosition && <OverlayMyLocation position={myPosition} />}
        {loading && (
          <StyledLoadingUI>
            <LoadingDots />
          </StyledLoadingUI>
        )}
        <StyledMyPositionButtonUI onClick={clickMyLocationButton} type="button">
          <MyLocation />
        </StyledMyPositionButtonUI>
        {!isMobile && (
          <StyledMapExpandButton onClick={clickMapExpand}>
            {isMapExpanded ? <RightBracket /> : <LeftBracket />}
          </StyledMapExpandButton>
        )}
      </MapContent>
    </Wrapper>
  );
}

export default Map;

const fadeIn = keyframes`
  from {
    scale: 0.8;
    opacity: 0;
  }
  to {
    scale:1;
    opacity: 1;
  }
`;

const StyledLoadingUI = styled.div`
  ${mapUIBase}
  position: absolute;
  top: 24px;
  right: calc(50% - 41px);

  width: 82px;
  height: 40px;

  animation: ${fadeIn} 0.4s ease-in;
`;

const StyledMyPositionButtonUI = styled.button`
  ${mapUIBase}
  position: absolute;
  top: 24px;
  right: 24px;

  width: 40px;
  height: 40px;
`;

const StyledMapExpandButton = styled.button`
  ${mapUIBase}
  position: absolute;
  top: 24px;
  left: 24px;

  width: 40px;
  height: 40px;
`;
