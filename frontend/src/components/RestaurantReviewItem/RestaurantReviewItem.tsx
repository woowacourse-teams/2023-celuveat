import { styled } from 'styled-components';
import { useQueryClient } from '@tanstack/react-query';

import GroupIcon from '~/assets/icons/etc/groups.svg';
import ThumpUpIcon from '~/assets/icons/etc/thumb-up.svg';
import SpeakerphoneIcon from '~/assets/icons/etc/speakerphone.svg';

import ProfileImage from '~/components/@common/ProfileImage';

import { useReviewModalContext } from '~/hooks/context/ReviewModalProvider';

import { FONT_SIZE } from '~/styles/common';

import type { ProfileData, RestaurantReview } from '~/@types/api.types';
import useRestaurantReview from '~/hooks/server/useRestaurantReview';

interface RestaurantReviewItemProps {
  review: RestaurantReview;
  isInModal: boolean;
}

function RestaurantReviewItem({ review, isInModal }: RestaurantReviewItemProps) {
  const qc = useQueryClient();
  const profileData: ProfileData = qc.getQueryData(['profile']);

  const { getReviewIsLiked, postReviewLike, postReviewReport } = useRestaurantReview();

  const { clickUpdateReview, clickDeleteReview, setReviewId } = useReviewModalContext();

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
      <StyledReviewLikeCountWrapper>
        <div>
          <GroupIcon />
          <StyledReviewText>{review.likeCount}</StyledReviewText>
          명이 추천했어요
        </div>
      </StyledReviewLikeCountWrapper>
      <StyledReviewContent isInModal={isInModal}>{review.content}</StyledReviewContent>
      <StyledReviewImgWrapper>
        {review?.reviewImageUrls?.map((reviewImageUrl, idx) => (
          <StyledReviewImg src={reviewImageUrl} alt={`${review.nickname}이 쓴 리뷰 사진${idx}`} />
        ))}
      </StyledReviewImgWrapper>
      <StyledReviewButtonsWrapper>
        {!isUsersReview && (
          <>
            <StyledReviewButton
              onClick={() => {
                postReviewLike(review.id);
              }}
            >
              <ThumpUpIcon fill={getReviewIsLiked(review.id) ? '#ff7b54' : 'white'} />
              <span>추천 {getReviewIsLiked(review.id) ? '취소' : '하기'}</span>
            </StyledReviewButton>
            <StyledReviewButton onClick={() => postReviewReport(review.id)}>
              <SpeakerphoneIcon />
              <span>신고하기</span>
            </StyledReviewButton>
          </>
        )}
      </StyledReviewButtonsWrapper>
    </StyledRestaurantReviewItemWrapper>
  );
}

export default RestaurantReviewItem;

const StyledReviewImg = styled.img`
  width: 150px;
  height: 150px;
  object-fit: cover;
`;

const StyledReviewImgWrapper = styled.div`
  margin: 1.2rem 0;
`;

const StyledReviewText = styled.span`
  margin-left: 0.5rem;

  font-size: ${FONT_SIZE.lg};
  font-weight: 500;
`;

const StyledReviewButton = styled.button`
  display: flex;
  align-items: center;

  padding: 0;
  margin: 0;

  border: none;
  background: none;

  font-size: ${FONT_SIZE.md};
  font-weight: 500;

  & > span {
    margin-left: 0.5rem;
  }

  &:hover {
    & > span {
      color: #ff7b54;
    }

    & > svg {
      fill: #ff7b54;
    }
  }
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

const StyledReviewLikeCountWrapper = styled.div`
  display: flex;
  justify-content: space-between;

  width: 100%;

  margin: 1.2rem 0;

  font-size: ${FONT_SIZE.md};
`;

const StyledReviewButtonsWrapper = styled.div`
  display: flex;
  gap: 2rem;

  width: 100%;

  font-size: ${FONT_SIZE.md};
`;
