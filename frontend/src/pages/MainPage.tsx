import { useEffect, useState } from 'react';
import { styled, css } from 'styled-components';
import { useMutation } from '@tanstack/react-query';
import { shallow } from 'zustand/shallow';
import Footer from '~/components/@common/Footer';
import Header from '~/components/@common/Header';
import Map from '~/components/@common/Map';
import CategoryNavbar from '~/components/CategoryNavbar';
import CelebDropDown from '~/components/CelebDropDown/CelebDropDown';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import { OPTION_FOR_CELEB_ALL } from '~/constants/options';
import useMediaQuery from '~/hooks/useMediaQuery';
import RestaurantCardList from '~/components/RestaurantCardList';
import { getCelebs } from '~/api';

import type { Celeb } from '~/@types/celeb.types';
import type { RestaurantCategory } from '~/@types/restaurant.types';
import PopUpContainer from '~/components/PopUpContainer';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';

function MainPage() {
  const { isMobile } = useMediaQuery();
  const [setCelebId, setCurrentPage, setRestaurantCategory] = useRestaurantsQueryStringState(
    state => [state.setCelebId, state.setCurrentPage, state.setRestaurantCategory],
    shallow,
  );

  const [isMapExpanded, setIsMapExpanded] = useState(false);
  const [hoveredId, setHoveredId] = useState<number | null>(null);
  const [celebOptions, setCelebOptions] = useState<Celeb[]>();

  const celebOptionsMutation = useMutation({
    mutationFn: () => getCelebs(),
    onSuccess: (data: Celeb[]) => {
      setCelebOptions([OPTION_FOR_CELEB_ALL, ...data]);
    },
  });

  useEffect(() => {
    celebOptionsMutation.mutate();
  }, []);

  const clickRestaurantCategory = (e: React.MouseEvent<HTMLElement>) => {
    const currentCategory = e.currentTarget.dataset.label as RestaurantCategory;

    setRestaurantCategory(currentCategory);
    setCurrentPage(0);
    // refetch();
  };

  const clickCeleb = (e: React.MouseEvent<HTMLElement>) => {
    const currentCelebId = Number(e.currentTarget.dataset.id);

    setCelebId(currentCelebId);
    setCurrentPage(0);
    // refetch();
  };

  const toggleMapExpand = () => setIsMapExpanded(prev => !prev);
  // const bottomSheetHeader = (total: number) => `지도 영역에 있는 음식점 수 ${total}개`;

  return (
    <>
      <Header />
      <StyledNavBar isMobile={isMobile}>
        <CelebDropDown celebs={celebOptions} externalOnClick={clickCeleb} />
        <StyledLine />
        <CategoryNavbar categories={RESTAURANT_CATEGORY} externalOnClick={clickRestaurantCategory} />
      </StyledNavBar>
      <StyledLayout isMapExpanded={isMapExpanded}>
        <StyledLeftSide isMapExpanded={isMapExpanded}>
          <RestaurantCardList setHoveredId={setHoveredId} setCurrentPage={setCurrentPage} />
        </StyledLeftSide>
        <StyledRightSide>
          <Map setCurrentPage={setCurrentPage} toggleMapExpand={toggleMapExpand} hoveredId={hoveredId} />
        </StyledRightSide>
      </StyledLayout>
      <PopUpContainer />
      <Footer />
    </>
  );
}

export default MainPage;

const StyledNavBar = styled.nav<{ isMobile: boolean }>`
  display: flex;
  align-items: center;

  position: ${({ isMobile }) => (isMobile ? 'fixed' : 'sticky')};
  top: ${({ isMobile }) => (isMobile ? '60px' : '80px')};
  z-index: 11;

  width: 100%;
  height: 80px;

  padding-left: 1.2rem;

  background-color: var(--white);
  border-bottom: 1px solid var(--gray-1);
`;

const StyledLine = styled.div`
  margin-left: 1.2rem;

  width: 1px;
  height: 46px;

  background-color: var(--gray-3);
`;

// const StyledLayer = styled.div<{ isMobile: boolean }>`
//   display: flex;
//   flex-direction: column;

//   position: fixed;
//   top: ${({ isMobile }) => (isMobile ? '140px' : '160px')};
//   z-index: 0;

//   width: 100%;
//   height: 100%;
// `;

// const StyledMapBottomCover = styled.div<{ isBottomSheetOpen: boolean }>`
//   position: fixed;
//   bottom: 0;

//   width: 100%;
//   height: 0;

//   background: var(--white);

//   transition: height 0.8s ease-in-out;

//   overflow: hidden;

//   ${({ isBottomSheetOpen }) =>
//     isBottomSheetOpen &&
//     css`
//       z-index: 20;

//       height: calc(36vh - 74px);
//     `}
// `;

// const StyledMobileLayout = styled.main`
//   display: flex;
//   align-items: flex-end;

//   position: relative;

//   width: 100%;
//   height: 100vh;
// `;

const StyledLayout = styled.main<{ isMapExpanded: boolean }>`
  display: grid;

  width: 100%;
  height: 100%;
  grid-template-columns: 63vw 37vw;

  ${({ isMapExpanded }) =>
    isMapExpanded &&
    css`
      grid-template-columns: 100vw;
    `}

  @media screen and (width <= 1240px) {
    grid-template-columns: 55vw 45vw;

    ${({ isMapExpanded }) =>
      isMapExpanded &&
      css`
        grid-template-columns: 100vw;
      `}
  }
`;

const StyledLeftSide = styled.section<{ isMapExpanded: boolean }>`
  z-index: 0;

  ${({ isMapExpanded }) =>
    isMapExpanded &&
    css`
      display: none;
    `}
`;

const StyledRightSide = styled.section`
  position: sticky;
  top: 160px;

  width: 100%;
  height: calc(100vh - 160px);
`;
