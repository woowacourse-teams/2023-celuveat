import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import styled, { css } from 'styled-components';
import Logo from '~/assets/icons/logo-icon.svg';

import Footer from '~/components/@common/Footer';
import Header from '~/components/@common/Header';
import LoginErrorHandleComponent from '~/components/@common/LoginErrorHandleComponent';
import RestaurantWishList from '~/components/RestaurantWishList';

import useMediaQuery from '~/hooks/useMediaQuery';
import { FONT_SIZE } from '~/styles/common';

function WishListPage() {
  const { isMobile } = useMediaQuery();

  return (
    <StyledWishListPageWrapper>
      <div>
        {isMobile ? (
          <StyledMobileHeader>
            <Link aria-label="셀럽잇 홈페이지" role="button" to="/">
              <Logo width={32} />
            </Link>
            <h5>celuveat</h5>
            <div />
          </StyledMobileHeader>
        ) : (
          <Header />
        )}
        <StyledMobileLayout>
          <StyledTitle isMobile={isMobile}>위시리스트</StyledTitle>
          <RestaurantWishList />
        </StyledMobileLayout>
      </div>
      <Footer />
    </StyledWishListPageWrapper>
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

const StyledMobileHeader = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;

  width: 100%;
  height: 44px;

  padding: 0.2rem 0.8rem;
`;
