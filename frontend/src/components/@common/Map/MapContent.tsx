/* eslint-disable @typescript-eslint/ban-ts-comment */
/* eslint-disable react-hooks/exhaustive-deps */

import React from 'react';
import useMap from './hooks/useMap';
import type { Coordinate } from '~/@types/map.types';

interface MapContentProps extends google.maps.MapOptions {
  zoom: number;
  center: Coordinate;
  markers?: Coordinate[];
  children?: React.ReactNode;
  style: { [key: string]: string };
  onIdle?: (map: google.maps.Map) => void;
  onClick?: (e: google.maps.MapMouseEvent) => void;
  gestureHandling?: 'greedy' | 'cooperative';
}

function MapContent({
  style,
  zoom,
  center,
  children,
  onClick,
  onIdle,
  markers,
  gestureHandling = 'greedy',
}: MapContentProps) {
  const { ref, map } = useMap({ center, zoom, onClick, onIdle, markers, gestureHandling });

  return (
    <>
      <div ref={ref} id="map" style={style} />
      {React.Children.map(children, child => {
        if (React.isValidElement(child)) {
          // @ts-ignore
          return React.cloneElement(child, { map });
        }
        return null;
      })}
    </>
  );
}

export default MapContent;
