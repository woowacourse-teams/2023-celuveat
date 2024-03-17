import { AxiosError } from 'axios';
import { shallow } from 'zustand/shallow';
import { useModalStore } from 'celuveat-ui-library';
import { Query, useMutation, useQueryClient } from '@tanstack/react-query';

import { postRestaurantLike } from '~/api/user';

import useBooleanState from '~/hooks/useBooleanState';
import useToastState from '~/hooks/store/useToastState';

import type { Restaurant } from '~/@types/restaurant.types';

import LoginModal from '~/components/LoginModal';

const useToggleLikeNotUpdate = (restaurant: Restaurant) => {
  const { openModal } = useModalStore();

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
      if (error.response.status < 500) {
        openModal({ title: '로그인 하기', content: <LoginModal /> });
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

  return { isLiked, toggleRestaurantLike };
};

export default useToggleLikeNotUpdate;
