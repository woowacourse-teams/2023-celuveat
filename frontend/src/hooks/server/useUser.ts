import { Oauth } from '~/@types/oauth.types';
import useAPIClient from './useAPIClient';

const useUser = () => {
  const { apiClient } = useAPIClient();

  const getAccessToken = async (type: Oauth, code: string) => {
    const response = await apiClient.get(`/oauth/login/${type}?code=${code}`);
    return response.data;
  };

  const getLogout = async (type: Oauth) => {
    const response = await apiClient.get(`/oauth/logout/${type}`);
    return response.data;
  };

  const getProfile = async () => {
    const response = await apiClient.get('/members/my');
    return response.data;
  };

  const getRestaurantWishList = async () => {
    const response = await apiClient.get('/restaurants/like');
    return response.data;
  };

  const postRestaurantLike = async (restaurantId: number) => {
    await apiClient.post(`/restaurants/${restaurantId}/like`);
  };

  const deleteUserData = async (type: Oauth) => {
    const response = await apiClient.delete(`/oauth/withdraw/${type}`);
    return response.data;
  };

  return { getAccessToken, getLogout, getProfile, getRestaurantWishList, postRestaurantLike, deleteUserData };
};

export default useUser;
