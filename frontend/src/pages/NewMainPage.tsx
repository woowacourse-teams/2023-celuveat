import styled from 'styled-components';
import ProfileImage from '~/components/@common/ProfileImage';
import BottomNavBar from '~/components/BottomNavBar';
import CategoryNavbar from '~/components/CategoryNavbar';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
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
          </StyledCeleb>{' '}
          <StyledCeleb>
            <ProfileImage
              name="제레미"
              imageUrl="https://avatars.githubusercontent.com/u/102432453?v=4"
              size="64px"
              boxShadow
            />
            <span>제레미</span>
          </StyledCeleb>{' '}
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
          <StyledRegion imgUrl="https://img.freepik.com/free-photo/downtown-cityscape-at-night-in-seoul-south-korea_335224-272.jpg?q=10&h=200">
            <StyledRegionName>서울</StyledRegionName>
            <StyledMask />
          </StyledRegion>
          <StyledRegion imgUrl="data:image/jpeg;base64,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">
            <StyledMask />
            <StyledRegionName>제주</StyledRegionName>
          </StyledRegion>
          <StyledRegion imgUrl="data:image/jpeg;base64,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">
            <StyledMask />
            <StyledRegionName>부산</StyledRegionName>
          </StyledRegion>
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

const StyledRegion = styled.div<{ imgUrl: string }>`
  display: flex;
  justify-content: center;
  align-items: center;

  position: relative;

  width: 64px;
  height: 64px;

  border-radius: 100%;
  background-image: ${({ imgUrl }) => `url(${imgUrl})`};
  background-size: cover;

  overflow: hidden;
`;

const StyledMask = styled.div`
  position: absolute;

  width: 100%;
  height: 100%;

  background-color: black;

  opacity: 0.35;
`;

const StyledRegionName = styled.span`
  z-index: 1;

  color: var(--white);
  font-size: ${FONT_SIZE.sm};
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
