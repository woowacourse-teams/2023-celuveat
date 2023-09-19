import { apiClient } from './apiClient';
import type { Celeb } from '~/@types/celeb.types';

export const getCelebs = async () => {
  const response = await apiClient.get<Celeb[]>('/celebs');
  return response.data;
};

export const getCelebVideo = async (celebId: string) => {
  const response = await apiClient.get(`/videos?celebId=${celebId}`);
  return response.data;
};
