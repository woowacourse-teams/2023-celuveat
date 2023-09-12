import { GetRestaurantsQueryParams } from '~/api';
import useAPIClient from './useAPIClient';
import getQueryString from '~/utils/getQueryString';

import { RestaurantListData } from '~/@types/api.types';

const useRestaurant = () => {
  const { apiClient } = useAPIClient();

  const getRestaurants = async (queryParams: GetRestaurantsQueryParams) => {
    const queryString = getQueryString(queryParams);
    const response = await apiClient.get<RestaurantListData>(`/restaurants?sort=like&${queryString}`);

    return response.data;
  };

  const getRestaurantDetail = async (restaurantId: string, celebId: string) => {
    const response = await apiClient.get(`/restaurants/${restaurantId}?celebId=${celebId}`);
    return response.data;
  };

  const getNearByRestaurant = async (restaurantId: string) => {
    const response = await apiClient.get(`/restaurants/${restaurantId}/nearby`);
    return response.data;
  };

  const getRestaurantVideo = async (restaurantId: string) => {
    const response = await apiClient.get(`/videos?restaurantId=${restaurantId}`);
    return response.data;
  };

  const getRestaurantReview = async (restaurantId: string) => {
    const response = await apiClient.get(`/reviews?restaurantId=${restaurantId}`);
    return response.data;
  };

  return {
    getRestaurants,
    getRestaurantDetail,
    getNearByRestaurant,
    getRestaurantVideo,
    getRestaurantReview,
  };
};

export default useRestaurant;
