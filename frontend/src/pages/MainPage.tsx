import { useQuery } from '@tanstack/react-query';
import { Link, useNavigate } from 'react-router-dom';
import styled, { css } from 'styled-components';
import { Helmet } from 'react-helmet-async';
import { useEffect } from 'react';
import { getCelebs } from '~/api/celeb';
import ProfileImage from '~/components/@common/ProfileImage';
import CategoryNavbar from '~/components/CategoryNavbar';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import RegionList from '~/components/RegionList';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import { FONT_SIZE } from '~/styles/common';
import { RestaurantData } from '~/@types/api.types';
import { getRecommendedRestaurants } from '~/api/restaurant';
import { SERVER_IMG_URL } from '~/constants/url';
import useMediaQuery from '~/hooks/useMediaQuery';
import useBottomNavBarState from '~/hooks/store/useBottomNavBarState';

function MainPage() {
  const navigate = useNavigate();
  const { isMobile } = useMediaQuery();
  const { data: celebOptions } = useQuery({
    queryKey: ['celebOptions'],
    queryFn: () => getCelebs(),
    suspense: true,
  });
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
        <Link to="/updated-recent">
          <StyledBannerSection>
            <StyledBanner
              alt="최근 업데이트된 맛집"
              src={`${SERVER_IMG_URL}banner/recent-updated.jpg`}
              isMobile={isMobile}
            />
          </StyledBannerSection>
        </Link>
        <div>
          <StyledTitle>셀럽 BEST</StyledTitle>
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
        </div>
        <div>
          <StyledTitle>셀럽잇 추천 맛집!</StyledTitle>
          <StyledPopularRestaurantBox>
            {recommendedRestaurantData?.map(({ celebs, ...restaurant }) => (
              <MiniRestaurantCard celebs={celebs} restaurant={restaurant} flexColumn showRating />
            ))}
          </StyledPopularRestaurantBox>
        </div>

        <div>
          <StyledTitle>어디로 가시나요?</StyledTitle>
          <StyledIconBox>
            <RegionList />
          </StyledIconBox>
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

const StyledTitle = styled.h5`
  margin-left: 1.6rem;
`;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;

  overflow-x: hidden;

  box-sizing: content-box;

  max-height: 1240px;

  margin: 0 auto;
  padding-bottom: 4.4rem;
`;

const StyledBannerSection = styled.section`
  display: flex;
  justify-content: center;

  width: 100%;
`;

const StyledBanner = styled.img<{ isMobile: boolean }>`
  width: 100%;
  max-width: 800px;
  height: 200px;
  max-height: 200px;

  object-fit: cover;

  overflow: hidden;

  ${({ isMobile }) =>
    !isMobile &&
    css`
      margin: 1.2rem;
    `}
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
  display: flex;
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

  &::-webkit-scrollbar {
    display: none;
  }
`;

const StyledCategoryBox = styled.div`
  padding: 1.6rem 0.8rem;
`;
