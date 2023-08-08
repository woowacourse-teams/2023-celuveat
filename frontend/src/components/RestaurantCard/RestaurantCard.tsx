import { styled } from 'styled-components';
import ImageCarousel from '../@common/ImageCarousel';
import Love from '~/assets/icons/love.svg';
import ProfileImageList from '../@common/ProfileImageList';
import { FONT_SIZE, truncateText } from '~/styles/common';

import type { Celeb } from '~/@types/celeb.types';
import type { Restaurant } from '~/@types/restaurant.types';
import useToggleRestaurantLike from '~/hooks/server/useToggleRestaurantLike';
import PopUpContainer from '~/components/PopUpContainer';

interface RestaurantCardProps {
  restaurant: Restaurant;
  celebs?: Celeb[];
  size?: string;
  type?: 'list' | 'map';
  onClick?: React.MouseEventHandler;
  setHoveredId?: React.Dispatch<React.SetStateAction<number>>;
}

function RestaurantCard({
  restaurant,
  celebs,
  size,
  type = 'list',
  onClick = () => {},
  setHoveredId = () => {},
}: RestaurantCardProps) {
  const { images, name, roadAddress, category, phoneNumber, isLiked } = restaurant;
  const { toggleRestaurantLike } = useToggleRestaurantLike(restaurant);

  const onMouseEnter = () => {
    setHoveredId(restaurant.id);
  };

  const onMouseLeave = () => {
    setHoveredId(null);
  };

  return (
    <StyledContainer onClick={onClick} onMouseEnter={onMouseEnter} onMouseLeave={onMouseLeave}>
      <StyledImageViewer>
        <ImageCarousel images={images} type={type} />
        <LikeButton aria-label="좋아요" type="button" onClick={toggleRestaurantLike}>
          <Love fill={isLiked ? 'red' : '#000'} fillOpacity={0.5} aria-hidden="true" />
        </LikeButton>
      </StyledImageViewer>
      <section>
        <StyledInfo>
          <StyledCategory>{category}</StyledCategory>
          <StyledName role="columnheader">{name}</StyledName>
          <StyledAddress>{roadAddress}</StyledAddress>
          <StyledAddress>{phoneNumber}</StyledAddress>
        </StyledInfo>
        <StyledProfileImageSection>
          {celebs && <ProfileImageList celebs={celebs} size={size} />}
        </StyledProfileImageSection>
        <PopUpContainer imgUrl={restaurant.images[0].name} />
      </section>
    </StyledContainer>
  );
}

export default RestaurantCard;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: start;
  gap: 0.8rem;

  width: 100%;
  height: 100%;

  & > section {
    display: flex;
    justify-content: space-between;
  }

  cursor: pointer;
`;

const StyledImageViewer = styled.div`
  position: relative;
`;

const StyledInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;

  position: relative;

  width: 100%;

  padding: 0.4rem;
`;

const StyledName = styled.h5`
  ${truncateText(1)}
`;

const StyledAddress = styled.span`
  ${truncateText(1)}
  color: var(--gray-4);
  font-size: ${FONT_SIZE.md};
`;

const StyledCategory = styled.span`
  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;

const StyledProfileImageSection = styled.div`
  align-self: flex-end;
`;

const LikeButton = styled.button`
  position: absolute;
  top: 12px;
  right: 12px;

  border: none;
  background-color: transparent;

  &:hover {
    transform: scale(1.2);
  }

  /* cursor: pointer; */
`;
