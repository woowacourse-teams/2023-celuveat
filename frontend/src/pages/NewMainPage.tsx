import styled from 'styled-components';
import ProfileImage from '~/components/@common/ProfileImage';
import BottomNavBar from '~/components/BottomNavBar';
import CategoryNavbar from '~/components/CategoryNavbar';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import RegionList from '~/components/RegionList';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import useBooleanState from '~/hooks/useBooleanState';
import useScrollDirection from '~/hooks/useScrollDirection';
import { FONT_SIZE } from '~/styles/common';

function NewMainPage() {
  const scrollDirection = useScrollDirection();
  const { value: isListShowed } = useBooleanState(false);

  return (
    <StyledContainer>
      <StyledBanner>배너 광고 문의 010-5258-1305</StyledBanner>
      <div>
        <StyledSubTitle>셀럽</StyledSubTitle>
        <StyledIconBox>
          <StyledCeleb>
            <ProfileImage
              name="제레미"
              imageUrl="https://avatars.githubusercontent.com/u/102432453?v=4"
              size="64px"
              boxShadow
            />
            <span>제레미</span>
          </StyledCeleb>
          <StyledCeleb>
            <ProfileImage
              name="제레미"
              imageUrl="https://avatars.githubusercontent.com/u/102432453?v=4"
              size="64px"
              boxShadow
            />
            <span>제레미</span>
          </StyledCeleb>
          <StyledCeleb>
            <ProfileImage
              name="제레미"
              imageUrl="https://avatars.githubusercontent.com/u/102432453?v=4"
              size="64px"
              boxShadow
            />
            <span>제레미</span>
          </StyledCeleb>
          <StyledCeleb>
            <ProfileImage
              name="제레미"
              imageUrl="https://avatars.githubusercontent.com/u/102432453?v=4"
              size="64px"
              boxShadow
            />
            <span>제레미</span>
          </StyledCeleb>
        </StyledIconBox>
      </div>
      <div>
        <StyledSubTitle>인기있는 맛집</StyledSubTitle>
        <StyledPopularRestaurantBox>
          <MiniRestaurantCard {...args} flexColumn showWaterMark={false} />
          <MiniRestaurantCard {...args} flexColumn showWaterMark={false} />
          <MiniRestaurantCard {...args} flexColumn showWaterMark={false} />
        </StyledPopularRestaurantBox>
      </div>
      <div>
        <StyledSubTitle>카테고리</StyledSubTitle>
        <StyledCategoryBox>
          <CategoryNavbar categories={RESTAURANT_CATEGORY} externalOnClick={() => {}} />
        </StyledCategoryBox>
      </div>
      <div>
        <StyledSubTitle>지역</StyledSubTitle>
        <StyledIconBox>
          <RegionList />
        </StyledIconBox>
      </div>
      <BottomNavBar isHide={isListShowed && scrollDirection.y === 'down'} />
    </StyledContainer>
  );
}

export default NewMainPage;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100vw;
  overflow-x: hidden;

  padding: 5.6rem 1.2rem;
`;

const StyledBanner = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 120px;

  background-color: var(--primary-6);

  color: white;
  font-size: ${FONT_SIZE.lg};
`;

const StyledSubTitle = styled.h5``;

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

const StyledCategoryBox = styled.div`
  padding: 1.6rem 0;
`;

const args = {
  restaurant: {
    lat: 37.5308887,
    lng: 127.0737184,
    id: 315,
    name: '맛좋은순대국',
    category: '한식',
    roadAddress: '서울 광진구 자양번영로1길 22',
    phoneNumber: '02-458-5737',
    naverMapUrl: 'https://map.naver.com/v5/entry/place/17990788?c=15,0,0,0,dh',
    viewCount: 415,
    distance: 2586,
    isLiked: false,
    likeCount: 8,
    celebs: [
      {
        id: 7,
        name: '성시경 SUNG SI KYUNG',
        youtubeChannelName: '@sungsikyung',
        profileImageUrl:
          'https://yt3.googleusercontent.com/vQrdlCaT4Tx1axJtSUa1oxp2zlnRxH-oMreTwWqB-2tdNFStIOrWWw-0jwPvVCUEjm_MywltBFY=s176-c-k-c0x00ffffff-no-rj',
      },
    ],
    images: [
      {
        id: 383,
        name: 'eW91bmNoZW9sam9vX-yEnOumsOuCmeyngF8x',
        author: '@knutour_groumet',
        sns: 'INSTAGRAM',
      },
      {
        id: 384,
        name: 'a251dG91cl9ncm91bWV0X-unm-yii-ydgOyInOuMgOq1rV8y.jpeg',
        author: '@knutour_groumet',
        sns: 'INSTAGRAM',
      },
      {
        id: 1388,
        name: 'a251dG91cl9ncm91bWV0LTE.jpeg',
        author: '@knutour_groumet',
        sns: 'INSTAGRAM',
      },
      {
        id: 1389,
        name: 'a251dG91cl9ncm91bWV0LTI.jpeg',
        author: '@knutour_groumet',
        sns: 'INSTAGRAM',
      },
      {
        id: 1390,
        name: 'a251dG91cl9ncm91bWV0LTM.jpeg',
        author: '@knutour_groumet',
        sns: 'INSTAGRAM',
      },
      {
        id: 1391,
        name: 'a251dG91cl9ncm91bWV0LTQ.jpeg',
        author: '@knutour_groumet',
        sns: 'INSTAGRAM',
      },
    ],
  },
  celebs: [
    {
      id: 1,
      name: '히밥',
      youtubeChannelName: '@heebab',
      profileImageUrl:
        'https://yt3.googleusercontent.com/sL5ugPfl9vvwRwhf6l5APY__BZBw8qWiwgHs-uVsMPFoD5-a4opTJIcRSyrY8aY5LEESOMWJ=s176-c-k-c0x00ffffff-no-rj',
    },
    {
      id: 2,
      name: '히밥',
      youtubeChannelName: '@heebab',
      profileImageUrl:
        'https://avatars.githubusercontent.com/u/102432453?s=400&u=8844baf7325b88634e8ee0e640579b012479cff8&v=4',
    },
  ],
};
