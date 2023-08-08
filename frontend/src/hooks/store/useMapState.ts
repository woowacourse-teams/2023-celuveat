import { create } from 'zustand';
import { Coordinate } from '~/@types/map.types';

interface MapState {
  center: Coordinate;
  setCenter: (newCenter: Coordinate) => void;
}

const JamsilCampus = { lat: 37.515271, lng: 127.1029949 };

const useMapState = create<MapState>(set => ({
  center: JamsilCampus,
  setCenter: newCenter => set(() => ({ center: newCenter })),
}));

export default useMapState;
