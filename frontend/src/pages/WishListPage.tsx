import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styled, { css } from 'styled-components';
import { ProfileData } from '~/@types/api.types';

import Footer from '~/components/@common/Footer';
import Header from '~/components/@common/Header';
import RestaurantWishList from '~/components/RestaurantWishList';
import useUser from '~/hooks/server/useUser';

import useMediaQuery from '~/hooks/useMediaQuery';

function WishListPage() {
  const navigator = useNavigate();
  const { getProfile } = useUser();
  const { isMobile } = useMediaQuery();

  const { isSuccess } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: () => getProfile(),
    staleTime: Infinity,
  });

  console.log(isSuccess);

  useEffect(() => {
    if (!isSuccess) {
      navigator('/signUp');
    }
  }, []);

  return (
    <StyledWishListPageWrapper>
      <div>
        <Header />
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
      margin-top: 6rem;
    `}
`;
