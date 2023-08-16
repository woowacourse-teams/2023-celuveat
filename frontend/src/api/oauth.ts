import { apiClient } from '~/api';
import { userInstance, userMSWInstance } from '~/api/User';

import type { Oauth } from '~/@types/oauth.types';
import type { RestaurantReviewReqBody } from '~/@types/api.types';

export const getAccessToken = async (type: Oauth, code: string) => {
  const response = await apiClient.get(`/oauth/login/${type}?code=${code}`);
  return response.data;
};

export const getLogout = async (type: Oauth) => {
  const response = await userInstance.get(`/oauth/logout/${type}`);
  return response.data;
};

export const getMSWLogout = async (type: Oauth) => {
  const response = await userMSWInstance.get(`/oauth/logout/${type}`);
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
  await userInstance.post(`/restaurants/${restaurantId}/like`);
};

export const postMSWRestaurantLike = async (restaurantId: number) => {
  const response = await userMSWInstance.post(`/restaurants/${restaurantId}/like`);
  return response;
};

export const postRestaurantReview = async ({
  restaurantId,
  body,
}: {
  restaurantId: number;
  body: RestaurantReviewReqBody;
}) => {
  const response = await userInstance.post(`/restaurants/${restaurantId}/reviews`, body);
  return response;
};

export const postMSWRestaurantReview = async ({
  restaurantId,
  body,
}: {
  restaurantId: number;
  body: RestaurantReviewReqBody;
}) => {
  const response = await userMSWInstance.post(`/restaurants/${restaurantId}/reviews`, body);
  return response;
};

export const patchRestaurantReview = async ({
  restaurantId,
  reviewId,
  body,
}: {
  restaurantId: number;
  reviewId: number;
  body: RestaurantReviewReqBody;
}) => {
  const response = await userInstance.patch(`/restaurants/${restaurantId}/reviews/${reviewId}`, body);
  return response;
};

export const patchMSWRestaurantReview = async ({
  restaurantId,
  reviewId,
  body,
}: {
  restaurantId: number;
  reviewId: number;
  body: RestaurantReviewReqBody;
}) => {
  const response = await userMSWInstance.patch(`/restaurants/${restaurantId}/reviews/${reviewId}`, body);
  return response;
};

export const deleteRestaurantReview = async ({
  restaurantId,
  reviewId,
}: {
  restaurantId: number;
  reviewId: number;
}) => {
  const response = await userInstance.delete(`/restaurants/${restaurantId}/reviews/${reviewId}`);
  return response;
};

export const deleteMSWRestaurantReview = async ({
  restaurantId,
  reviewId,
}: {
  restaurantId: number;
  reviewId: number;
}) => {
  const response = await userMSWInstance.delete(`/restaurants/${restaurantId}/reviews/${reviewId}`);
  return response;
};

export const deleteUserData = async (type: Oauth) => {
  const response = await userInstance.delete(`/oauth/withdraw/${type}`);
  return response.data;
};

export const deleteMSWUserData = async (type: Oauth) => {
  const response = await userMSWInstance.delete(`/oauth/withdraw/${type}`);
  return response.data;
};
