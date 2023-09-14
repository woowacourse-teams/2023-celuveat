import { forwardRef } from 'react';
import styled, { css } from 'styled-components';
import { useQueryClient } from '@tanstack/react-query';

import { Modal, ModalContent } from '~/components/@common/Modal';
import ProfileImage from '~/components/@common/ProfileImage';
import ReviewForm from '~/components/ReviewForm/ReviewForm';
import DeleteButton from '~/components/ReviewForm/DeleteButton';

import useIsTextOverflow from '~/hooks/useIsTextOverflow';
import { useReviewModalContext } from '~/hooks/context/ReviewModalProvider';

import { FONT_SIZE, truncateText } from '~/styles/common';

import Alert from '~/assets/icons/alert.svg';

import type { ProfileData, RestaurantReview } from '~/@types/api.types';

interface RestaurantReviewItemProps {
  review: RestaurantReview;
  isInModal: boolean;
}

const RestaurantReviewItem = forwardRef<HTMLDivElement, RestaurantReviewItemProps>(({ review, isInModal }, ref) => {
  const qc = useQueryClient();
  const profileData: ProfileData = qc.getQueryData(['profile']);
  const { ref: contentRef, isTextOverflow } = useIsTextOverflow();

  const { formType, isModalOpen, openModal, closeModal, clickUpdateReview, clickDeleteReview, openShowAll } =
    useReviewModalContext();

  const isUsersReview = profileData?.memberId === review.memberId;

  return (
    <>
      <StyledRestaurantReviewItemWrapper ref={ref}>
        <StyledProfileAndButton>
          <StyledProfileWrapper>
            <ProfileImage name={review.nickname} size="40px" imageUrl={review.profileImageUrl} />
            <StyledProfileInfoWrapper>
              <StyledProfileNickName>{review.nickname}</StyledProfileNickName>
              <StyledCreateDated>{review.createdDate}</StyledCreateDated>
            </StyledProfileInfoWrapper>
          </StyledProfileWrapper>
          {isUsersReview && (
            <StyledButtonContainer>
              <button type="button" onClick={clickUpdateReview}>
                수정
              </button>
              <StyledLine />
              <button type="button" onClick={clickDeleteReview}>
                삭제
              </button>
            </StyledButtonContainer>
          )}
        </StyledProfileAndButton>
        <StyledReviewContent ref={contentRef} isInModal={isInModal}>
          {review.content}
        </StyledReviewContent>
        {isTextOverflow && (
          <StyledSeeMore isInModal={isInModal} data-id={review.id} onClick={openShowAll}>
            더 보기
          </StyledSeeMore>
        )}
      </StyledRestaurantReviewItemWrapper>

      <Modal open={openModal} close={closeModal} isOpen={isModalOpen}>
        {formType === 'update' && (
          <ModalContent title="리뷰 수정하기">
            <ReviewForm type="update" reviewId={review.id} />
          </ModalContent>
        )}
        {formType === 'delete' && (
          <ModalContent title="리뷰 삭제하기">
            <div>
              <StyledWarningMessage>
                <Alert width={32} />
                <p>정말 삭제하시겠습니까? 한 번 삭제된 리뷰는 복구가 불가능합니다.</p>
              </StyledWarningMessage>
              <DeleteButton reviewId={review.id} />
            </div>
          </ModalContent>
        )}
      </Modal>
    </>
  );
});

export default RestaurantReviewItem;

const StyledProfileNickName = styled.span`
  color: #222;
  font-size: ${FONT_SIZE.md};
  font-weight: bold;
  line-height: 2rem;
`;

const StyledCreateDated = styled.span`
  color: #717171;
  font-size: 1.4rem;
  line-height: 1.8rem;
`;

const StyledProfileAndButton = styled.div`
  display: flex;
  justify-content: space-between;
`;

const StyledProfileWrapper = styled.div`
  display: flex;
  align-items: center;
`;

const StyledProfileInfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 1rem;
`;

const StyledReviewContent = styled.div<{ isInModal: boolean }>`
  margin: 1.2rem 0;

  color: #222;
  font-size: ${FONT_SIZE.md};
  ${({ isInModal }) => !isInModal && truncateText(3)}
  line-height: 2.4rem;
`;

const StyledButtonContainer = styled.div`
  display: flex;
  align-items: flex-end;
  gap: 0 0.4rem;

  & > button {
    border: none;
    background: none;

    font-size: ${FONT_SIZE.sm};
  }
`;

const StyledLine = styled.div`
  width: 1px;
  height: 16.5px;

  background: var(--black);
`;

const StyledRestaurantReviewItemWrapper = styled.div`
  width: 100%;

  padding: 1.6rem;

  border: 1px solid #f2f2f2;
  border-radius: 20px;

  box-shadow: var(--shadow);
`;

const StyledSeeMore = styled.span<{ isInModal: boolean }>`
  position: relative;

  color: #222;
  font-size: ${FONT_SIZE.md};
  text-decoration: underline;

  cursor: pointer;

  &::after {
    display: inline-block;

    position: absolute;
    top: 30%;

    width: 6px;
    height: 6px;

    transform: rotate(45deg);

    content: '';

    border-top: 2.5px solid #222; /* 선 두께 */
    border-right: 2.5px solid #222; /* 선 두께 */
  }

  ${({ isInModal }) =>
    isInModal &&
    css`
      display: none;
    `}
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
