import { styled } from 'styled-components';

import { useState } from 'react';
import { FONT_SIZE } from '~/styles/common';

import useRestaurantReview from '~/hooks/server/useRestaurantReview';

import TextButton from '~/components/@common/Button';

interface ReviewReportFormProps {
  reviewId: number;
}

function ReviewReportForm({ reviewId }: ReviewReportFormProps) {
  const { postReviewReport, isSubmitRequesting } = useRestaurantReview();

  const [text, setText] = useState('');

  const isSubmitDisabled = text.length === 0 || isSubmitRequesting;

  const onChange: React.ChangeEventHandler<HTMLTextAreaElement> = e => {
    setText(e.target.value);
  };

  const onReportReview: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.preventDefault();

    postReviewReport({ reviewId, content: text });
  };

  return (
    <StyledReviewFormContainer>
      <StyledTextArea placeholder="신고 사유를 작성해주세요" value={text} onChange={onChange} />
      <TextButton type="submit" onClick={onReportReview} text="신고하기" colorType="dark" disabled={isSubmitDisabled} />
    </StyledReviewFormContainer>
  );
}

export default ReviewReportForm;

const StyledReviewFormContainer = styled.form`
  display: flex;
  flex-direction: column;
  gap: 2rem 0;

  width: 100%;
`;

const StyledTextArea = styled.textarea`
  height: 300px;

  padding: 0.8rem;

  border: 5px solid var(--gray-2);
  border-radius: 10px;
  background-color: var(--gray-2);

  font-size: ${FONT_SIZE.md};
  text-align: start;
  resize: vertical;

  &:focus {
    border: 5px solid #ff7b54;
  }
`;
