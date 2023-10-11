import { create } from 'zustand';
import { RestaurantData } from '~/@types/api.types';
import type { Coordinate } from '~/@types/map.types';

interface MapState {
  center: Coordinate;
  zoom: number;
  preview: RestaurantData | null;
}

interface MapAction {
  setCenter: (newCenter: Coordinate) => void;
  setZoom: (newZoom: number) => void;
  setPreview: (newRestaurant: RestaurantData) => void;
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
