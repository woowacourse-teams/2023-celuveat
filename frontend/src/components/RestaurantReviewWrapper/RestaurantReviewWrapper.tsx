import { styled } from 'styled-components';

import { Modal } from 'celuveat-ui-library';
import Pencil from '~/assets/icons/pencil.svg';

import RestaurantReviewList from '~/components/RestaurantReviewList';

import useRestaurantReview from '~/hooks/server/useRestaurantReview';

import { FONT_SIZE } from '~/styles/common';
import { REVIEW_SHOW_COUNT } from '~/constants/options';

import { ReviewForm } from '~/components/ReviewForm';

function RestaurantReviewWrapper() {
  const { restaurantReviewsData } = useRestaurantReview();

  const { totalElementsCount: reviewCount } = restaurantReviewsData;
  const isMoreReviews = reviewCount > REVIEW_SHOW_COUNT;

  return (
    <StyledRestaurantReviewWrapper>
      <StyledReviewCountText>리뷰 {reviewCount ? `${reviewCount}개` : '없음'}</StyledReviewCountText>
      <RestaurantReviewList isSummary />
      <StyledButtonContainer>
        <Modal.OpenButton isCustom modalTitle="리뷰 작성하기" modalContent={<ReviewForm type="create" />}>
          <StyledButton type="button">
            <Pencil width={20} />
            리뷰 작성하기
          </StyledButton>
        </Modal.OpenButton>
        {isMoreReviews && (
          <Modal.OpenButton isCustom modalTitle="리뷰 모두 보기" modalContent={<RestaurantReviewList />}>
            <StyledButton type="button">{`리뷰 ${reviewCount}개 모두 보기`}</StyledButton>
          </Modal.OpenButton>
        )}
      </StyledButtonContainer>
    </StyledRestaurantReviewWrapper>
  );
}

export default RestaurantReviewWrapper;

const StyledRestaurantReviewWrapper = styled.article`
  margin: 4rem 0;
`;

const StyledReviewCountText = styled.h5`
  margin-bottom: 2rem;
`;

const StyledButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const StyledButton = styled.button`
  display: flex;
  align-items: center;
  gap: 0.4rem;

  padding: 1.3rem 2rem;

  border: none;
  border-radius: 8px;
  background: var(--gray-1);

  font-size: ${FONT_SIZE.md};
  outline: none;
`;
