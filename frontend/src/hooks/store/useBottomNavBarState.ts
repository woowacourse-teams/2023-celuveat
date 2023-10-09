import { create } from 'zustand';

interface BottomNavBarState {
  selected: 'home' | 'map' | 'user';
}

interface BottomNavBarAction {
  setHomeSelected: VoidFunction;
  setMapSelected: VoidFunction;
  setUserSelected: VoidFunction;
}

const useBottomNavBarState = create<BottomNavBarState & BottomNavBarAction>(set => ({
  selected: 'home',

  setHomeSelected: () => set({ selected: 'home' }),
  setMapSelected: () => set({ selected: 'map' }),
  setUserSelected: () => set({ selected: 'user' }),
}));

export default useBottomNavBarState;
