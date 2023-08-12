import type { Oauth } from '~/@types/oauth.types';
import { apiClient } from '~/api';
import { userInstance, userMSWInstance } from '~/api/User';

export const getAccessToken = async (type: Oauth, code: string) => {
  const response = await apiClient.get(`/oauth/login/${type}?code=${code}`);
  return response.data;
};

export const getLogout = async (type: Oauth) => {
  const response = await userInstance.post(`/oauth/logout/${type}`);
  return response.data;
};

export const getMSWLogout = async (type: Oauth) => {
  const response = await userMSWInstance.post(`/oauth/logout/${type}`);
  return response.data;
};

export const getProfile = async () => {
  const response = await userInstance.get('/profile');
  return response.data;
};

export const getMSWProfile = async () => {
  const response = await userMSWInstance.get('/profile');
  return response.data;
};

export const getRestaurantWishList = async () => {
  const response = await userInstance.get('/restaurants/like');
  return response.data;
};

export const getMSWRestaurantWishList = async () => {
  const response = await userMSWInstance.get('/restaurants/like');
  return response.data;
};

export const postRestaurantLike = async (restaurantId: number) => {
  const response = await userInstance.post(`/restaurants/${restaurantId}/like`);
  return response.data;
};

export const postMSWRestaurantLike = async (restaurantId: number) => {
  const response = await userMSWInstance.post(`/restaurants/${restaurantId}/like`);
  return response.data;
};
