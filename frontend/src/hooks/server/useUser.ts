import { Oauth } from '~/@types/oauth.types';
import useAPIClient from './useAPIClient';

const useUser = () => {
  const { apiUserClient } = useAPIClient();

  const getAccessToken = async (type: Oauth, code: string) => {
    const response = await apiUserClient.get(`/oauth/login/${type}?code=${code}`);
    return response.data;
  };

  const getLogout = async (type: Oauth) => {
    const response = await apiUserClient.get(`/oauth/logout/${type}`);
    return response.data;
  };

  const getProfile = async () => {
    const response = await apiUserClient.get('/members/my');
    return response.data;
  };

  const getRestaurantWishList = async () => {
    const response = await apiUserClient.get('/restaurants/like');
    return response.data;
  };

  const postRestaurantLike = async (restaurantId: number) => {
    await apiUserClient.post(`/restaurants/${restaurantId}/like`);
  };

  const deleteUserData = async (type: Oauth) => {
    const response = await apiUserClient.delete(`/oauth/withdraw/${type}`);
    return response.data;
  };

  return { getAccessToken, getLogout, getProfile, getRestaurantWishList, postRestaurantLike, deleteUserData };
};

export default useUser;
