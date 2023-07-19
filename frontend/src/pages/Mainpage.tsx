import { useState } from 'react';
import { styled } from 'styled-components';
import { RestaurantData } from '~/@types/api.types';
import { Coordinate } from '~/@types/map.types';
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
    </>
  );
}

export default MainPage;

const StyledLayout = styled.div`
  display: grid;
  grid-template-columns: 3fr 2fr;

  width: 100%;
  height: 100%;
`;

const StyledLeftSide = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  height: calc(100vh - 128px);

  margin: 3.2rem 2.4rem;
`;

const StyledCardListHeader = styled.p`
  font-size: ${FONT_SIZE.md};
`;

const StyledRestaurantCardList = styled.div`
  display: grid;
  gap: 4rem 2.4rem;

  height: 100%;
  grid-template-columns: 1fr 1fr 1fr;

  overflow-y: auto;
  grid-auto-flow: row;
  grid-auto-rows: 360px;
`;

const StyledRightSide = styled.div`
  width: 100%;
  height: 100%;
`;
