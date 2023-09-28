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

import type { RestaurantReviewData, RestaurantReviewPatchBody, RestaurantReviewPostBody } from '../../@types/api.types';

const useRestaurantReview = () => {
  const queryClient = useQueryClient();
  const { id: restaurantId } = useParams();
  const { onFailure, onSuccess: successReviewLike } = useToastState(
    state => ({
      onFailure: state.onFailure,
      onSuccess: state.onSuccess,
    }),
    shallow,
  );

  const errorHandler = (error: AxiosError) => {
    switch (error.response.status) {
      case 401:
        onFailure('로그인 후 이용 가능합니다.');
        break;
      default:
        onFailure(error.response.data as string);
        break;
    }
  };

  const { data: restaurantReviewsData, isLoading } = useQuery<RestaurantReviewData>({
    queryKey: ['restaurantReview', restaurantId],
    queryFn: () => getRestaurantReview(restaurantId),
    suspense: true,
  });

  const createReview = useMutation({
    mutationFn: (body: RestaurantReviewPostBody) => postRestaurantReview(body),
    onError: errorHandler,
  });

  const updateReview = useMutation({
    mutationFn: ({ reviewId, body }: { reviewId: number; body: RestaurantReviewPatchBody }) =>
      patchRestaurantReview({ reviewId, body }),
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

      queryClient.setQueryData(['restaurantReview', restaurantId], (oldReviewsQueryData: RestaurantReviewData) => {
        const newReviewListData = oldReviewsQueryData?.reviews.map(reviewItem =>
          reviewItem.id === reviewId ? { ...reviewItem, isLiked: !reviewItem.isLiked } : reviewItem,
        );

        return { ...oldReviewsQueryData, reviews: newReviewListData };
      });

      return { previousReviews };
    },

    onSuccess: () => {
      successReviewLike('해당 리뷰를 추천하였습니다!!');
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

  return {
    isLoading,
    restaurantReviewsData,
    getReviewIsLiked,
    createReview: createReview.mutate,
    updateReview: updateReview.mutate,
    deleteReview: deleteReview.mutate,
    postReviewLike: postReviewLike.mutate,
    postReviewReport: postReviewReport.mutate,
  };
};

export default useRestaurantReview;
