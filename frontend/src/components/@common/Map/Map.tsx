import { useState } from 'react';
import { Wrapper, Status } from '@googlemaps/react-wrapper';
import { styled } from 'styled-components';
import OverlayMarker from './OverlayMarker';
import type { Coordinate, CoordinateBoundary } from '~/@types/map.types';
import MapContent from './MapContent';
import OverlayMyLocation from './OverlayMyLocation';
import LoadingDots from '../LoadingDots';
import { mapUIBase } from '~/styles/common';
import MyLocation from '~/assets/icons/my-location.svg';
import LeftBracket from '~/assets/icons/left-bracket.svg';
import RightBracket from '~/assets/icons/right-bracket.svg';
import Minus from '~/assets/icons/minus.svg';
import Plus from '~/assets/icons/plus.svg';
import { RestaurantData } from '~/@types/api.types';
import getQuadrant from '~/utils/getQuadrant';

interface MapProps {
  data: RestaurantData[];
  setBoundary: React.Dispatch<React.SetStateAction<CoordinateBoundary>>;
  toggleMapExpand: () => void;
}

const render = (status: Status) => {
  if (status === Status.FAILURE)
    return <div>지도를 불러올 수 없습니다. 페이지를 새로고침 하거나 네트워크 연결을 다시 한 번 확인해주세요.</div>;
  return <LoadingDots />;
};

function Map({ data, setBoundary, toggleMapExpand }: MapProps) {
  const [center, setCenter] = useState<Coordinate>({ lat: 37.5057482, lng: 127.050727 });
  const [clicks, setClicks] = useState<google.maps.LatLng[]>([]);
  const [zoom, setZoom] = useState(16);
  const [myPosition, setMyPosition] = useState<Coordinate | null>(null);
  const [isMapExpanded, setIsMapExpanded] = useState(false);
  const [loading, setLoading] = useState(false);
  const [mainPosition, setMainPosition] = useState({ lat: 37.5057482, lng: 127.050727 });

  const onClick = (e: google.maps.MapMouseEvent) => {
    setClicks([...clicks, e.latLng!]);
  };

  const onIdle = (m: google.maps.Map) => {
    setZoom(m.getZoom()!);
    setMainPosition({ lat: m.getCenter().lat(), lng: m.getCenter().lng() });

    const lowLatitude = String(m.getBounds().getSouthWest().lat());
    const highLatitude = String(m.getBounds().getNorthEast().lat());
    const lowLongitude = String(m.getBounds().getSouthWest().lng());
    const highLongitude = String(m.getBounds().getNorthEast().lng());
    const coordinateBoundary = { lowLatitude, highLatitude, lowLongitude, highLongitude };

    setBoundary(coordinateBoundary);
  };

  const clickMyLocationButton = () => {
    setLoading(true);
    navigator.geolocation.getCurrentPosition((position: GeolocationPosition) => {
      setMyPosition({ lat: position.coords.latitude, lng: position.coords.longitude });
      setLoading(false);
      setCenter({ lat: position.coords.latitude, lng: position.coords.longitude });
    });
  };

  const clickZoom =
    (number: number): React.MouseEventHandler<HTMLButtonElement> =>
    () => {
      setZoom(prev => prev + number);
    };

  const clickMapExpand = () => {
    setIsMapExpanded(prev => !prev);
    toggleMapExpand();
  };

  return (
    <Wrapper apiKey={process.env.GOOGLE_MAP_API_KEY} render={render} language="ko">
      <MapContent
        onClick={onClick}
        onIdle={onIdle}
        style={{ width: '100%', height: '100%' }}
        zoom={zoom}
        center={center}
      >
        {data.map(({ celebs, ...restaurant }) => {
          const { lat, lng } = restaurant;
          return (
            <OverlayMarker
              restaurant={restaurant}
              onClick={() => {}}
              celeb={celebs[0]}
              quadrant={getQuadrant(mainPosition, { lat, lng })}
            />
          );
        })}
        {myPosition && <OverlayMyLocation position={myPosition} />}
        {loading && (
          <LoadingUI>
            <LoadingDots />
          </LoadingUI>
        )}
        <StyledMyPositionButtonUI onClick={clickMyLocationButton} type="button">
          <MyLocation />
        </StyledMyPositionButtonUI>
        <StyledZoomUI>
          <button type="button" onClick={clickZoom(1)}>
            <Plus />
          </button>
          <div />
          <button type="button" onClick={clickZoom(-1)}>
            <Minus />
          </button>
        </StyledZoomUI>
        <StyledMapExpandButton onClick={clickMapExpand}>
          {isMapExpanded ? <RightBracket /> : <LeftBracket />}
        </StyledMapExpandButton>
      </MapContent>
    </Wrapper>
  );
}

export default Map;

const LoadingUI = styled.div`
  ${mapUIBase}
  position: absolute;
  top: 24px;
  right: calc(50% - 41px);

  width: 82px;

  padding: 1.6rem 2.4rem;
`;

const StyledMyPositionButtonUI = styled.button`
  ${mapUIBase}
  position: absolute;
  top: 129px;
  right: 24px;

  width: 40px;
  height: 40px;
`;

const StyledZoomUI = styled.div`
  ${mapUIBase}
  flex-direction: column;

  position: absolute;
  top: 24px;
  right: 24px;

  & > button {
    ${mapUIBase}
    width: 40px;
    height: 40px;
    box-shadow: none;
  }

  & > div {
    width: 100%;
    height: 1px;

    background-color: var(--black);

    opacity: 0.1;
  }
`;

const StyledMapExpandButton = styled.button`
  ${mapUIBase}
  position: absolute;
  top: 24px;
  left: 24px;

  width: 40px;
  height: 40px;
`;
