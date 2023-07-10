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
  isAds?: boolean;
}

RestaurantCard.defaultProps = {
  isAds: false,
};

function RestaurantCard({ imageUrl, name, address, category, rating, reviewCount, isAds }: RestaurantCardProps) {
  const filteredReviewCount = reviewCount > 999 ? '999+' : String(reviewCount);

  return (
    <StyledContainer>
      <StyledImage alt={`${name} 대표 이미지`} src={imageUrl} />
      <StyledInfo>
        <StyledLabelSection>{isAds && <Label text="유료광고" />}</StyledLabelSection>
        <StyledRestaurantNameSection>
          <StyledRestaurantName>{name}</StyledRestaurantName>
          <StyledRightSide>
            <Star />
            <StyledRating>{rating}</StyledRating>
            <StyledReviewCount>({filteredReviewCount})</StyledReviewCount>
          </StyledRightSide>
        </StyledRestaurantNameSection>
        <StyledAddress>{address}</StyledAddress>
        <StyledCategory>{category}</StyledCategory>
      </StyledInfo>
    </StyledContainer>
  );
}

export default RestaurantCard;

const StyledContainer = styled.div`
  display: flex;
  justify-content: start;
  flex-direction: column;

  width: 30.1rem;
  height: 28.7rem;
`;

const StyledImage = styled.img`
  width: 100%;
  height: 19.2rem;

  border-radius: ${BORDER_RADIUS.sm};
`;

const StyledInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem 0;

  width: 100%;
  flex: 1;

  padding: 4px;
`;

const StyledLabelSection = styled.div`
  height: 1.4rem;
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

const StyledAddress = styled.span`
  color: var(--gray-4);
  font-size: ${FONT_SIZE.sm};
`;

const StyledCategory = styled.span`
  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;
