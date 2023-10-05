import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import type { RestaurantData } from './api.types';
import type { CoordinateBoundary } from './map.types';

export type Restaurant = Omit<RestaurantData, 'celebs'>;
export type RestaurantModalInfo = Omit<Restaurant, 'lat' | 'lng'>;
export interface RestaurantsQueryParams {
  boundary: CoordinateBoundary;
  celebId?: number;
  category?: RestaurantCategory;
  page?: number;
  sort: 'distance' | 'like';
}

export type RestaurantCategory = keyof typeof RESTAURANT_CATEGORY;
