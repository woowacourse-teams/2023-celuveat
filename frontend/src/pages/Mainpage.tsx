import { useState } from 'react';
import { styled } from 'styled-components';
import { RestaurantData } from '~/@types/api.types';
import { Coordinate } from '~/@types/map.types';
import Footer from '~/components/@common/Footer';
import Header from '~/components/@common/Header';
import Map from '~/components/@common/Map';
import RestaurantCard from '~/components/RestaurantCard';
import { data } from '~/mocks/data';
import { FONT_SIZE } from '~/styles/common';

const mapArgs = {
  width: '100%',
  height: '100%',
  level: 7,
  mainPosition: { latitude: 37.5057482, longitude: 127.050727 },
  markers: data.map(({ latitude, longitude }) => ({ latitude, longitude })),
};

function MainPage() {
  const [mainPosition, setMainPosition] = useState(mapArgs.mainPosition);

  const switchMainPosition = (position: Coordinate) => {
    setMainPosition({ ...position });
  };

  return (
    <>
      <Header />

      <StyledLayout>
        <StyledLeftSide>
          <StyledCardListHeader>음식점 수 20 개</StyledCardListHeader>
          <StyledRestaurantCardList>
            {data.map(({ celebs, ...restaurant }: RestaurantData) => {
              const { latitude, longitude } = restaurant;

              return (
                <RestaurantCard
                  restaurant={restaurant}
                  celebs={celebs}
                  size={42}
                  onClick={() => switchMainPosition({ latitude, longitude })}
                />
              );
            })}
          </StyledRestaurantCardList>
        </StyledLeftSide>
        <StyledRightSide>
          <Map {...mapArgs} mainPosition={mainPosition} />
        </StyledRightSide>
      </StyledLayout>
      <Footer />
    </>
  );
}

export default MainPage;

const StyledLayout = styled.div`
  display: grid;

  width: 100%;
  height: 100%;
  grid-template-columns: 3fr 2fr;
`;

const StyledLeftSide = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  height: 100%;
`;

const StyledCardListHeader = styled.p`
  margin: 3.2rem 2.4rem;

  font-size: ${FONT_SIZE.md};
`;

const StyledRestaurantCardList = styled.div`
  display: grid;
  gap: 4rem 2.4rem;

  height: 100%;

  margin: 3.2rem 2.4rem;
  grid-template-columns: 1fr 1fr 1fr;
`;

const StyledRightSide = styled.div`
  position: sticky;
  top: 60px;

  width: 100%;
  height: calc(100vh - 60px);
`;
