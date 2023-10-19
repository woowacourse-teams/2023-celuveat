import { css, styled } from 'styled-components';
import { MouseEventHandler, memo } from 'react';
import { useNavigate } from 'react-router-dom';
import Love from '~/assets/icons/love.svg';
import { FONT_SIZE, truncateText } from '~/styles/common';
import Star from '~/assets/icons/star.svg';

import type { Celeb } from '~/@types/celeb.types';
import type { Restaurant } from '~/@types/restaurant.types';
import LoginModal from '~/components/LoginModal';
import useToggleLikeNotUpdate from '~/hooks/server/useToggleLikeNotUpdate';
import WaterMarkImage from '../@common/WaterMarkImage';
import ProfileImageList from '../@common/ProfileImageList';

interface MiniRestaurantCardProps {
  restaurant: Restaurant;
  celebs?: Celeb[];
  setHoveredId?: React.Dispatch<React.SetStateAction<number>>;
  flexColumn?: boolean;
  showRating?: boolean;
  showLike?: boolean;
  showDistance?: boolean;
  hideProfile?: boolean;
  isCarouselItem?: boolean;
}

function MiniRestaurantCard({
  restaurant,
  celebs = [],
  flexColumn = false,
  setHoveredId = () => {},
  isCarouselItem = false,
  showRating = false,
  showLike = false,
  showDistance = false,
  hideProfile = false,
}: MiniRestaurantCardProps) {
  const { id, images, name, roadAddress, category, rating, distance } = restaurant;
  const { isModalOpen, closeModal, toggleRestaurantLike, isLiked } = useToggleLikeNotUpdate(restaurant);

  const navigate = useNavigate();

  const onMouseEnter = () => setHoveredId(restaurant.id);
  const onMouseLeave = () => setHoveredId(null);
  const onClick = () => navigate(`/restaurants/${id}?celebId=${celebs[0].id}`);

  const toggle: MouseEventHandler<HTMLButtonElement> = e => {
    e.stopPropagation();

    toggleRestaurantLike();
  };

  return (
    <>
      <StyledContainer
        onClick={onClick}
        onMouseEnter={onMouseEnter}
        onMouseLeave={onMouseLeave}
        data-cy="음식점 카드"
        aria-label={`${name} 카드`}
        tabIndex={0}
        flexColumn={flexColumn}
        isCarouselItem={isCarouselItem}
      >
        <StyledImageSection>
          <WaterMarkImage imageUrl={images[0]?.name} type="list" sns={images[0]?.sns} />
          {showLike && (
            <StyledLikeButton aria-label="좋아요" type="button" onClick={toggle}>
              <Love width={20} fill={isLiked ? 'red' : '#000'} fillOpacity={0.8} aria-hidden="true" />
            </StyledLikeButton>
          )}
        </StyledImageSection>
        <StyledInfoSection>
          <StyledInfoTopSection>
            <StyledName role="columnheader">{name}</StyledName>
            {showRating && (
              <StyledRating>
                <Star /> {rating.toFixed(2)}
              </StyledRating>
            )}
          </StyledInfoTopSection>
          <StyledCategory>{category}</StyledCategory>
          <StyledAddress>{roadAddress}</StyledAddress>
          {showDistance && <StyledAddress>{distance}m 이내</StyledAddress>}
          {!hideProfile && (
            <StyledProfileSection>{celebs && <ProfileImageList celebs={celebs} size="24px" />}</StyledProfileSection>
          )}
        </StyledInfoSection>
      </StyledContainer>

      <LoginModal isOpen={isModalOpen} close={closeModal} />
    </>
  );
}

function areEqual(prevProps: MiniRestaurantCardProps, nextProps: MiniRestaurantCardProps) {
  const { restaurant: prevRestaurant, celebs: prevCelebs } = prevProps;
  const { restaurant: nextRestaurant, celebs: nextCelebs } = nextProps;

  return prevRestaurant.id === nextRestaurant.id && prevCelebs[0].id === nextCelebs[0].id;
}

export default memo(MiniRestaurantCard, areEqual);

const StyledContainer = styled.li<{ flexColumn: boolean; isCarouselItem: boolean }>`
  display: flex;

  ${({ isCarouselItem }) =>
    isCarouselItem &&
    css`
      margin: 0 auto;
    `}

  ${({ flexColumn }) =>
    flexColumn &&
    css`
      flex-direction: column;
      justify-content: start;
      align-items: center;

      width: 200px;
    `}

  cursor: pointer;
`;

const StyledImageSection = styled.section`
  position: relative;

  width: 198px;
  min-width: 150px;
`;

const StyledInfoSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;

  position: relative;

  width: 100%;

  padding: 0.8rem;
`;

const StyledName = styled.h5`
  ${truncateText(1)}
  font-size: ${FONT_SIZE.md};
  line-height: 20px;
`;

const StyledAddress = styled.span`
  width: calc(100% - 28px);

  ${truncateText(1)}
  color: var(--gray-4);
  font-size: ${FONT_SIZE.sm};
  line-height: 20px;
`;

const StyledCategory = styled.span`
  display: flex;
  justify-content: space-between;

  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;

const StyledRating = styled.span`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 0.4rem;

  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;

const StyledLikeButton = styled.button`
  position: absolute;
  top: 4%;
  right: 1%;
  z-index: 8;

  border: none;
  background-color: transparent;

  &:hover {
    transform: scale(1.2);
  }
`;

const StyledInfoTopSection = styled.section`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const StyledProfileSection = styled.div`
  position: absolute;
  right: 4px;
  bottom: 4px;
`;
