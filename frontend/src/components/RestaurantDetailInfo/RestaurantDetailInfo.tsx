import { styled } from 'styled-components';
import { CopyToClipboard } from 'react-copy-to-clipboard';
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
        <StyledTable>
          <tbody>
            <StyledTableRow>
              <StyledTableHeader>카테고리</StyledTableHeader>
              <StyledTableCell>{category}</StyledTableCell>
            </StyledTableRow>
            <StyledTableRow>
              <StyledTableHeader>전화번호</StyledTableHeader>
              <StyledTableCell>
                {phoneNumber}
                <CopyToClipboard text={phoneNumber}>
                  <StyledCopyButton type="button">복사</StyledCopyButton>
                </CopyToClipboard>
              </StyledTableCell>
            </StyledTableRow>
            <StyledTableRow>
              <StyledTableHeader>주소</StyledTableHeader>
              <StyledTableCell>
                {address}
                <CopyToClipboard text={address}>
                  <StyledCopyButton type="button">복사</StyledCopyButton>
                </CopyToClipboard>
              </StyledTableCell>
            </StyledTableRow>
          </tbody>
        </StyledTable>
      </StyledTextInfo>
      <StyledImage src={imageUrl} />
    </StyledContainer>
  );
}

const StyledContainer = styled.div`
  display: flex;
  justify-content: space-between;

  width: 100%;
  height: fit-content;
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
  flex: 1;
  height: 487px;

  border-radius: ${BORDER_RADIUS.sm};
`;

const StyledTable = styled.table`
  width: 100%;

  border-collapse: collapse;
`;

const StyledTableRow = styled.tr`
  font-size: ${FONT_SIZE.md};
`;

const StyledTableHeader = styled.th`
  padding: 12px;

  color: var(--gray-3);
  text-align: left;
`;

const StyledTableCell = styled.td`
  padding: 10px;
`;

const StyledCopyButton = styled.button`
  background-color: transparent;
  border: none;

  color: var(--gray-3);
`;

export default RestaurantDetailInfo;
