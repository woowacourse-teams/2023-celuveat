// 카테고리명이 너무 길어 백엔드에서 카테고리 명이 수정되기 전까지 임시방편으로 만든 유틸함수입니다.

import { RestaurantCategory } from '~/@types/restaurant.types';

export const getShortCategoryName = (category: RestaurantCategory) => {
  if (category === '치킨, 피자, 햄버거') return '패스트푸드';
  if (category === '커피, 디저트, 빵') return '디저트';

  return category.split(', ').join(',');
};
