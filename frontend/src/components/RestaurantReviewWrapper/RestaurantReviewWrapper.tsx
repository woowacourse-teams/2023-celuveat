import { useMemo } from 'react';
import { shallow } from 'zustand/shallow';
import styled from 'styled-components';

import RestaurantReviewList from '~/components/RestaurantReviewList';
import ReviewForm from '~/components/ReviewForm/ReviewForm';
import DeleteButton from '~/components/ReviewForm/DeleteButton';
import { Modal, ModalContent } from '~/components/@common/Modal';
import Alert from '~/assets/icons/alert.svg';
import useModalState from '~/hooks/store/useModalState';
import { FONT_SIZE } from '~/styles/common';

import useRestaurantReview from '~/hooks/server/useRestaurantReview';
import getToken from '~/utils/getToken';
import { isEmptyString } from '~/utils/compare';
import PopUpContainer from '../PopUpContainer';
import LoginModalContent from '~/components/LoginModalContent';

const REVIEW_SHOW_COUNT = 6;

const isMoreThan = (target: number, standard: number) => target > standard;

function RestaurantReviewWrapper() {
  const { restaurantReviewsData, isLoading } = useRestaurantReview();
  const [content, isModalOpen, close, open, setId, setContent] = useModalState(
    state => [state.content, state.isModalOpen, state.close, state.open, state.setId, state.setContent],
    shallow,
  );

  const reviewCount = restaurantReviewsData?.totalElementsCount;
  const previewReviews = useMemo(
    () => restaurantReviewsData?.reviews.slice(0, REVIEW_SHOW_COUNT),
    [restaurantReviewsData],
  );
  const isMoreReviews = isMoreThan(reviewCount, REVIEW_SHOW_COUNT);

  const openAllReviews = () => {
    setContent('리뷰 모두 보기');
    setId(0);
    open();
  };

  const openFormModal = () => {
    const hasToken = !isEmptyString(getToken());
    if (hasToken) {
      setContent('리뷰 작성 하기');
    } else {
      setContent('');
    }

    open();
  };

  return (
    <StyledRestaurantReviewWrapper>
      <StyledReviewCountText>리뷰 {reviewCount ? `${reviewCount}개` : '없음'}</StyledReviewCountText>
      {isLoading && <h5>로딩중입니다.</h5>}
      <RestaurantReviewList reviews={previewReviews} />
      <StyledButtonContainer>
        <button type="button" onClick={openFormModal}>
          리뷰 작성하기
        </button>
        {isMoreReviews && <button type="button" onClick={openAllReviews}>{`리뷰 ${reviewCount}개 모두 보기`}</button>}
      </StyledButtonContainer>
      <Modal>
        {content === '리뷰 모두 보기' && (
          <ModalContent isShow={isModalOpen} title={content} closeModal={close}>
            <RestaurantReviewList reviews={restaurantReviewsData.reviews} isModal />
          </ModalContent>
        )}
        {content === '리뷰 작성 하기' && (
          <ModalContent isShow={isModalOpen} title={content} closeModal={close}>
            <ReviewForm type="create" />
          </ModalContent>
        )}
        {content === '리뷰 수정 하기' && (
          <ModalContent isShow={isModalOpen} title={content} closeModal={close}>
            <ReviewForm type="update" />
          </ModalContent>
        )}
        {content === '리뷰 삭제 하기' && (
          <ModalContent isShow={isModalOpen} title={content} closeModal={close}>
            <div>
              <StyledWarningMessage>
                <Alert width={32} />
                <p>정말 삭제하시겠습니까? 한 번 삭제된 리뷰는 복구가 불가능합니다.</p>
              </StyledWarningMessage>
              <DeleteButton />
            </div>
          </ModalContent>
        )}
        {content === '' && (
          <ModalContent isShow={isModalOpen} title="로그인" closeModal={close}>
            <LoginModalContent />
          </ModalContent>
        )}
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

const StyledWarningMessage = styled.p`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2.4rem 0;

  margin: 3.2rem 0;

  font-size: ${FONT_SIZE.md};
  text-align: center;
`;
