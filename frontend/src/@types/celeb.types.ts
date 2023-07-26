import type { RestaurantData } from './api.types';

type Celebs = RestaurantData['celebs'];

export type Celeb = Celebs[number];

export type CelebsSearchBarOptionType = Omit<Celeb, 'subscriberCount'>;
