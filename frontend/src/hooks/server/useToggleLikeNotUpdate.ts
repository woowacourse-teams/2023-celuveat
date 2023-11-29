import { shallow } from 'zustand/shallow';
import { Query, useMutation, useQueryClient } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import { Restaurant } from '../../@types/restaurant.types';
import useToastState from '~/hooks/store/useToastState';
import useBooleanState from '~/hooks/useBooleanState';
import { postRestaurantLike } from '~/api/user';

const useToggleLikeNotUpdate = (restaurant: Restaurant) => {
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);
  const { value: isLiked, toggle: toggleIsLiked } = useBooleanState(restaurant.isLiked ?? true);
  const { onSuccess, onFailure, close } = useToastState(
    state => ({
      onSuccess: state.onSuccess,
      onFailure: state.onFailure,
      close: state.close,
    }),
    shallow,
  );
  const queryClient = useQueryClient();

  const toggleLike = useMutation({
    mutationFn: postRestaurantLike,

    onMutate: toggleIsLiked,

    onError: (error: AxiosError) => {
      if (error.response.status === 429) {
        toggleIsLiked();
      }
      if (error.response.status === 401) {
        openModal();
        toggleIsLiked();
      } else {
        onFailure(error.response.data as string);
      }
    },

    onSuccess: () => {
      const message = `위시리스트에 ${isLiked ? '저장' : '삭제'}됨.`;
      const imgUrl = restaurant.images[0].name;

      onSuccess(message, { url: imgUrl, alt: `좋아요한 ${restaurant.name}` });
      return queryClient.invalidateQueries({
        queryKey: ['restaurants'],
        predicate: (query: Query<unknown, unknown, unknown, [string, { type: string }]>) =>
          query.queryKey[0] === 'restaurants' && query.queryKey[1]?.type !== 'wish-list',
      });
    },
   });

  const toggleRestaurantLike = () => {
    toggleLike.mutate(restaurant.id);
    close();
  };

  return { isModalOpen, closeModal, openModal, isLiked, toggleRestaurantLike, toggleLike };
};

export default useToggleLikeNotUpdate;
