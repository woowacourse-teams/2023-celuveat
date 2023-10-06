import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { getCelebs } from '~/api/celeb';
import ProfileImage from '~/components/@common/ProfileImage';
import CategoryNavbar from '~/components/CategoryNavbar';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import RegionList from '~/components/RegionList';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import { popularRestaurants } from '~/mocks/data/popularRestaurants';
import { FONT_SIZE } from '~/styles/common';
import Banner from '~/assets/banner/banner.svg';

function MainPage() {
  const navigate = useNavigate();
  const { data: celebOptions } = useQuery({
    queryKey: ['celebOptions'],
    queryFn: () => getCelebs(),
    suspense: true,
  });

  const clickCelebIcon = (id: number) => {
    navigate(`/celeb/${id}`);
  };

  const clickRestaurantCategory = (e: React.MouseEvent<HTMLElement>) => {
    const currentCategory = e.currentTarget.dataset.label;

    navigate(`/category/${currentCategory}`);
  };

  return (
    <StyledLayout>
      <StyledContainer>
        <StyledBanner>
          <Banner />
        </StyledBanner>
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
            {popularRestaurants.map(({ celebs, ...restaurant }) => (
              <MiniRestaurantCard celebs={celebs} restaurant={restaurant} flexColumn showWaterMark={false} />
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
    </StyledLayout>
  );
}

export default MainPage;

const StyledTitle = styled.h5`
  margin-left: 1.6rem;
`;

const StyledLayout = styled.div`
  display: flex;
  justify-content: center;

  width: 100%;
`;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;
  max-width: 1200px;

  overflow-x: hidden;

  box-sizing: content-box;
`;

const StyledBanner = styled.div`
  width: 100%;
  max-height: 200px;

  object-fit: cover;

  overflow: hidden;
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
  padding: 1.6rem 0;
`;
