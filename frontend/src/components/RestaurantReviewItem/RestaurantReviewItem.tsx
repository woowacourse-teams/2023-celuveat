import { forwardRef } from 'react';
import styled, { css } from 'styled-components';

import ProfileImage from '~/components/@common/ProfileImage';
import useIsTextOverflow from '~/hooks/useIsTextOverflow';

import { FONT_SIZE, truncateText } from '~/styles/common';

import { RestaurantReview } from '~/@types/api.types';

interface RestaurantReviewItemProps {
  review: RestaurantReview;
  isModal: boolean;
  reviewModalOpen: React.MouseEventHandler<HTMLDivElement>;
}

const RestaurantReviewItem = forwardRef<HTMLDivElement, RestaurantReviewItemProps>(
  ({ review, isModal, reviewModalOpen }, ref) => {
    const { ref: contentRef, isTextOverflow } = useIsTextOverflow();

    return (
      <StyledRestaurantReviewItemWrapper ref={ref}>
        <StyledProfileWrapper>
          <ProfileImage name={review.nickname} size="40px" imageUrl={review.profileImageUrl} />
          <StyledProfileInfoWrapper>
            <StyledProfileNickName>{review.nickname}</StyledProfileNickName>
            <StyledCreateDated>{review.createdDate}</StyledCreateDated>
          </StyledProfileInfoWrapper>
        </StyledProfileWrapper>
        <StyledReviewContent ref={contentRef} isModal={isModal}>
          {review.content}
        </StyledReviewContent>
        {isTextOverflow && (
          <StyledSeeMore isModal={isModal} data-id={review.id} onClick={reviewModalOpen}>
            더 보기
          </StyledSeeMore>
        )}
      </StyledRestaurantReviewItemWrapper>
    );
  },
);

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

const StyledProfileWrapper = styled.div`
  display: flex;
  align-items: center;
`;

const StyledProfileInfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 1rem;
`;

const StyledReviewContent = styled.div<{ isModal: boolean }>`
  margin: 1.2rem 0;

  color: #222;
  font-size: ${FONT_SIZE.md};
  ${({ isModal }) => !isModal && truncateText(3)}
  line-height: 2.4rem;
`;

const StyledRestaurantReviewItemWrapper = styled.div`
  width: 100%;
`;

const StyledSeeMore = styled.span<{ isModal: boolean }>`
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

  ${({ isModal }) =>
    isModal &&
    css`
      display: none;
    `}
`;
