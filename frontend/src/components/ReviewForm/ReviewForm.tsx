import { useQueryClient } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { RestaurantReviewData } from '~/@types/api.types';
import useRestaurantReview from '~/hooks/server/useRestaurantReview';
import useModalState from '~/hooks/store/useModalState';

interface ReviewFormProps {
  type: 'create' | 'update';
}

function ReviewForm({ type }: ReviewFormProps) {
  const { id: restaurantId } = useParams();
  const qc = useQueryClient();

  const reviewId = useModalState(state => state.targetId);
  const reviewData: RestaurantReviewData = qc.getQueryData(['restaurantReview', restaurantId]);

  const [text, setText] = useState('');

  const { createReview, updateReview } = useRestaurantReview();

  const onCreateReview: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.preventDefault(); // 네트워크 요청 확인을 위해 사용
    createReview({ content: text, restaurantId: Number(restaurantId) });
  };

  const onUpdateReview: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.preventDefault(); // 네트워크 요청 확인을 위해 사용
    updateReview({ reviewId, body: { content: text } });
  };

  const onChange: React.ChangeEventHandler<HTMLInputElement> = e => {
    setText(e.target.value);
  };

  useEffect(() => {
    if (type === 'update' && !!reviewData) {
      const targetReview = reviewData.reviews.find(review => review.id === reviewId);
      setText(targetReview.content);
    }
  }, [reviewData]);

  return (
    <form>
      <input type="textarea" value={text} onChange={onChange} />
      {type === 'create' && (
        <button type="submit" onClick={onCreateReview}>
          제출
        </button>
      )}
      {type === 'update' && (
        <button type="submit" onClick={onUpdateReview}>
          수정하기
        </button>
      )}
    </form>
  );
}

export default ReviewForm;
