import { AxiosError } from 'axios';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import { shallow } from 'zustand/shallow';

import {
  deleteRestaurantReview,
  getRestaurantReview,
  patchRestaurantReview,
  postRestaurantReview,
  postRestaurantReviewLike,
  postRestaurantReviewReport,
} from '~/api/restaurantReview';

import useToastState from '~/hooks/store/useToastState';

import type { RestaurantReviewData } from '~/@types/api.types';

const useRestaurantReview = () => {
  const queryClient = useQueryClient();
  const { id: restaurantId } = useParams();
  const {
    onSuccess: onSuccessForReview,
    onFailure: onFailureForReview,
    close,
  } = useToastState(
    state => ({
      onFailure: state.onFailure,
      onSuccess: state.onSuccess,
      close: state.close,
    }),
    shallow,
  );

  const errorHandler = (error: AxiosError) => {
    switch (error.response.status) {
      case 401:
        onFailureForReview('로그인 후 이용 가능합니다.');
        break;
      default:
        onFailureForReview(error.response.data as string);
        break;
    }
  };

  const { data: restaurantReviewsData, isLoading } = useQuery<RestaurantReviewData>({
    queryKey: ['restaurantReview', restaurantId],
    queryFn: () => getRestaurantReview(restaurantId),
    suspense: true,
  });

  const createReview = useMutation({
    mutationFn: postRestaurantReview,
    onError: errorHandler,
  });

  const updateReview = useMutation({
    mutationFn: patchRestaurantReview,
    onError: errorHandler,
  });

  const deleteReview = useMutation({
    mutationFn: (reviewId: number) => deleteRestaurantReview(reviewId),
    onError: errorHandler,
  });

  const postReviewLike = useMutation({
    mutationFn: postRestaurantReviewLike,
    onMutate: async reviewId => {
      await queryClient.cancelQueries(['restaurantReview']);
      const previousReviews: RestaurantReviewData = queryClient.getQueryData(['restaurantReview', restaurantId]);

      let isLikedFlag = null;

      queryClient.setQueryData(['restaurantReview', restaurantId], (oldReviewsQueryData: RestaurantReviewData) => {
        const newReviewListData = oldReviewsQueryData?.reviews.map(reviewItem => {
          if (reviewItem.id === reviewId) {
            const newLikeCount = reviewItem.isLiked ? reviewItem.likeCount + 1 : reviewItem.likeCount - 1;
            isLikedFlag = !reviewItem.isLiked;

            return {
              ...reviewItem,
              isLiked: !reviewItem.isLiked,
              likeCount: newLikeCount,
            };
          }

          return reviewItem;
        });

        return { ...oldReviewsQueryData, reviews: newReviewListData };
      });

      return { previousReviews, isLikedFlag };
    },

    onSuccess: (data, reviewId, context) => {
      if (context.isLikedFlag === null) {
        return;
      }
      const message = `해당 리뷰를 추천 ${context.isLikedFlag ? '했습니다' : '취소 했습니다'}`;
      onSuccessForReview(message);
    },

    onError: (error: AxiosError, reviewId, context) => {
      queryClient.setQueryData(['restaurantReview', restaurantId], context.previousReviews);
      errorHandler(error);
    },

    onSettled: () => {
      queryClient.invalidateQueries(['restaurantReview', restaurantId]);
    },
  });

  const postReviewReport = useMutation({
    mutationFn: postRestaurantReviewReport,
    onError: errorHandler,
  });

  const getReviewIsLiked = (reviewId: number) => {
    const review = restaurantReviewsData.reviews.find(reviewItem => reviewItem.id === reviewId);

    return review ? review.isLiked : null;
  };

  const toggleRestaurantReviewLike = (reviewId: number) => {
    postReviewLike.mutate(reviewId);
    close();
  };

  return {
    isLoading,
    restaurantReviewsData,
    getReviewIsLiked,
    toggleRestaurantReviewLike,
    createReview: createReview.mutate,
    updateReview: updateReview.mutate,
    deleteReview: deleteReview.mutate,
    postReviewLike: postReviewLike.mutate,
    postReviewReport: postReviewReport.mutate,
  };
};

export default useRestaurantReview;
