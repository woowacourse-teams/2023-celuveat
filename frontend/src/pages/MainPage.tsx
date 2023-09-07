import { Suspense } from 'react';
import { styled, css } from 'styled-components';
import Footer from '~/components/@common/Footer';
import Header from '~/components/@common/Header';
import Map from '~/components/@common/Map';
import RestaurantCardList from '~/components/RestaurantCardList';
import PopUpContainer from '~/components/PopUpContainer';
import MainPageNavBar from '~/components/MainPageNavBar';
import useBooleanState from '~/hooks/useBooleanState';

function MainPage() {
  const { value: isMapExpanded, toggle: toggleExpandedMap } = useBooleanState(false);

  return (
    <>
      <Header />
      <Suspense fallback={<div>로딩중...</div>}>
        <MainPageNavBar />
      </Suspense>
      <StyledLayout isMapExpanded={isMapExpanded}>
        <StyledLeftSide isMapExpanded={isMapExpanded}>
          <RestaurantCardList />
        </StyledLeftSide>
        <StyledRightSide>
          <Map toggleMapExpand={toggleExpandedMap} />
        </StyledRightSide>
      </StyledLayout>
      <PopUpContainer />
      <Footer />
    </>
  );
}

export default MainPage;

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
