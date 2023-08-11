import { shallow } from 'zustand/shallow';
import { useMutation } from '@tanstack/react-query';
import { useCallback } from 'react';
import { AxiosError } from 'axios';
import { userMSWInstance } from '~/api/User';
import { Restaurant } from '../../@types/restaurant.types';
import useToastState from '~/hooks/store/useToastState';
import useBooleanState from '~/hooks/useBooleanState';

const useToggleLikeNotUpdate = (restaurant: Restaurant) => {
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);
  const { value: isLiked, toggle: toggleIsLiked } = useBooleanState(restaurant.isLiked);
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

    onError: (error: AxiosError) => {
      if (error.response.status < 500) {
        openModal();
      } else {
        onFailure(error.response.data as string);
      }
    },

    onSuccess: () => {
      const message = `위시리스트에 ${!isLiked ? '저장' : '삭제'}됨.`;
      const imgUrl = restaurant.images[0].name;

      toggleIsLiked();
      onSuccess(message, imgUrl);
    },
  });

  const toggleRestaurantLike = useCallback(() => {
    toggleLike.mutate(restaurant.id);
    close();
  }, []);

  return { isModalOpen, closeModal, isLiked, toggleRestaurantLike };
};

export default useToggleLikeNotUpdate;
