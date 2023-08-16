import axios from 'axios';
import { Celeb } from '../@types/celeb.types';

import type { RestaurantListData } from '~/@types/api.types';
import { CoordinateBoundary } from '~/@types/map.types';
import { RestaurantCategory } from '~/@types/restaurant.types';
import getQueryString from '~/utils/getQueryString';

export interface GetRestaurantsQueryParams {
  boundary: CoordinateBoundary;
  celebId: number;
  category: RestaurantCategory;
  page: number;
}

export interface GetRestaurantDetailQueryParams {
  restaurantId: string;
  celebId: string;
}

export const apiClient = axios.create({
  baseURL: `${process.env.BASE_URL}/api`,
  headers: {
    'Content-type': 'application/json',
  },
});

const apiMSWClient = axios.create({
  baseURL: `/`,
  headers: {
    'Content-type': 'application/json',
  },
});

export const getRestaurants = async (queryParams: GetRestaurantsQueryParams) => {
  const queryString = getQueryString(queryParams);

  const response = await apiClient.get<RestaurantListData>(`/restaurants?${queryString}`);

  return response.data;
};

export const getMSWRestaurants = async (queryParams: GetRestaurantsQueryParams) => {
  const queryString = getQueryString(queryParams);

  const response = await apiMSWClient.get<RestaurantListData>(`/restaurants?${queryString}`);

  return response.data;
};

export const getCelebs = async () => {
  const response = await apiClient.get<Celeb[]>('/celebs');
  return response.data;
};

export const getRestaurantDetail = async (restaurantId: string, celebId: string) => {
  const response = await apiClient.get(`/restaurants/${restaurantId}?celebId=${celebId}`);
  return response.data;
};

export const getNearByRestaurant = async (restaurantId: string) => {
  const response = await apiClient.get(`/restaurants/${restaurantId}/nearby`);
  return response.data;
};

export const getRestaurantVideo = async (restaurantId: string) => {
  const response = await apiClient.get(`/videos?restaurantId=${restaurantId}`);
  return response.data;
};

export const getCelebVideo = async (celebId: string) => {
  const response = await apiClient.get(`/videos?celebId=${celebId}`);
  return response.data;
};

export const postRevisedInfo = async ({ restaurantId, data }: { restaurantId: number; data: string[] }) => {
  const response = await apiClient.post(`/restaurants/${restaurantId}/correction`, data);
  return response.data;
};

export const getRestaurantReview = async (restaurantId: string) => {
  const response = await apiClient.get(`/reviews?restaurantId=${restaurantId}`);
  return response.data;
};

export const getMSWRestaurantReview = async (restaurantId: string) => {
  const response = await apiMSWClient.get(`/reviews?restaurantId=${restaurantId}`);
  return response.data;
};
