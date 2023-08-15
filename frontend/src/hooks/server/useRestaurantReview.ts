import { AxiosError } from 'axios';
import { useQuery, useMutation } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import { shallow } from 'zustand/shallow';
import { patchMSWRestaurantReview, deleteMSWRestaurantReview, postMSWRestaurantReview } from '../../api/oauth';
import { RestaurantReview, RestaurantReviewReqBody } from '~/@types/api.types';
import { getMSWRestaurantReview } from '~/api';
import useToastState from '~/hooks/store/useToastState';

const useRestaurantReview = () => {
  const { id: restaurantId } = useParams();

  const { onFailure } = useToastState(
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

  const { data: restaurantReviews } = useQuery<RestaurantReview[]>({
    queryKey: ['restaurantReview', restaurantId],
    queryFn: () => getMSWRestaurantReview(restaurantId),
  });

  const createReview = useMutation({
    mutationFn: (body: RestaurantReviewReqBody) =>
      postMSWRestaurantReview({ restaurantId: Number(restaurantId), body }),
    onError: errorHandler,
  });

  const updateReview = useMutation({
    mutationFn: ({ reviewId, body }: { reviewId: number; body: RestaurantReviewReqBody }) =>
      patchMSWRestaurantReview({ restaurantId: Number(restaurantId), reviewId, body }),
    onError: errorHandler,
  });

  const deleteReview = useMutation({
    mutationFn: (reviewId: number) => deleteMSWRestaurantReview({ restaurantId: Number(restaurantId), reviewId }),
    onError: errorHandler,
  });

  return {
    restaurantReviews,
    createReview: createReview.mutate,
    updateReview: updateReview.mutate,
    deleteReview: deleteReview.mutate,
  };
};

export default useRestaurantReview;
