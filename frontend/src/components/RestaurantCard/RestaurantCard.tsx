import { styled } from 'styled-components';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import ProfileImage from '../@common/ProfileImage';
import { ProfileImageProps } from '../@common/ProfileImage/ProfileImage';

interface RestaurantCardProps {
  imageUrl: string;
  name: string;
  address: string;
  category: string;
  celeb: ProfileImageProps;
}

function RestaurantCard({ imageUrl, name, address, category, celeb }: RestaurantCardProps) {
  return (
    <StyledContainer>
      <StyledImage alt={`${name} 대표 이미지`} src={imageUrl} />
      <StyledInfo>
        <StyledRestaurantNameSection>
          <StyledRestaurantName>{name}</StyledRestaurantName>
          <StyledCategory>{category}</StyledCategory>
        </StyledRestaurantNameSection>
        <StyledAddress>{address}</StyledAddress>
        <StyledAddress>02-1234-5678</StyledAddress>
        <StyledProfileImageSection>
          <ProfileImage name={celeb.name} imageUrl={celeb.imageUrl} size={celeb.size} />
        </StyledProfileImageSection>
      </StyledInfo>
    </StyledContainer>
  );
}

export default RestaurantCard;

const StyledContainer = styled.div`
  display: flex;
  justify-content: start;
  flex-direction: column;
  gap: 8px;

  width: 320px;
  height: 360px;
`;

const StyledImage = styled.img`
  width: 100%;
<<<<<<< HEAD
  height: 320px;

  object-fit: cover;

  border-radius: ${BORDER_RADIUS.md};
=======
  height: 192px;
  border-radius: ${BORDER_RADIUS.sm};
>>>>>>> 30c425ad4e51b1a4f8ffafcca60ad1ad853d9bf1
`;

const StyledInfo = styled.div`
  display: flex;
<<<<<<< HEAD
  flex-direction: column;
  gap: 4px;

  position: relative;

  width: 100%;
=======
>>>>>>> 30c425ad4e51b1a4f8ffafcca60ad1ad853d9bf1
  flex: 1;

  width: 100%;

  padding: 0.4rem;
  flex-direction: column;
  gap: 0.8rem 0;
`;

const StyledRestaurantNameSection = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  width: 100%;
`;

const StyledRestaurantName = styled.span`
  font-size: ${FONT_SIZE.md};
  font-weight: bold;
`;

<<<<<<< HEAD
=======
const StyledRightSide = styled.div`
  display: flex;
  gap: 0 0.4rem;
`;

const StyledRating = styled.span`
  font-size: ${FONT_SIZE.md};
`;

const StyledReviewCount = styled.span`
  color: var(--gray-3);
  font-size: ${FONT_SIZE.md};
`;

>>>>>>> 30c425ad4e51b1a4f8ffafcca60ad1ad853d9bf1
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

  bottom: 4px;
  right: 4px;
`;
