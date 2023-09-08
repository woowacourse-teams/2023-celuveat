import { create } from 'zustand';

interface HoveredRestaurantState {
  id: number;
}

interface HoveredRestaurantAction {
  setId: (id: number) => void;
}

const useHoveredRestaurantState = create<HoveredRestaurantState & HoveredRestaurantAction>(set => ({
  id: null,

  setId: id => set({ id }),
}));

export default useHoveredRestaurantState;
