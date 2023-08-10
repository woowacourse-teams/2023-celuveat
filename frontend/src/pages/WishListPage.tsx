import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styled, { css } from 'styled-components';

import Footer from '~/components/@common/Footer';
import Header from '~/components/@common/Header';
import RestaurantWishList from '~/components/RestaurantWishList';
import useTokenState from '~/hooks/store/useTokenState';

import useMediaQuery from '~/hooks/useMediaQuery';
import { isEmptyString } from '~/utils/compare';

function WishListPage() {
  const { isMobile } = useMediaQuery();

  const token = useTokenState(state => state.token);
  const navigator = useNavigate();

  useEffect(() => {
    if (isEmptyString(token)) {
      navigator('/signUp');
    }
  }, []);

  return (
    <>
      <Header />
      <StyledMobileLayout>
        <StyledTitle isMobile={isMobile}>위시리스트</StyledTitle>
        <RestaurantWishList />
      </StyledMobileLayout>
      <Footer />
    </>
  );
}

export default WishListPage;

const StyledMobileLayout = styled.div`
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
      margin-top: 6rem;
    `}
`;
