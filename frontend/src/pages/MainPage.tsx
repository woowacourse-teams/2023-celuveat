import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { getCelebs } from '~/api/celeb';
import ProfileImage from '~/components/@common/ProfileImage';
import BottomNavBar from '~/components/BottomNavBar';
// import CategoryNavbar from '~/components/CategoryNavbar';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import RegionList from '~/components/RegionList';
// import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import useBooleanState from '~/hooks/useBooleanState';
import useMediaQuery from '~/hooks/useMediaQuery';
import useScrollDirection from '~/hooks/useScrollDirection';
import { popularRestaurants } from '~/mocks/data/popularRestaurants';
import { FONT_SIZE } from '~/styles/common';
import Banner from '~/assets/banner/banner.svg';

function MainPage() {
  const scrollDirection = useScrollDirection();
  const navigate = useNavigate();
  const { value: isListShowed } = useBooleanState(false);
  const { data: celebOptions } = useQuery({
    queryKey: ['celebOptions'],
    queryFn: () => getCelebs(),
    suspense: true,
  });
  const { isMobile } = useMediaQuery();

  const clickCelebIcon = (id: number) => {
    navigate(`/celeb/${id}`);
  };

  return (
    <StyledLayout>
      <StyledContainer>
        <StyledBanner>
          <Banner />
        </StyledBanner>
        <div>
          <h5>셀럽 BEST</h5>
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
          <h5>셀럽잇 추천 맛집!</h5>
          <StyledPopularRestaurantBox>
            {popularRestaurants.map(({ celebs, ...restaurant }) => (
              <MiniRestaurantCard celebs={celebs} restaurant={restaurant} flexColumn showWaterMark={false} />
            ))}
          </StyledPopularRestaurantBox>
        </div>
        {/* <div>
          <h5>카테고리</h5>
          <StyledCategoryBox>
            <CategoryNavbar categories={RESTAURANT_CATEGORY} externalOnClick={() => {}} />
          </StyledCategoryBox>
        </div> */}
        <div>
          <h5>어디로 가시나요?</h5>
          <StyledIconBox>
            <RegionList />
          </StyledIconBox>
        </div>
      </StyledContainer>
      {isMobile && <BottomNavBar isHide={isListShowed && scrollDirection.y === 'down'} />}
    </StyledLayout>
  );
}

export default MainPage;

const StyledLayout = styled.div`
  display: flex;
  justify-content: center;

  width: 100vw;
`;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;
  max-width: 1500px;

  padding: 5.6rem 1.6rem;
  overflow-x: hidden;
`;

const StyledBanner = styled.div`
  width: 100%;

  border-radius: 20px;
  object-fit: cover;
  overflow: hidden;
`;

const StyledIconBox = styled.div`
  display: flex;
  gap: 2rem;

  width: 100%;

  padding: 1.6rem 0.8rem;

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
`;

const StyledPopularRestaurantBox = styled.div`
  display: flex;
  align-items: center;
  gap: 1.2rem;

  overflow-x: scroll;

  &::-webkit-scrollbar {
    display: none;
  }

  padding: 1.6rem 0.8rem;
`;

// const StyledCategoryBox = styled.div`
//   padding: 1.6rem 0;
// `;
