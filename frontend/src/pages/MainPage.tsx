import styled from 'styled-components';
import { useEffect } from 'react';
import useBottomNavBarState from '~/hooks/store/useBottomNavBarState';
import CeluveatRecommendSection from './MainPage/CeluveatRecommendSection/CeluveatRecommendSection';
import CelebBestSection from './MainPage/CelebBestSection/CelebBestSection';
import CategorySection from './MainPage/CategorySection/CategorySection';
import BannerSection from './MainPage/BannerSection/BannerSection';
import RegionSection from './MainPage/RegionSection/RegionSection';

function MainPage() {
  const setHomeSelected = useBottomNavBarState(state => state.setHomeSelected);

  useEffect(() => {
    setHomeSelected();
  }, []);

  return (
    <StyledContainer>
      <BannerSection />
      <CelebBestSection />
      <CeluveatRecommendSection />
      <RegionSection />
      <CategorySection />
    </StyledContainer>
  );
}

export default MainPage;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;

  box-sizing: content-box;

  max-width: 1240px;

  margin: 0 auto;

  padding-bottom: 4.4rem;
`;
