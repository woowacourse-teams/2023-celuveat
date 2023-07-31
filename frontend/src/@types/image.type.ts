import type { RestaurantData } from './api.types';

type RestaurantImages = RestaurantData['images'];

export type RestaurantImage = RestaurantImages[number];
