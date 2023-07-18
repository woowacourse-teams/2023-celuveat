import { styled } from 'styled-components';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import ProfileImage from '../@common/ProfileImage';
import { Restaurant } from '~/@types/restaurant.types';
import { Celebs } from '~/@types/celeb.types';

interface RestaurantCardProps {
  restaurant: Restaurant;
  celebs: Celebs;
  size: number;
}

function RestaurantCard({ restaurant, celebs, size }: RestaurantCardProps) {
  const { images, name, roadAddress, category } = restaurant;

  return (
    <StyledContainer>
      <StyledImage alt={`${name} 대표 이미지`} src={images[0].name} />
      <StyledInfo>
        <StyledCategory>{category}</StyledCategory>
        <h5>{name}</h5>
        <StyledAddress>{roadAddress}</StyledAddress>
        <StyledAddress>02-1234-5678</StyledAddress>
        <StyledProfileImageSection>
          <ProfileImage name={celebs[0].name} imageUrl={celebs[0].profileImageUrl} size={size} />
        </StyledProfileImageSection>
      </StyledInfo>
    </StyledContainer>
  );
}

export default RestaurantCard;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: start;
  gap: 0.8rem;

  width: 320px;
  height: 360px;
`;

const StyledImage = styled.img`
  width: 100%;
  height: 320px;
  object-fit: cover;

  border-radius: ${BORDER_RADIUS.md};
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

const StyledAddress = styled.span`
  color: var(--gray-4);
  font-size: ${FONT_SIZE.sm};
`;

const StyledCategory = styled.span`
  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;

const StyledProfileImageSection = styled.div`
  position: absolute;
  right: 4px;
  bottom: 4px;
`;
