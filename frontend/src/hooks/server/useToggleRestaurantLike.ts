import { shallow } from 'zustand/shallow';
import { AxiosError } from 'axios';
import { useCallback } from 'react';
import { useQueryClient, useMutation } from '@tanstack/react-query';
import useToastState from '~/hooks/store/useToastState';
import useBooleanState from '~/hooks/useBooleanState';
import { postRestaurantLike } from '~/api/oauth';

import type { Restaurant } from '../../@types/restaurant.types';
import type { RestaurantListData } from '../../@types/api.types';

const useToggleRestaurantLike = (restaurant: Restaurant) => {
  const queryClient = useQueryClient();
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);
  const { value: isLiked, toggle: toggleLikeState } = useBooleanState(false);
  const { onSuccess, onFailure, close } = useToastState(
    state => ({
      onSuccess: state.onSuccess,
      onFailure: state.onFailure,
      close: state.close,
    }),
    shallow,
  );

  const toggleLike = useMutation({
    mutationFn: postRestaurantLike,
    onMutate: () => {
      const previousRestaurantListData: RestaurantListData = queryClient.getQueryData(['restaurants']);
      const newRestaurantListData = previousRestaurantListData?.content.map(restaurantItem =>
        restaurantItem.id === restaurant.id ? { ...restaurantItem, isLiked: !restaurantItem.isLiked } : restaurantItem,
      );

      queryClient.setQueryData(['restaurants'], newRestaurantListData);

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
      const imgUrl = restaurant.images[0].name;

      onSuccess(message, imgUrl);
      toggleLikeState();
    },

    onSettled: () => {
      queryClient.invalidateQueries(['restaurants']);
    },
  });

  const toggleRestaurantLike = useCallback(() => {
    toggleLike.mutate(restaurant.id);
    close();
  }, []);

  return { isModalOpen, isLiked, closeModal, toggleRestaurantLike };
};

export default useToggleRestaurantLike;
