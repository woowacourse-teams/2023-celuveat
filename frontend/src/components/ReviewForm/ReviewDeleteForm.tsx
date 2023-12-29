import { styled } from 'styled-components';

import { FONT_SIZE } from '~/styles/common';

import useRestaurantReview from '~/hooks/server/useRestaurantReview';

import Alert from '~/assets/icons/alert.svg';

import TextButton from '~/components/@common/Button';

interface ReviewDeleteFormProps {
  reviewId: number;
}

function ReviewDeleteForm({ reviewId }: ReviewDeleteFormProps) {
  const { deleteReview, isSubmitRequesting } = useRestaurantReview();

  const onDeleteReview: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.preventDefault();

    deleteReview(reviewId);
  };

  return (
    <div>
      <StyledWarningMessage>
        <Alert width={32} />
        <p>정말 삭제하시겠습니까? 한 번 삭제된 리뷰는 복구가 불가능합니다.</p>
      </StyledWarningMessage>
      <TextButton
        type="submit"
        onClick={onDeleteReview}
        colorType="dark"
        text="삭제하기"
        width="100%"
        disabled={isSubmitRequesting}
      />
    </div>
  );
}

export default ReviewDeleteForm;

const StyledWarningMessage = styled.p`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2.4rem 0;

  margin: 3.2rem 0;

  font-size: ${FONT_SIZE.md};
  text-align: center;
`;
