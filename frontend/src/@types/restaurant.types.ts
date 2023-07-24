import type { RestaurantData } from './api.types';

export type Restaurant = Omit<RestaurantData, 'celebs'>;
export type RestaurantModalInfo = Omit<Restaurant, 'lat' | 'lng'>;
