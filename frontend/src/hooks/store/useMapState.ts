import { create } from 'zustand';
import type { Coordinate } from '~/@types/map.types';

interface MapState {
  center: Coordinate;
  zoom: number;
}

interface MapAction {
  setCenter: (newCenter: Coordinate) => void;
  setZoom: (newZoom: number) => void;
}

const JamsilCampus = { lat: 37.515271, lng: 127.1029949 };

const useMapState = create<MapState & MapAction>(set => ({
  center: JamsilCampus,
  zoom: 13,

  setCenter: newCenter => set(() => ({ center: newCenter })),
  setZoom: newZoom => set(() => ({ zoom: newZoom })),
}));

export default useMapState;
