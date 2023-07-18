import type { RestaurantData } from './api.types';

export type Restaurant = Omit<RestaurantData, 'celebs'>;
