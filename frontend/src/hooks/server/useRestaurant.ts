import useAPIClient from './useAPIClient';
import getQueryString from '~/utils/getQueryString';

import type { RestaurantListData } from '~/@types/api.types';
import type { CoordinateBoundary } from '~/@types/map.types';
import type { RestaurantCategory } from '~/@types/restaurant.types';

export interface GetRestaurantsQueryParams {
  boundary: CoordinateBoundary;
  celebId: number;
  category: RestaurantCategory;
  page: number;
  sort: 'distance' | 'like';
}

const useRestaurant = () => {
  const { apiClient } = useAPIClient();

  const getRestaurants = async (queryParams: GetRestaurantsQueryParams) => {
    const queryString = getQueryString(queryParams);
    const response = await apiClient.get<RestaurantListData>(`/restaurants?${queryString}`);

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

  const postRevisedInfo = async ({ restaurantId, data }: { restaurantId: number; data: { contents: string[] } }) => {
    const response = await apiClient.post(`/restaurants/${restaurantId}/correction`, data);
    return response.data;
  };

  return {
    getRestaurants,
    getRestaurantDetail,
    getNearByRestaurant,
    getRestaurantVideo,
    postRevisedInfo,
  };
};

export default useRestaurant;