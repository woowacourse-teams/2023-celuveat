import { create } from 'zustand';

interface BottomNavBarState {
  selected: 'home' | 'map' | 'user' | 'wishList';
}

interface BottomNavBarAction {
  setHomeSelected: VoidFunction;
  setMapSelected: VoidFunction;
  setUserSelected: VoidFunction;
  setWishListSelected: VoidFunction;
}

const useBottomNavBarState = create<BottomNavBarState & BottomNavBarAction>(set => ({
  selected: 'home',

  setHomeSelected: () => set({ selected: 'home' }),
  setMapSelected: () => set({ selected: 'map' }),
  setUserSelected: () => set({ selected: 'user' }),
  setWishListSelected: () => set({ selected: 'wishList' }),
}));

export default useBottomNavBarState;
