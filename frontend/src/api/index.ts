import axios from 'axios';
import { Celeb } from '../@types/celeb.types';

import { CoordinateBoundary } from '~/@types/map.types';
import { RestaurantCategory } from '~/@types/restaurant.types';

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

export const getCelebs = async () => {
  const response = await apiClient.get<Celeb[]>('/celebs');
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
