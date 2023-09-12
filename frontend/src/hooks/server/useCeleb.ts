import { Celeb } from '~/@types/celeb.types';
import useAPIClient from './useAPIClient';

const useCeleb = () => {
  const { apiClient } = useAPIClient();

  const getCelebs = async () => {
    const response = await apiClient.get<Celeb[]>('/celebs');
    return response.data;
  };

  const getCelebVideo = async (celebId: string) => {
    const response = await apiClient.get(`/videos?celebId=${celebId}`);
    return response.data;
  };

  return { getCelebs, getCelebVideo };
};

export default useCeleb;
