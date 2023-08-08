import { create } from 'zustand';
import type { Coordinate, CoordinateBoundary } from '~/@types/map.types';

interface MapState {
  center: Coordinate;
  setCenter: (newCenter: Coordinate) => void;
  zoom: number;
  setZoom: (newZoom: number) => void;
  boundary: CoordinateBoundary | null;
  setBoundary: (newBoundary: CoordinateBoundary) => void;
}

const JamsilCampus = { lat: 37.515271, lng: 127.1029949 };

const useMapState = create<MapState>(set => ({
  center: JamsilCampus,
  setCenter: newCenter => set(() => ({ center: newCenter })),
  zoom: 13,
  setZoom: newZoom => set(() => ({ zoom: newZoom })),
  boundary: null,
  setBoundary: newBoundary => set(() => ({ boundary: newBoundary })),
}));

export default useMapState;
