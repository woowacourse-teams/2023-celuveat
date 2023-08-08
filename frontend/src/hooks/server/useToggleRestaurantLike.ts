import { shallow } from 'zustand/shallow';
import { AxiosError } from 'axios';
import { useCallback } from 'react';
import { useQueryClient, useMutation } from '@tanstack/react-query';
import userInstance from '~/api/RestaurantLike';
import useToastState from '~/hooks/store/useToastState';

import type { Restaurant } from '../../@types/restaurant.types';
import type { RestaurantListData } from '../../@types/api.types';

const useToggleRestaurantLike = (restaurant: Restaurant) => {
  const queryClient = useQueryClient();
  const { onSuccess, onFailure, close } = useToastState(
    state => ({
      onSuccess: state.onSuccess,
      onFailure: state.onFailure,
      close: state.close,
    }),
    shallow,
  );

  const toggleLike = useMutation({
    mutationFn: async (restaurantId: number) => userInstance.post(`/restaurants/${restaurantId}/like`),
    onMutate: () => {
      const previousRestaurantListData: RestaurantListData = queryClient.getQueryData(['restaurants']);
      const newRestaurantListData = previousRestaurantListData?.content.map(restaurantItem =>
        restaurantItem.id === restaurant.id ? { ...restaurantItem, isLiked: !restaurantItem.isLiked } : restaurantItem,
      );

      queryClient.setQueryData(['restaurants'], newRestaurantListData);

      return { previousRestaurantListData };
    },

    onError: (error: AxiosError, newData, context) => {
      onFailure(error.response.data as string);

      if (context.previousRestaurantListData) {
        queryClient.setQueriesData(['restaurants'], context.previousRestaurantListData);
      }
    },

    onSuccess: () => {
      const message = `위시리스트에 ${!restaurant.isLiked ? '저장' : '삭제'}됨.`;

      onSuccess(message);
    },

    onSettled: () => {
      queryClient.invalidateQueries(['restaurants']);
    },
  });

  const toggleRestaurantLike = useCallback(() => {
    toggleLike.mutate(restaurant.id);
    close();
  }, []);

  return { toggleRestaurantLike };
};

export default useToggleRestaurantLike;
