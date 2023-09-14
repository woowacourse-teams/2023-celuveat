import { forwardRef } from 'react';
import styled, { css } from 'styled-components';
import { useQueryClient } from '@tanstack/react-query';
import ProfileImage from '~/components/@common/ProfileImage';

import useIsTextOverflow from '~/hooks/useIsTextOverflow';
import { useReviewModalContext } from '~/hooks/context/ReviewModalProvider';

import { FONT_SIZE, truncateText } from '~/styles/common';

import type { ProfileData, RestaurantReview } from '~/@types/api.types';

interface RestaurantReviewItemProps {
  review: RestaurantReview;
  isInModal: boolean;
}

const RestaurantReviewItem = forwardRef<HTMLDivElement, RestaurantReviewItemProps>(({ review, isInModal }, ref) => {
  const qc = useQueryClient();
  const profileData: ProfileData = qc.getQueryData(['profile']);
  const { ref: contentRef, isTextOverflow } = useIsTextOverflow();

  const { clickUpdateReview, clickDeleteReview, openShowAll, setReviewId } = useReviewModalContext();

  const isUsersReview = profileData?.memberId === review.memberId;

  return (
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
            <button
              type="button"
              onClick={() => {
                setReviewId(review.id);
                clickUpdateReview();
              }}
            >
              수정
            </button>
            <StyledLine />
            <button
              type="button"
              onClick={() => {
                setReviewId(review.id);
                clickDeleteReview();
              }}
            >
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
