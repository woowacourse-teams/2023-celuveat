import { styled, css } from 'styled-components';

import RestaurantWishList from '~/components/RestaurantWishList';
import LoginErrorHandleComponent from '~/components/@common/LoginErrorHandleComponent';

import useMediaQuery from '~/hooks/useMediaQuery';
import { FONT_SIZE } from '~/styles/common';

function WishListPage() {
  const { isMobile } = useMediaQuery();

  return (
    <LoginErrorHandleComponent>
      <StyledWishListPageWrapper>
        <StyledMobileLayout>
          <StyledTitle isMobile={isMobile}>위시리스트</StyledTitle>
          <RestaurantWishList />
        </StyledMobileLayout>
      </StyledWishListPageWrapper>
    </LoginErrorHandleComponent>
  );
}

export default WishListPage;

const StyledWishListPageWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  width: 100%;
  min-height: 100dvh;
`;

const StyledMobileLayout = styled.main`
  display: flex;
  flex-direction: column;

  position: relative;

  width: 100%;
  height: 100%;
`;

const StyledTitle = styled.h3<{ isMobile: boolean }>`
  width: 100%;

  padding: 2rem;

  ${({ isMobile }) =>
    isMobile &&
    css`
      font-size: ${FONT_SIZE.lg};
    `}
`;
