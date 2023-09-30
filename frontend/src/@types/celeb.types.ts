import { CELEB } from '~/constants/celeb';
import type { RestaurantData } from './api.types';

type Celebs = RestaurantData['celebs'];

export type Celeb = Celebs[number];

export type CelebsSearchBarOptionType = Omit<Celeb, 'subscriberCount'>;

export type CelebId = keyof typeof CELEB;
