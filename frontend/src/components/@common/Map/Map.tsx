import { useState } from 'react';
import { Wrapper, Status } from '@googlemaps/react-wrapper';
import OverlayMarker from './OverlayMarker';
import type { Coordinate } from '~/@types/map.types';
import type { Celebs } from '~/@types/celeb.types';
import MapContent from './MapContent';

interface MapProps {
  center: Coordinate;
  clickMarker: ({ lat, lng }: Coordinate) => void;
  markers: { position: Coordinate; celebs: Celebs }[];
}

const render = (status: Status) => {
  if (status === Status.FAILURE) return <div>에러</div>;
  return <div>로딩중</div>;
};

function Map({ center, clickMarker, markers }: MapProps) {
  const [clicks, setClicks] = useState<google.maps.LatLng[]>([]);
  const [zoom, setZoom] = useState(16);

  const onClick = (e: google.maps.MapMouseEvent) => {
    setClicks([...clicks, e.latLng!]);
  };

  const onIdle = (m: google.maps.Map) => {
    setZoom(m.getZoom()!);
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
        {markers.map(({ position, celebs }) => (
          <OverlayMarker position={position} onClick={clickMarker} celeb={celebs[0]} />
        ))}
      </MapContent>
    </Wrapper>
  );
}

export default Map;
