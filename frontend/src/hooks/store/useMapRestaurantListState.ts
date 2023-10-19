import { create } from 'zustand';
import { RestaurantListData } from '~/@types/api.types';

interface MapRestaurantListState {
  isList: boolean;
  storage: RestaurantListData;
}

interface MapRestaurantListAction {
  setIsList: (isList: boolean) => void;
  setStorage: (restaurantData: RestaurantListData) => void;
}

const useMapRestaurantListState = create<MapRestaurantListState & MapRestaurantListAction>(set => ({
  isList: false,
  storage: null,

  setIsList: isList => set({ isList }),
  setStorage: storage => set({ storage }),
}));

export default useMapRestaurantListState;
