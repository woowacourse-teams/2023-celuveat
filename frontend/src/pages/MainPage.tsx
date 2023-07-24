import { useEffect, useState } from 'react';
import { styled } from 'styled-components';
import { RestaurantData } from '~/@types/api.types';
import { Coordinate } from '~/@types/map.types';
import { Restaurant, RestaurantModalInfo } from '~/@types/restaurant.types';
import Footer from '~/components/@common/Footer';
import Header from '~/components/@common/Header';
import Map from '~/components/@common/Map';
import MapModal from '~/components/MapModal/MapModal';
import RestaurantCard from '~/components/RestaurantCard';
import useMapModal from '~/hooks/useMapModal';
import { FONT_SIZE } from '~/styles/common';

function MainPage() {
  const [center, setCenter] = useState({ lat: 37.5057482, lng: 127.050727 });
  const [currentRestaurant, setCurrentRestaurant] = useState<RestaurantModalInfo | null>(null);
  const { modalOpen, isVisible, closeModal, openModal } = useMapModal(true);
  const [data, setData] = useState<RestaurantData[]>([]);
  const mapArgs = {
    center: { lat: 37.5057482, lng: 127.050727 },
    zoom: 13,
    size: { width: '100%', height: '100%' },
    restaurants: data,
  };

  useEffect(() => {
    const fetchRestaurant = async () => {
      const response = await fetch('http://3.35.157.27/api/restaurants');
      const newData = await response.json();

      setData(newData);
    };
    fetchRestaurant();
  }, []);

  const clickCard = (restaurant: Restaurant) => {
    const { lat, lng, ...restaurantModalInfo } = restaurant;

    openModal();
    setCenter({ lat, lng });
    setCurrentRestaurant(restaurantModalInfo);
  };

  const clickMarker = ({ lat, lng }: Coordinate) => {
    const filteredRestaurant = data.find(restaurantData => lat === restaurantData.lat && lng === restaurantData.lng);

    const { id, name, category, roadAddress, phoneNumber, naverMapUrl, images }: RestaurantModalInfo =
      filteredRestaurant;

    setCurrentRestaurant({ id, name, category, roadAddress, phoneNumber, naverMapUrl, images });
    setCenter({ lat, lng });
  };

  return (
    <>
      <Header />

      <StyledLayout>
        <StyledLeftSide>
          <StyledCardListHeader>음식점 수 {data.length} 개</StyledCardListHeader>
          <StyledRestaurantCardList>
            {data.map(({ celebs, ...restaurant }: RestaurantData) => (
              <RestaurantCard restaurant={restaurant} celebs={celebs} size={42} onClick={() => clickCard(restaurant)} />
            ))}
          </StyledRestaurantCardList>
        </StyledLeftSide>
        <StyledRightSide>
          <Map {...mapArgs} center={center} clickMarker={clickMarker} />
          {currentRestaurant && (
            <MapModal
              modalOpen={modalOpen}
              isVisible={isVisible}
              onClickExit={closeModal}
              modalRestaurantInfo={currentRestaurant}
            />
          )}
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

  margin: 0 2.4rem;
  grid-template-columns: 1fr 1fr 1fr;
`;

const StyledRightSide = styled.div`
  position: sticky;
  top: 60px;

  width: 100%;
  height: calc(100vh - 60px);
`;
