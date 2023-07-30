import { styled } from 'styled-components';
import { FONT_SIZE, truncateText } from '~/styles/common';
import ProfileImage from '../@common/ProfileImage';
import ImageCarousel from '../@common/ImageCarousel';
import Love from '~/assets/icons/love.svg';
import type { Restaurant } from '~/@types/restaurant.types';
import type { Celeb } from '~/@types/celeb.types';

interface RestaurantCardProps {
  restaurant: Restaurant;
  celebs?: Celeb[];
  size?: number;
  type?: 'list' | 'map';
  onClick?: React.MouseEventHandler;
}

function RestaurantCard({ restaurant, celebs, size, type = 'list', onClick }: RestaurantCardProps) {
  const { images, name, roadAddress, category } = restaurant;

  return (
    <StyledContainer onClick={onClick}>
      <StyledImageViewer>
        <ImageCarousel images={images} type={type} />
        <Love fill="#000" fillOpacity={0.5} />
      </StyledImageViewer>
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

const StyledImageViewer = styled.div`
  position: relative;

  & > svg {
    position: absolute;
    top: 12px;
    right: 12px;
  }
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
