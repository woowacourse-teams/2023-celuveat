import { styled } from 'styled-components';
import { BORDER_RADIUS, FONT_SIZE, paintSkeleton, truncateText } from '~/styles/common';
import ProfileImage from '../@common/ProfileImage';
import { BASE_URL } from '~/App';

import type { Celeb } from '~/@types/celeb.types';
import type { Restaurant } from '~/@types/restaurant.types';

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
  const { images, name, roadAddress, category } = restaurant;

  const onMouseEnter = () => {
    setHoveredId(restaurant.id);
  };

  const onMouseLeave = () => {
    setHoveredId(null);
  };

  return (
    <StyledContainer onClick={onClick} onMouseEnter={onMouseEnter} onMouseLeave={onMouseLeave}>
      <StyledImage alt={`${name} 대표 이미지`} src={`${BASE_URL}/images-data/${images[0].name}`} type={type} />
      <section>
        <StyledInfo>
          <StyledCategory>{category}</StyledCategory>
          <StyledName>{name}</StyledName>
          <StyledAddress>{roadAddress}</StyledAddress>
          <StyledAddress>02-1234-5678</StyledAddress>
        </StyledInfo>
        <StyledProfileImageSection>
          {celebs && <ProfileImage name={celebs[0].name} imageUrl={celebs[0].profileImageUrl} size={size} />}
        </StyledProfileImageSection>
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

const StyledImage = styled.img<{ type: 'list' | 'map' }>`
  ${paintSkeleton}
  width: 100%;
  aspect-ratio: 1.05 / 1;

  border-radius: ${({ type }) =>
    type === 'list' ? `${BORDER_RADIUS.md}` : `${BORDER_RADIUS.md} ${BORDER_RADIUS.md} 0 0 `};

  object-fit: cover;
`;

const StyledInfo = styled.div`
  display: flex;
  flex: 1;
  flex-direction: column;
  gap: 0.4rem;

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
  font-size: ${FONT_SIZE.sm};
`;

const StyledCategory = styled.span`
  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;

const StyledProfileImageSection = styled.div`
  align-self: flex-end;
`;
