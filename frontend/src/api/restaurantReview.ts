import { apiUserClient, apiUserFilesClient } from './apiClient';

export const getRestaurantReview = async (id: string) => {
  const response = await apiUserClient.get(`/reviews?restaurantId=${id}`);
  return response.data;
};

export const postRestaurantReview = async (body: FormData) => {
  const response = await apiUserFilesClient.post(`/reviews`, body);
  return response;
};

export const patchRestaurantReview = async ({ reviewId, body }: { reviewId: number; body: FormData }) => {
  const response = await apiUserFilesClient.patch(`/reviews/${reviewId}`, body);
  return response;
};

export const deleteRestaurantReview = async (reviewId: number) => {
  const response = await apiUserFilesClient.delete(`/reviews/${reviewId}`);
  return response;
};

export const postRestaurantReviewLike = async (reviewId: number) => {
  const response = await apiUserClient.post(`/reviews/${reviewId}/like`);
  return response;
};

export const postRestaurantReviewReport = async ({ reviewId, content }: { reviewId: number; content: string }) => {
  const response = await apiUserClient.post(`/reviews/${reviewId}/report`, { content });
  return response;
};
