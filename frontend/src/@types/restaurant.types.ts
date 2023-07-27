import type { RestaurantData } from './api.types';

export type Restaurant = Omit<RestaurantData, 'celebs'>;
export type RestaurantModalInfo = Omit<Restaurant, 'lat' | 'lng'>;

export type RestaurantCategory =
  | '전체'
  | '일식당'
  | '한식'
  | '초밥,롤'
  | '생선회'
  | '양식'
  | '육류,고기요리'
  | '이자카야'
  | '돼지고기구이'
  | '요리주점'
  | '와인'
  | '기타';
