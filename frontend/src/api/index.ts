import axios from 'axios';
import getQueryString from '~/utils/getQueryString';

import { userInstance, userMSWInstance } from './User';

import type { Celeb } from '~/@types/celeb.types';
import type { RestaurantListData } from '~/@types/api.types';
import type { CoordinateBoundary } from '~/@types/map.types';
import type { RestaurantCategory } from '~/@types/restaurant.types';

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
  baseURL: `${process.env.BASE_URL}`,
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

  const response = await apiClient.get<RestaurantListData>(`/restaurants?sort=like&${queryString}`);

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

export const getMSWCelebs = async () => {
  const response = await apiMSWClient.get<Celeb[]>('/celebs');
  return response.data;
};

export const getRestaurantDetail = async (restaurantId: string, celebId: string) => {
  const response = await userInstance.get(`/restaurants/${restaurantId}?celebId=${celebId}`);
  return response.data;
};

export const getMSWRestaurantDetail = async (restaurantId: string, celebId: string) => {
  const response = await userMSWInstance.get(`/restaurants/${restaurantId}?celebId=${celebId}`);
  return response.data;
};

export const getNearByRestaurant = async (restaurantId: string) => {
  const response = await userInstance.get(`/restaurants/${restaurantId}/nearby`);
  return response.data;
};

export const getMSWNearByRestaurant = async (restaurantId: string) => {
  const response = await userMSWInstance.get(`/restaurants/${restaurantId}/nearby`);
  return response.data;
};

export const getRestaurantVideo = async (restaurantId: string) => {
  const response = await apiClient.get(`/videos?restaurantId=${restaurantId}`);
  return response.data;
};

export const getMSWRestaurantVideo = async (restaurantId: string) => {
  const response = await apiMSWClient.get(`/videos?restaurantId=${restaurantId}`);
  return response.data;
};

export const getCelebVideo = async (celebId: string) => {
  const response = await apiClient.get(`/videos?celebId=${celebId}`);
  return response.data;
};

export const getMSWCelebVideo = async (celebId: string) => {
  const response = await apiMSWClient.get(`/videos?celebId=${celebId}`);
  return response.data;
};

export const postRevisedInfo = async ({
  restaurantId,
  data,
}: {
  restaurantId: number;
  data: { contents: string[] };
}) => {
  const response = await apiClient.post(`/restaurants/${restaurantId}/correction`, data);
  return response.data;
};

export const postMSWRevisedInfo = async ({
  restaurantId,
  data,
}: {
  restaurantId: number;
  data: { contents: string[] };
}) => {
  const response = await apiMSWClient.post(`/restaurants/${restaurantId}/correction`, data);
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
