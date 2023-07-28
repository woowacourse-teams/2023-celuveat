import { useEffect, useMemo } from 'react';
import { createPortal } from 'react-dom';
import createOverlay from './domain/createOverlay';

interface OverlayProps {
  map: google.maps.Map;
  children: React.ReactNode;
  position: google.maps.LatLng | google.maps.LatLngLiteral;
  pane?: keyof google.maps.MapPanes;
  zIndex?: number;
}

function Overlay({ position, pane = 'floatPane', map, zIndex, children }: OverlayProps) {
  const container = useMemo(() => {
    const div = document.createElement('div');
    div.style.position = 'absolute';
    div.style.zIndex = `${zIndex}`;
    div.onmouseover = function () {
      div.style.zIndex = '18';
    };
    div.onmouseout = function () {
      div.style.zIndex = `${zIndex}`;
    };

    return div;
  }, [zIndex]);

  const overlay = useMemo(() => createOverlay(container, pane, position), [container, pane, position]);

  useEffect(() => {
    overlay?.setMap(map);
    return () => overlay?.setMap(null);
  }, [map, overlay]);

  useEffect(() => {
    container.style.zIndex = `${zIndex}`;
  }, [zIndex, container]);

  return createPortal(children, container);
}

export default Overlay;
