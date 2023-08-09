import { shallow } from 'zustand/shallow';
import { AxiosError } from 'axios';
import { useCallback } from 'react';
import { useQueryClient, useMutation } from '@tanstack/react-query';
import useToastState from '~/hooks/store/useToastState';

import type { Restaurant } from '../../@types/restaurant.types';
import type { RestaurantListData } from '../../@types/api.types';
import useBooleanState from '~/hooks/useBooleanState';
import { userMSWInstance } from '~/api/User';

const useToggleRestaurantLike = (restaurant: Restaurant) => {
  const queryClient = useQueryClient();
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);
  const { onSuccess, onFailure, close } = useToastState(
    state => ({
      onSuccess: state.onSuccess,
      onFailure: state.onFailure,
      close: state.close,
    }),
    shallow,
  );

  const toggleLike = useMutation({
    mutationFn: async (restaurantId: number) => userMSWInstance.post(`/restaurants/${restaurantId}/like`),
    onMutate: () => {
      close();

      const previousRestaurantListData: RestaurantListData = queryClient.getQueryData(['restaurants']);
      const newRestaurantListData = previousRestaurantListData?.content.map(restaurantItem =>
        restaurantItem.id === restaurant.id ? { ...restaurantItem, isLiked: !restaurantItem.isLiked } : restaurantItem,
      );

      queryClient.setQueryData(['restaurant'], newRestaurantListData);

      return { previousRestaurantListData };
    },

    onError: (error: AxiosError, newData, context) => {
      if (error.response.status < 500) {
        openModal();
      } else {
        onFailure(error.response.data as string);
      }

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
  }, []);

  return { isModalOpen, closeModal, toggleRestaurantLike };
};

export default useToggleRestaurantLike;
