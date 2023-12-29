import { styled } from 'styled-components';

import Pencil from '~/assets/icons/pencil.svg';

import RestaurantReviewList from '~/components/RestaurantReviewList';

import useRestaurantReview from '~/hooks/server/useRestaurantReview';

import { FONT_SIZE } from '~/styles/common';
import { REVIEW_SHOW_COUNT } from '~/constants/options';
import { ReviewFormType } from '~/@types/review.types';
import useCeluveatModal from '~/hooks/useCeluveatModal';

function RestaurantReviewWrapper() {
  const { restaurantReviewsData } = useRestaurantReview();
  const { openReviewFormModal } = useCeluveatModal();

  const { totalElementsCount: reviewCount } = restaurantReviewsData;
  const isMoreReviews = reviewCount > REVIEW_SHOW_COUNT;

  const onClickOpenModal = (reviewFormType: ReviewFormType) => () => {
    openReviewFormModal({ type: reviewFormType });
  };

  return (
    <StyledRestaurantReviewWrapper>
      <StyledReviewCountText>리뷰 {reviewCount ? `${reviewCount}개` : '없음'}</StyledReviewCountText>
      <RestaurantReviewList isSummary />
      <StyledButtonContainer>
        <StyledButton type="button" onClick={onClickOpenModal('create')}>
          <Pencil width={20} />
          리뷰 작성하기
        </StyledButton>
        {isMoreReviews && (
          <StyledButton
            type="button"
            onClick={onClickOpenModal('all')}
          >{`리뷰 ${reviewCount}개 모두 보기`}</StyledButton>
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
