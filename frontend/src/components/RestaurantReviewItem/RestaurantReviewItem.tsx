/* eslint-disable react/no-unknown-property */
import { styled, css } from 'styled-components';
import { useQuery } from '@tanstack/react-query';

import ThumpUpIcon from '~/assets/icons/etc/thumb-up.svg';
import SpeakerphoneIcon from '~/assets/icons/etc/speakerphone.svg';

import StarRating from '~/components/@common/StarRating';
import ProfileImage from '~/components/@common/ProfileImage';

import { useReviewModalContext } from '~/hooks/context/ReviewModalProvider';
import useRestaurantReview from '~/hooks/server/useRestaurantReview';

import { FONT_SIZE } from '~/styles/common';
import { getProfile } from '~/api/user';
import { getReviewImgUrl } from '~/utils/image';

import type { ProfileData, RestaurantReview } from '~/@types/api.types';

interface RestaurantReviewItemProps {
  review: RestaurantReview;
  isInModal: boolean;
}

function RestaurantReviewItem({ review, isInModal }: RestaurantReviewItemProps) {
  const { data: profileData } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: getProfile,
  });

  const { getReviewIsLiked, toggleRestaurantReviewLike } = useRestaurantReview();

  const { openReviewModal, setReviewId } = useReviewModalContext();

  const isUsersReview = profileData?.memberId === review.memberId;

  return (
    <StyledRestaurantReviewItemWrapper>
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
                openReviewModal('update');
              }}
            >
              수정
            </button>
            <StyledLine />
            <button
              type="button"
              onClick={() => {
                setReviewId(review.id);
                openReviewModal('delete');
              }}
            >
              삭제
            </button>
          </StyledButtonContainer>
        )}
      </StyledProfileAndButton>
      <StyledStarRatingWrapper>
        <StarRating rate={review.rating} size="8px" />
      </StyledStarRatingWrapper>
      <StyledReviewContent isInModal={isInModal}>{review.content}</StyledReviewContent>
      <StyledReviewImgWrapper>
        {review?.reviewImageUrls?.map((reviewImageUrl, idx) => (
          <StyledReviewImg
            src={getReviewImgUrl(reviewImageUrl.imgUrl, 'webp')}
            alt={`${review.nickname}이 쓴 리뷰 사진${idx}`}
          />
        ))}
      </StyledReviewImgWrapper>

      <StyledReviewButtonsWrapper>
        {!isUsersReview && (
          <>
            <StyledReviewButton
              isLiked={getReviewIsLiked(review.id)}
              onClick={() => {
                toggleRestaurantReviewLike(review.id);
              }}
            >
              <ThumpUpIcon stroke={getReviewIsLiked(review.id) ? '#ff7b54' : '#3a3b3c'} />
              <StyledReviewText isLiked={getReviewIsLiked(review.id)}>{review.likeCount}</StyledReviewText>
            </StyledReviewButton>
            <StyledReviewButton
              onClick={() => {
                setReviewId(review.id);
                openReviewModal('report');
              }}
            >
              <SpeakerphoneIcon stroke="#3a3b3c" />
              <StyledReviewText>신고</StyledReviewText>
            </StyledReviewButton>
          </>
        )}
      </StyledReviewButtonsWrapper>
    </StyledRestaurantReviewItemWrapper>
  );
}

export default RestaurantReviewItem;

const StyledReviewText = styled.span<{ isLiked?: boolean }>`
  color: #3a3b3c;

  ${({ isLiked }) =>
    isLiked &&
    css`
      font-weight: 600;
    `}
`;

const StyledStarRatingWrapper = styled.div`
  margin-top: 1.2rem;

  display: flex;
`;

const StyledReviewImg = styled.img`
  width: 120px;
  height: 120px;
  object-fit: cover;
`;

const StyledReviewImgWrapper = styled.div`
  display: flex;
  flex-wrap: nowrap;
  overflow: auto;

  margin: 1.2rem 0;

  &::-webkit-scrollbar {
    display: none;
  }

  ${StyledReviewImg} + ${StyledReviewImg} {
    margin-left: 1rem;
  }
`;

const StyledReviewButton = styled.button<{ isLiked?: boolean }>`
  display: flex;
  align-items: center;

  padding: 0.6rem;
  margin: 0;

  border: ${({ isLiked }) => `1px solid ${isLiked ? '#ff7b54' : '#e4e4e5'}`};
  border-radius: 5px;
  background: none;

  font-size: ${FONT_SIZE.sm};

  & > span {
    margin-left: 0.5rem;
  }

  ${({ isLiked }) =>
    isLiked &&
    css`
      background-color: #ffe6d7;
    `}

  ${({ isLiked }) =>
    !isLiked &&
    css`
      @media (hover: hover) {
        &:hover {
          background-color: var(--gray-2);
        }
      }
    `}
`;

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
  margin-top: 1.2rem;

  color: #222;
  font-size: ${FONT_SIZE.md};
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

const StyledReviewButtonsWrapper = styled.div`
  display: flex;
  gap: 2rem;

  width: 100%;

  font-size: ${FONT_SIZE.md};
`;
