import { styled } from 'styled-components';
import { BORDER_RADIUS, FONT_SIZE, truncateText } from '~/styles/common';
import ProfileImage from '../@common/ProfileImage';
import { Restaurant } from '~/@types/restaurant.types';
import { Celebs } from '~/@types/celeb.types';

interface RestaurantCardProps {
  restaurant: Restaurant;
  celebs: Celebs;
  size: number;
  onClick: React.MouseEventHandler;
}

function RestaurantCard({ restaurant, celebs, size, onClick }: RestaurantCardProps) {
  const { images, name, roadAddress, category } = restaurant;

  return (
    <StyledContainer onClick={onClick}>
      <StyledImage alt={`${name} 대표 이미지`} src={`images-data/${images[0].name}`} />
      <section>
        <StyledInfo>
          <StyledCategory>{category}</StyledCategory>
          <StyledName>{name}</StyledName>
          <StyledAddress>{roadAddress}</StyledAddress>
          <StyledAddress>02-1234-5678</StyledAddress>
        </StyledInfo>
        <StyledProfileImageSection>
          <ProfileImage name={celebs[0].name} imageUrl={celebs[0].profileImageUrl} size={size} />
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
`;

const StyledImage = styled.img`
  width: 100%;
  aspect-ratio: 1.05 / 1;

  border-radius: ${BORDER_RADIUS.md};

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