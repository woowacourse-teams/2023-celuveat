import { userInstance, userMSWInstance } from '~/api/User';

import type { Oauth } from '~/@types/oauth.types';
import type { RestaurantReviewPatchBody, RestaurantReviewPostBody } from '~/@types/api.types';

export const getAccessToken = async (type: Oauth, code: string) => {
  const response = await userInstance.get(`/oauth/login/${type}?code=${code}`);
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
  const response = await userInstance.get('/members/my');
  return response.data;
};

export const getMSWProfile = async () => {
  const response = await userMSWInstance.get('/members/my');
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

export const postRestaurantReview = async (body: RestaurantReviewPostBody) => {
  const response = await userInstance.post(`/reviews`, body);
  return response;
};

export const postMSWRestaurantReview = async (body: RestaurantReviewPostBody) => {
  const response = await userMSWInstance.post(`/reviews`, body);
  return response;
};

export const patchRestaurantReview = async ({
  reviewId,
  body,
}: {
  reviewId: number;
  body: RestaurantReviewPatchBody;
}) => {
  const response = await userInstance.patch(`/reviews/${reviewId}`, body);
  return response;
};

export const patchMSWRestaurantReview = async ({
  reviewId,
  body,
}: {
  reviewId: number;
  body: RestaurantReviewPatchBody;
}) => {
  const response = await userMSWInstance.patch(`/reviews/${reviewId}`, body);
  return response;
};

export const deleteRestaurantReview = async (reviewId: number) => {
  const response = await userInstance.delete(`/reviews/${reviewId}`);
  return response;
};

export const deleteMSWRestaurantReview = async (reviewId: number) => {
  const response = await userMSWInstance.delete(`/reviews/${reviewId}`);
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
