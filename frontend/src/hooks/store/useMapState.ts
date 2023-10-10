import { create } from 'zustand';
import { Celeb } from '~/@types/celeb.types';
import type { Coordinate } from '~/@types/map.types';
import { Restaurant } from '~/@types/restaurant.types';

interface Preview extends Restaurant {
  celebs?: Celeb[];
}

interface MapState {
  center: Coordinate;
  zoom: number;
  preview: Preview | null;
}

interface MapAction {
  setCenter: (newCenter: Coordinate) => void;
  setZoom: (newZoom: number) => void;
  setPreview: (newRestaurant: Preview) => void;
}

const JamsilCampus = { lat: 37.515271, lng: 127.1029949 };

const useMapState = create<MapState & MapAction>(set => ({
  center: JamsilCampus,
  zoom: 13,
  preview: null,

  setCenter: newCenter => set(() => ({ center: newCenter })),
  setZoom: newZoom => set(() => ({ zoom: newZoom })),
  setPreview: newRestaurant => set(() => ({ preview: newRestaurant })),
}));

export default useMapState;
