import { useMemo } from 'react';
import styled from 'styled-components';
import Alert from '~/assets/icons/alert.svg';

import RestaurantReviewList from '~/components/RestaurantReviewList';
import { Modal, ModalContent } from '~/components/@common/Modal';
import LoginModalContent from '~/components/LoginModalContent';
import ReviewForm from '../ReviewForm/ReviewForm';

import useRestaurantReview from '~/hooks/server/useRestaurantReview';
import { useReviewModalContext } from '~/hooks/context/ReviewModalProvider';
import { FONT_SIZE } from '~/styles/common';
import { isMoreThan } from '~/utils/compare';
import { REVIEW_SHOW_COUNT } from '~/constants/options';
import DeleteButton from '../ReviewForm/DeleteButton';

function RestaurantReviewWrapper() {
  const { restaurantReviewsData } = useRestaurantReview();
  const { formType, isModalOpen, openModal, closeModal, openCreateReview, openShowAll, reviewId } =
    useReviewModalContext();
  const { totalElementsCount: reviewCount, reviews } = restaurantReviewsData;

  const previewReviews = useMemo(() => reviews.slice(0, REVIEW_SHOW_COUNT), [reviews]);
  const isMoreReviews = isMoreThan(reviewCount, REVIEW_SHOW_COUNT);

  return (
    <>
      <StyledRestaurantReviewWrapper>
        <StyledReviewCountText>리뷰 {reviewCount ? `${reviewCount}개` : '없음'}</StyledReviewCountText>
        <RestaurantReviewList reviews={previewReviews} />
        <StyledButtonContainer>
          <button type="button" onClick={openCreateReview}>
            리뷰 작성하기
          </button>
          {isMoreReviews && <button type="button" onClick={openShowAll}>{`리뷰 ${reviewCount}개 모두 보기`}</button>}
        </StyledButtonContainer>
      </StyledRestaurantReviewWrapper>

      <Modal isOpen={isModalOpen} open={openModal} close={closeModal}>
        <ModalContent>
          <>
            {formType === '' && <LoginModalContent />}
            {formType === 'create' && <ReviewForm type="create" />}
            {formType === 'all' && (
              <RestaurantReviewList reviews={restaurantReviewsData.reviews} isModal={isModalOpen} />
            )}
            {formType === 'update' && (
              <ModalContent title="리뷰 수정하기">
                <ReviewForm type="update" reviewId={reviewId} />
              </ModalContent>
            )}
            {formType === 'delete' && (
              <ModalContent title="리뷰 삭제하기">
                <div>
                  <StyledWarningMessage>
                    <Alert width={32} />
                    <p>정말 삭제하시겠습니까? 한 번 삭제된 리뷰는 복구가 불가능합니다.</p>
                  </StyledWarningMessage>
                  <DeleteButton reviewId={reviewId} />
                </div>
              </ModalContent>
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

const StyledWarningMessage = styled.p`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2.4rem 0;

  margin: 3.2rem 0;

  font-size: ${FONT_SIZE.md};
  text-align: center;
`;
