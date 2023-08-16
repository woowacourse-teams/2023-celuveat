import { useQueryClient } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import { RestaurantReviewData } from '~/@types/api.types';
import useRestaurantReview from '~/hooks/server/useRestaurantReview';
import useModalState from '~/hooks/store/useModalState';
import TextButton from '../@common/Button';
import { FONT_SIZE } from '~/styles/common';

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

  const onChange: React.ChangeEventHandler<HTMLTextAreaElement> = e => {
    setText(e.target.value);
  };

  useEffect(() => {
    if (type === 'update' && !!reviewData) {
      const targetReview = reviewData.reviews.find(review => review.id === reviewId);
      setText(targetReview.content);
    }
  }, [reviewData]);

  return (
    <StyledReviewFormContainer>
      <textarea placeholder="여기에 리뷰를 적어주세요." value={text} onChange={onChange} />
      {type === 'create' && (
        <TextButton
          type="submit"
          onClick={onCreateReview}
          text="등록하기"
          colorType="dark"
          disabled={text.length === 0}
        />
      )}
      {type === 'update' && (
        <TextButton
          type="submit"
          onClick={onUpdateReview}
          text="수정하기"
          colorType="dark"
          disabled={text.length === 0}
        />
      )}
    </StyledReviewFormContainer>
  );
}

export default ReviewForm;

const StyledReviewFormContainer = styled.form`
  display: flex;
  flex-direction: column;
  gap: 3.6rem 0;

  width: 100%;

  & > textarea {
    height: 30vh;
    resize: vertical;

    font-size: ${FONT_SIZE.md};
    text-align: start;
  }
`;
