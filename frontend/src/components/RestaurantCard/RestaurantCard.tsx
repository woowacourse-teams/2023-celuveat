import { styled } from 'styled-components';
import Label from '~/components/@common/Label';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import Star from '~/assets/icons/star.svg';

interface RestaurantCardProps {
  imageUrl: string;
  name: string;
  address: string;
  category: string;
  rating: string;
  reviewCount: number;
  isAds: boolean;
}

function RestaurantCard({ imageUrl, name, address, category, rating, reviewCount, isAds }: RestaurantCardProps) {
  return (
    <StyledDiv>
      <StyledImage src={imageUrl} />
      <StyledInfo>
        {isAds && <Label text="유료광고" />}
        <StyledRestaurantNameSection>
          <StyledRestaurantName>{name}</StyledRestaurantName>
          <StyledRestaurantName>
            <Star />
            <StyledRating>{rating}</StyledRating>
            <StyledReviewCount>({reviewCount})</StyledReviewCount>
          </StyledRestaurantName>
        </StyledRestaurantNameSection>
        <StyledAddress>{address}</StyledAddress>
        <StyledCategory>{category}</StyledCategory>
      </StyledInfo>
    </StyledDiv>
  );
}

export default RestaurantCard;

const StyledDiv = styled.div`
  display: flex;
  justify-content: start;
  flex-direction: column;

  width: 301px;
  height: 287px;
`;

const StyledImage = styled.img`
  width: 100%;
  height: 192px;

  border-radius: ${BORDER_RADIUS.sm};
`;

const StyledInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;

  width: 100%;
  flex: 1;

  padding: 4px;

  box-sizing: border-box;
`;

const StyledRestaurantNameSection = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  width: 100%;
`;

const StyledRestaurantName = styled.span`
  font-size: ${FONT_SIZE.lg};
  font-weight: bold;
`;

const StyledRating = styled.span`
  margin: 0 4px;

  font-size: ${FONT_SIZE.md};
  font-weight: normal;
`;

const StyledReviewCount = styled.span`
  color: var(--gray-3);
  font-size: ${FONT_SIZE.md};
  font-weight: normal;
`;

const StyledAddress = styled.span`
  color: var(--gray-4);
  font-size: ${FONT_SIZE.sm};
`;

const StyledCategory = styled.span`
  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;
