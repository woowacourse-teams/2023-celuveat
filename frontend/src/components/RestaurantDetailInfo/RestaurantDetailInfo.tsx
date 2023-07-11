import { styled } from 'styled-components';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import Star from '~/assets/icons/star.svg';
import getFilteredReviewCount from '~/utils/getFilteredReviewCount';

interface RestaurantDetailInfoProps {
  imageUrl: string;
  name: string;
  address: string;
  category: string;
  rating: string;
  reviewCount: number;
  phoneNumber: string;
}

function RestaurantDetailInfo({
  imageUrl,
  name,
  address,
  category,
  rating,
  reviewCount,
  phoneNumber,
}: RestaurantDetailInfoProps) {
  return (
    <StyledContainer>
      <StyledTextInfo>
        <RestaurantNameSection>
          <h1>{name}</h1>
          <StyledRightSide>
            <Star width="20px" height="20px" />
            <StyledRating>{rating}</StyledRating>
            <StyledReviewCount>({getFilteredReviewCount(reviewCount)})</StyledReviewCount>
          </StyledRightSide>
        </RestaurantNameSection>
        <Table>
          <tbody>
            <TableRow>
              <TableHeader>카테고리</TableHeader>
              <TableCell>{category}</TableCell>
            </TableRow>
            <TableRow>
              <TableHeader>전화번호</TableHeader>
              <TableCell>{phoneNumber}</TableCell>
            </TableRow>
            <TableRow>
              <TableHeader>주소</TableHeader>
              <TableCell>{address}</TableCell>
            </TableRow>
          </tbody>
        </Table>
      </StyledTextInfo>
      <StyledImage src={imageUrl} />
    </StyledContainer>
  );
}

const StyledContainer = styled.div`
  display: flex;
  justify-content: space-between;

  width: 1458px;
  height: 487px;
`;

const StyledTextInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;

  width: 50%;

  margin-right: 16px;
`;

const RestaurantNameSection = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  padding: 8px 12px;

  border-bottom: 1px solid var(--gray-3);
`;

const StyledRightSide = styled.div`
  display: flex;
  gap: 0 4px;
`;

const StyledRating = styled.span`
  font-size: ${FONT_SIZE.lg};
`;

const StyledReviewCount = styled.span`
  color: var(--gray-3);
  font-size: ${FONT_SIZE.lg};
`;

const StyledImage = styled.img`
  height: 100%;
  flex: 1;

  border-radius: ${BORDER_RADIUS.sm};
`;

const Table = styled.table`
  width: 100%;

  border-collapse: collapse;
`;

const TableRow = styled.tr`
  font-size: ${FONT_SIZE.md};
`;

const TableHeader = styled.th`
  padding: 12px;

  color: var(--gray-3);
  text-align: left;
`;

const TableCell = styled.td`
  padding: 10px;
`;

export default RestaurantDetailInfo;
