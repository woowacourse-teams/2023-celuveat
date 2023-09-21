import { apiUserClient } from './apiClient';
import type { Oauth } from '~/@types/oauth.types';

export const getAccessToken = async (type: Oauth, code: string) => {
  const response = await apiUserClient.get(`/oauth/login/${type}?code=${code}`);
  return response.data;
};

export const getLogout = async (type: Oauth) => {
  const response = await apiUserClient.get(`/oauth/logout/${type}`);
  return response.data;
};

export const getProfile = async () => {
  const response = await apiUserClient.get('/members/my');
  return response.data;
};

export const getRestaurantWishList = async () => {
  const response = await apiUserClient.get('/restaurants/like');
  return response.data;
};

export const postRestaurantLike = async (restaurantId: number) => {
  await apiUserClient.post(`/restaurants/${restaurantId}/like`);
};

export const deleteUserData = async (type: Oauth) => {
  const response = await apiUserClient.delete(`/oauth/withdraw/${type}`);
  return response.data;
};
