import { css, styled } from 'styled-components';
import { MouseEventHandler, memo } from 'react';
import { useNavigate } from 'react-router-dom';
import ImageCarousel from '../@common/ImageCarousel';
import Love from '~/assets/icons/love.svg';
import { FONT_SIZE, truncateText } from '~/styles/common';
import Star from '~/assets/icons/star.svg';

import type { Celeb } from '~/@types/celeb.types';
import type { Restaurant } from '~/@types/restaurant.types';
import LoginModal from '~/components/LoginModal';
import useToggleLikeNotUpdate from '~/hooks/server/useToggleLikeNotUpdate';

interface MiniRestaurantCardProps {
  restaurant: Restaurant;
  celebs?: Celeb[];
  setHoveredId?: React.Dispatch<React.SetStateAction<number>>;
  flexColumn?: boolean;
  carousel?: boolean;
  showWaterMark?: boolean;
  showRating?: boolean;
  showLike?: boolean;
}

function MiniRestaurantCard({
  restaurant,
  celebs = [],
  flexColumn = false,
  setHoveredId = () => {},
  carousel = false,
  showWaterMark = false,
  showRating = false,
  showLike = false,
}: MiniRestaurantCardProps) {
  const { id, images, name, roadAddress, category, rating } = restaurant;
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
      >
        <StyledImageSection>
          <ImageCarousel images={images} type="list" showWaterMark={showWaterMark} disabled={!carousel} />
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

const StyledContainer = styled.li<{ flexColumn: boolean }>`
  display: flex;

  ${({ flexColumn }) =>
    flexColumn &&
    css`
      flex-direction: column;
      justify-content: start;
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
