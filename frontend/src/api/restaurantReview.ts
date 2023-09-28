import type { RestaurantReviewPatchBody, RestaurantReviewPostBody } from '~/@types/api.types';
import { apiUserClient } from './apiClient';

export const getRestaurantReview = async (id: string) => {
  const response = await apiUserClient.get(`/reviews?restaurantId=${id}`);
  return response.data;
};

export const postRestaurantReview = async (body: RestaurantReviewPostBody) => {
  const response = await apiUserClient.post(`/reviews`, body);
  return response;
};

export const deleteRestaurantReview = async (reviewId: number) => {
  const response = await apiUserClient.delete(`/reviews/${reviewId}`);
  return response;
};

export const postRestaurantReviewLike = async (reviewId: number) => {
  const response = await apiUserClient.post(`/reviews/${reviewId}/like`);
  return response;
};

export const postRestaurantReviewReport = async (reviewId: number) => {
  const response = await apiUserClient.post(`/reviews/${reviewId}/report`);
  return response;
};

export const patchRestaurantReview = async ({
  reviewId,
  body,
}: {
  reviewId: number;
  body: RestaurantReviewPatchBody;
}) => {
  const response = await apiUserClient.patch(`/reviews/${reviewId}`, body);
  return response;
};
