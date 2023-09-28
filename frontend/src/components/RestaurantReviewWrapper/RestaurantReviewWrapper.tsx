import { styled } from 'styled-components';

import Alert from '~/assets/icons/alert.svg';
import Pencil from '~/assets/icons/pencil.svg';

import Modal from '~/components/@common/Modal';

import useRestaurantReview from '~/hooks/server/useRestaurantReview';
import { useReviewModalContext } from '~/hooks/context/ReviewModalProvider';
import { FONT_SIZE } from '~/styles/common';

import { REVIEW_SHOW_COUNT } from '~/constants/options';

import RestaurantReviewList from '~/components/RestaurantReviewList';
import LoginModal from '~/components/LoginModal';
import { ReviewForm, DeleteButton } from '~/components/ReviewForm';

const REVIEW_FORM_TITLE = {
  create: '리뷰 작성하기',
  update: '리뷰 수정하기',
  delete: '리뷰 삭제하기',
  report: '리뷰 신고하기',
  all: '리뷰 모두 보기',
} as const;

function RestaurantReviewWrapper() {
  const { restaurantReviewsData } = useRestaurantReview();
  const { formType, isModalOpen, reviewId, closeModal, openReviewModal } = useReviewModalContext();

  const { totalElementsCount: reviewCount, reviews } = restaurantReviewsData;
  const previewReviews = reviews.slice(0, REVIEW_SHOW_COUNT);
  const isMoreReviews = reviewCount > REVIEW_SHOW_COUNT;

  return (
    <>
      <StyledRestaurantReviewWrapper>
        <StyledReviewCountText>리뷰 {reviewCount ? `${reviewCount}개` : '없음'}</StyledReviewCountText>
        <RestaurantReviewList reviews={previewReviews} />
        <StyledButtonContainer>
          <StyledButton type="button" onClick={() => openReviewModal('create')}>
            <Pencil width={20} />
            리뷰 작성하기
          </StyledButton>
          {isMoreReviews && (
            <StyledButton
              type="button"
              onClick={() => openReviewModal('all')}
            >{`리뷰 ${reviewCount}개 모두 보기`}</StyledButton>
          )}
        </StyledButtonContainer>
      </StyledRestaurantReviewWrapper>

      {formType ? (
        <Modal title={REVIEW_FORM_TITLE[formType]} isOpen={isModalOpen} close={closeModal}>
          <>
            {formType === 'create' && <ReviewForm type="create" />}
            {formType === 'report' && <ReviewForm type="report" reviewId={reviewId} />}
            {formType === 'all' && (
              <RestaurantReviewList reviews={restaurantReviewsData.reviews} isModal={isModalOpen} />
            )}
            {formType === 'update' && <ReviewForm type="update" reviewId={reviewId} />}
            {formType === 'delete' && (
              <div>
                <StyledWarningMessage>
                  <Alert width={32} />
                  <p>정말 삭제하시겠습니까? 한 번 삭제된 리뷰는 복구가 불가능합니다.</p>
                </StyledWarningMessage>
                <DeleteButton reviewId={reviewId} />
              </div>
            )}
          </>
        </Modal>
      ) : (
        <LoginModal isOpen={isModalOpen} close={closeModal} />
      )}
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

const StyledWarningMessage = styled.p`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2.4rem 0;

  margin: 3.2rem 0;

  font-size: ${FONT_SIZE.md};
  text-align: center;
`;
