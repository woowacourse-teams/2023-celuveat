import { useMemo, useState } from 'react';
import styled from 'styled-components';

import RestaurantReviewList from '~/components/RestaurantReviewList';
import { Modal, ModalContent } from '~/components/@common/Modal';
import { FONT_SIZE } from '~/styles/common';

import useRestaurantReview from '~/hooks/server/useRestaurantReview';
import PopUpContainer from '../PopUpContainer';
import LoginModalContent from '~/components/LoginModalContent';
import useBooleanState from '~/hooks/useBooleanState';
import ReviewForm from '../ReviewForm/ReviewForm';

const REVIEW_SHOW_COUNT = 6;

const isMoreThan = (target: number, standard: number) => target > standard;

function RestaurantReviewWrapper() {
  const { restaurantReviewsData, isLoading } = useRestaurantReview();
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);
  const [formType, setFormType] = useState<string | null>(null);

  const reviewCount = restaurantReviewsData?.totalElementsCount;
  const previewReviews = useMemo(
    () => restaurantReviewsData?.reviews.slice(0, REVIEW_SHOW_COUNT),
    [restaurantReviewsData],
  );
  const isMoreReviews = isMoreThan(reviewCount, REVIEW_SHOW_COUNT);

  // const openAllReviews = () => {
  //   setContent('리뷰 모두 보기');
  //   setReviewId(0);
  //   openModal();
  // };

  const openCreateReview = () => {
    // const hasToken = !isEmptyString(getToken());
    setFormType('create');
    openModal();
  };

  const openShowAll = () => {
    // const hasToken = !isEmptyString(getToken());
    setFormType('all');
    openModal();
  };

  return (
    <StyledRestaurantReviewWrapper>
      <StyledReviewCountText>리뷰 {reviewCount ? `${reviewCount}개` : '없음'}</StyledReviewCountText>
      {isLoading && <h5>로딩중입니다.</h5>}
      <RestaurantReviewList reviews={previewReviews} />
      <StyledButtonContainer>
        <button type="button" onClick={openCreateReview}>
          리뷰 작성하기
        </button>
        {isMoreReviews && <button type="button" onClick={openShowAll}>{`리뷰 ${reviewCount}개 모두 보기`}</button>}
      </StyledButtonContainer>
      <Modal isOpen={isModalOpen} open={openModal} close={closeModal}>
        <ModalContent>
          <>
            {formType === '' && <LoginModalContent />}
            {formType === 'create' && <ReviewForm type="create" />}
            {formType === 'all' && (
              <RestaurantReviewList reviews={restaurantReviewsData.reviews} isModal={isModalOpen} />
            )}
          </>
        </ModalContent>
      </Modal>
      <PopUpContainer />
    </StyledRestaurantReviewWrapper>
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

  & > button {
    padding: 1.3rem 2.3rem;

    border: 1px solid #222;
    border-radius: 8px;
    background: transparent;

    font-size: ${FONT_SIZE.md};
    outline: none;

    &:hover {
      background: var(--gray-1);
    }
  }
`;
