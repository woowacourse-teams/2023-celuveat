import { create } from 'zustand';

import type { CoordinateBoundary } from '~/@types/map.types';
import type { RestaurantCategory } from '~/@types/restaurant.types';

interface RestaurantsQueryStringState {
  boundary: CoordinateBoundary | null;
  celebId: number;
  currentPage: number;
  restaurantCategory: RestaurantCategory;
  sort: 'distance' | 'like';
}

interface RestaurantsQueryStringAction {
  setBoundary: (boundary: CoordinateBoundary) => void;
  setCelebId: (celebId: number) => void;
  setCurrentPage: (currentPage: number) => void;
  setRestaurantCategory: (restaurantCategory: RestaurantCategory) => void;
  setSort: (sort: 'distance' | 'like') => void;
}

const useRestaurantsQueryStringState = create<RestaurantsQueryStringState & RestaurantsQueryStringAction>(set => ({
  boundary: null,
  celebId: -1,
  currentPage: 0,
  restaurantCategory: '전체',
  sort: 'like',

  setBoundary: boundary => set(() => ({ boundary })),
  setCelebId: celebId => set(() => ({ celebId })),
  setCurrentPage: currentPage => set(() => ({ currentPage })),
  setRestaurantCategory: restaurantCategory => set({ restaurantCategory }),
  setSort: sort => set({ sort }),
}));

export default useRestaurantsQueryStringState;
