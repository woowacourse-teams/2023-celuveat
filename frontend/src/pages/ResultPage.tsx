import styled from 'styled-components';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import { FONT_SIZE } from '~/styles/common';

function ResultPage() {
  return (
    <StyledContainer>
      <h5> ← 압구정 청담</h5>
      <StyledResultCount>12개의 매장</StyledResultCount>
      <StyledResultBox>
        <MiniRestaurantCard {...args} showWaterMark={false} />
        <MiniRestaurantCard {...args} showWaterMark={false} />
        <MiniRestaurantCard {...args} showWaterMark={false} />
        <MiniRestaurantCard {...args} showWaterMark={false} />
        <MiniRestaurantCard {...args} showWaterMark={false} />
      </StyledResultBox>
    </StyledContainer>
  );
}

export default ResultPage;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100vw;
  overflow-x: hidden;

  padding: 5.6rem 1.2rem;
`;

const StyledResultCount = styled.span`
  font-size: ${FONT_SIZE.md};
`;

const StyledResultBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;
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
