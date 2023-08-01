import axios from 'axios';
import { Celeb } from '../@types/celeb.types';
/* eslint-disable import/prefer-default-export */

import type { RestaurantListData } from '~/@types/api.types';
import { CoordinateBoundary } from '~/@types/map.types';
import { RestaurantCategory } from '~/@types/restaurant.types';
import getQueryString from '~/utils/getQueryString';

export interface GetRestaurantsQueryParams {
  boundary: CoordinateBoundary;
  celebId: number;
  category: RestaurantCategory;
}

const apiClient = axios.create({
  baseURL: `${process.env.BASE_URL}/api`,
  headers: {
    'Content-type': 'application/json',
  },
});

export const getRestaurants = async (queryParams: GetRestaurantsQueryParams) => {
  const queryString = getQueryString(queryParams);
  const response = await apiClient.get<RestaurantListData>(`/restaurants?${queryString}`);
  return response.data;
};

export const getCelebs = async () => {
  const response = await apiClient.get<Celeb[]>('/celebs');
  return response.data;
};
