import { useQueryClient } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import { RestaurantReviewData } from '~/@types/api.types';
import useRestaurantReview from '~/hooks/server/useRestaurantReview';
import TextButton from '../@common/Button';
import { FONT_SIZE } from '~/styles/common';
import { ReviewSubmitButtonType } from '~/@types/review.types';

const SUBMIT_BUTTON_TEXT = {
  create: '등록하기',
  update: '수정하기',
  report: '신고하기',
} as const;

interface ReviewFormProps {
  type: ReviewSubmitButtonType;
  reviewId?: number;
}

function ReviewForm({ type, reviewId }: ReviewFormProps) {
  const { id: restaurantId } = useParams();
  const qc = useQueryClient();
  const reviewData: RestaurantReviewData = qc.getQueryData(['restaurantReview', restaurantId]);
  const [text, setText] = useState('');

  const { createReview, updateReview, postReviewReport } = useRestaurantReview();

  const onChange: React.ChangeEventHandler<HTMLTextAreaElement> = e => {
    setText(e.target.value);
  };

  const submitReviewForm: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.preventDefault();

    switch (type) {
      case 'create':
        createReview({ content: text, restaurantId: Number(restaurantId) });
        break;
      case 'update':
        updateReview({ reviewId, body: { content: text } });
        break;
      case 'report':
        postReviewReport({ reviewId, content: text });
        break;
      default:
        throw new Error('해당 타입의 review Form은 지원하지 않습니다.');
    }

    window.location.reload();
  };

  useEffect(() => {
    if (type === 'update') {
      const targetReview = reviewData?.reviews.find(review => review.id === reviewId);
      setText(targetReview.content);
    }
  }, [reviewData]);

  return (
    <StyledReviewFormContainer>
      <StyledTextArea placeholder="여기에 리뷰를 적어주세요." value={text} onChange={onChange} />
      <TextButton
        type="submit"
        onClick={submitReviewForm}
        text={SUBMIT_BUTTON_TEXT[type]}
        colorType="dark"
        disabled={text.length === 0}
      />
    </StyledReviewFormContainer>
  );
}

export default ReviewForm;

const StyledReviewFormContainer = styled.form`
  display: flex;
  flex-direction: column;
  gap: 3.6rem 0;

  width: 100%;
`;

const StyledTextArea = styled.textarea`
  height: 30vh;

  padding: 0.8rem;

  border: none;
  border-radius: 10px;
  background-color: var(--gray-2);

  font-size: ${FONT_SIZE.md};
  text-align: start;
  resize: vertical;
`;
