import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { Helmet } from 'react-helmet-async';
import { useEffect } from 'react';
import Slider from 'react-slick';
import ProfileImage from '~/components/@common/ProfileImage';
import CategoryNavbar from '~/components/CategoryNavbar';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import RegionList from '~/components/RegionList';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import { FONT_SIZE, hideScrollBar } from '~/styles/common';
import { RestaurantData } from '~/@types/api.types';
import { getRecommendedRestaurants } from '~/api/restaurant';
import useBottomNavBarState from '~/hooks/store/useBottomNavBarState';
import BannerSlider from './MainPage/BannerSlider';
import { CelebCarouselSettings, RestaurantCardCarouselSettings } from '~/constants/carouselSettings';
import useMediaQuery from '~/hooks/useMediaQuery';
import { celebOptions } from '~/constants/celeb';
import MiniRestaurantCardSkeleton from '~/components/MiniRestaurantCard/MiniRestaurantCardSkeleton';

function MainPage() {
  const { isMobile } = useMediaQuery();
  const navigate = useNavigate();
  // const { data: celebOptions } = useQuery({
  //   queryKey: ['celebOptions'],
  //   queryFn: () => getCelebs(),
  //   suspense: true,
  // });
  const setHomeSelected = useBottomNavBarState(state => state.setHomeSelected);

  useEffect(() => {
    setHomeSelected();
  }, []);

  const { data: recommendedRestaurantData } = useQuery<RestaurantData[]>({
    queryKey: ['recommendedRestaurants'],
    queryFn: getRecommendedRestaurants,
  });

  const clickCelebIcon = (id: number) => {
    navigate(`/celeb/${id}`);
  };

  const clickRestaurantCategory = (e: React.MouseEvent<HTMLElement>) => {
    const currentCategory = e.currentTarget.dataset.label;

    navigate(`/category/${currentCategory}`);
  };

  return (
    <>
      <Helmet>
        <meta property="og:title" content="Celuveat" />
        <meta property="og:url" content="celuveat.com" />
        <meta name="image" property="og:image" content="https://www.celuveat.com/og-image.jpeg" />
        <meta name="description" property="og:description" content="셀럽 추천 맛집 서비스, 셀럽잇" />
      </Helmet>
      <StyledContainer>
        {isMobile ? (
          <BannerSlider />
        ) : (
          <StyledSliderContainer>
            <BannerSlider />
          </StyledSliderContainer>
        )}

        <div>
          <StyledTitle>셀럽 BEST</StyledTitle>
          {isMobile ? (
            <StyledIconBox>
              {celebOptions.map(celeb => {
                const { name, profileImageUrl, id } = celeb;
                return (
                  <StyledCeleb onClick={() => clickCelebIcon(id)}>
                    <ProfileImage name={name} imageUrl={profileImageUrl} size="64px" boxShadow />
                    <span>{name}</span>
                  </StyledCeleb>
                );
              })}
            </StyledIconBox>
          ) : (
            <StyledSliderContainer>
              <Slider {...CelebCarouselSettings}>
                {celebOptions.map(celeb => {
                  const { name, profileImageUrl, id } = celeb;
                  return (
                    <StyledCeleb onClick={() => clickCelebIcon(id)}>
                      <ProfileImage name={name} imageUrl={profileImageUrl} size="64px" boxShadow />
                      <span>{name}</span>
                    </StyledCeleb>
                  );
                })}
              </Slider>
            </StyledSliderContainer>
          )}
        </div>

        <div>
          <StyledTitle>셀럽잇 추천 맛집!</StyledTitle>
          {isMobile ? (
            <StyledPopularRestaurantBox>
              {recommendedRestaurantData ? (
                recommendedRestaurantData?.map(({ celebs, ...restaurant }) => (
                  <MiniRestaurantCard celebs={celebs} restaurant={restaurant} flexColumn showRating />
                ))
              ) : (
                <>
                  <MiniRestaurantCardSkeleton flexColumn />
                  <MiniRestaurantCardSkeleton flexColumn />
                  <MiniRestaurantCardSkeleton flexColumn />
                  <MiniRestaurantCardSkeleton flexColumn />
                  <MiniRestaurantCardSkeleton flexColumn />
                </>
              )}
            </StyledPopularRestaurantBox>
          ) : (
            <StyledSliderContainer>
              <Slider {...RestaurantCardCarouselSettings}>
                {recommendedRestaurantData
                  ? recommendedRestaurantData?.map(({ celebs, ...restaurant }) => (
                      <MiniRestaurantCard
                        celebs={celebs}
                        restaurant={restaurant}
                        flexColumn
                        showRating
                        isCarouselItem
                      />
                    ))
                  : new Array(5).fill('1').map(() => <MiniRestaurantCardSkeleton flexColumn isCarouselItem />)}
              </Slider>
            </StyledSliderContainer>
          )}
        </div>

        <div>
          <StyledTitle>어디로 가시나요?</StyledTitle>
          <RegionList />
        </div>
        <div>
          <StyledTitle>카테고리</StyledTitle>
          <StyledCategoryBox>
            <CategoryNavbar
              categories={RESTAURANT_CATEGORY}
              externalOnClick={clickRestaurantCategory}
              includeAll={false}
              grid
            />
          </StyledCategoryBox>
        </div>
      </StyledContainer>
    </>
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

const StyledIconBox = styled.div`
  display: flex;
  gap: 2rem;

  padding: 1.6rem;

  justify-items: flex-start;

  overflow-x: scroll;

  &::-webkit-scrollbar {
    display: none;
  }
`;

const StyledCeleb = styled.div`
  display: flex !important;
  flex-direction: column;
  align-items: center;
  gap: 0.8rem;

  font-size: ${FONT_SIZE.sm};

  cursor: pointer;
`;

const StyledPopularRestaurantBox = styled.div`
  display: flex;
  align-items: center;
  gap: 1.2rem;

  padding: 1.6rem;

  overflow-x: scroll;

  ${hideScrollBar}
`;

const StyledCategoryBox = styled.div`
  padding: 1.6rem 0.8rem;
`;

const StyledTitle = styled.h5`
  margin-left: 1.6rem;
`;

const StyledSliderContainer = styled.div`
  padding: 2rem 4rem;
`;
