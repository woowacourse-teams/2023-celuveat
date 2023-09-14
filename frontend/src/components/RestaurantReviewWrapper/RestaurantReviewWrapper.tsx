import { useMemo } from 'react';
import styled from 'styled-components';

import RestaurantReviewList from '~/components/RestaurantReviewList';
import { Modal, ModalContent } from '~/components/@common/Modal';
import LoginModalContent from '~/components/LoginModalContent';
import ReviewForm from '../ReviewForm/ReviewForm';
import Pencil from '~/assets/icons/pencil.svg';

import useRestaurantReview from '~/hooks/server/useRestaurantReview';
import { useReviewModalContext } from '~/hooks/context/ReviewModalProvider';
import { FONT_SIZE } from '~/styles/common';
import { isMoreThan } from '~/utils/compare';
import { REVIEW_SHOW_COUNT } from '~/constants/options';

function RestaurantReviewWrapper() {
  const { restaurantReviewsData } = useRestaurantReview();
  const { formType, isModalOpen, openModal, closeModal, openCreateReview, openShowAll } = useReviewModalContext();
  const { totalElementsCount: reviewCount, reviews } = restaurantReviewsData;

  const previewReviews = useMemo(() => reviews.slice(0, REVIEW_SHOW_COUNT), [reviews]);
  const isMoreReviews = isMoreThan(reviewCount, REVIEW_SHOW_COUNT);

  const getTitle = (type: string) => {
    if (type === '') return '로그인';
    if (type === 'create') return '리뷰 작성하기';
    if (type === 'all') return '리뷰 모두 보기';
    return null;
  };

  return (
    <>
      <StyledRestaurantReviewWrapper>
        <StyledReviewCountText>리뷰 {reviewCount ? `${reviewCount}개` : '없음'}</StyledReviewCountText>
        <RestaurantReviewList reviews={previewReviews} />
        <StyledButtonContainer>
          <StyledButton type="button" onClick={openCreateReview}>
            <Pencil width={20} />
            리뷰 작성하기
          </StyledButton>
          {isMoreReviews && (
            <StyledButton type="button" onClick={openShowAll}>{`리뷰 ${reviewCount}개 모두 보기`}</StyledButton>
          )}
        </StyledButtonContainer>
      </StyledRestaurantReviewWrapper>

      <Modal isOpen={isModalOpen} open={openModal} close={closeModal}>
        <ModalContent title={getTitle(formType)}>
          <>
            {formType === '' && <LoginModalContent />}
            {formType === 'create' && <ReviewForm type="create" />}
            {formType === 'all' && (
              <RestaurantReviewList reviews={restaurantReviewsData.reviews} isModal={isModalOpen} />
            )}
          </>
        </ModalContent>
      </Modal>
    </>
  );
}

export default RestaurantReviewWrapper;

const StyledRestaurantReviewWrapper = styled.article`
  margin-bottom: 4rem;
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
