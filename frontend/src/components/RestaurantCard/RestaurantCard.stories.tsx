import type { Meta, StoryObj } from '@storybook/react';
import RestaurantCard from './RestaurantCard';

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
        name: 'a251dG91cl9ncm91bWV0X-unm-yii-ydgOyInOuMgOq1rV8x.jpeg',
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
        'https://yt3.googleusercontent.com/sL5ugPfl9vvwRwhf6l5APY__BZBw8qWiwgHs-uVsMPFoD5-a4opTJIcRSyrY8aY5LEESOMWJ=s176-c-k-c0x00ffffff-no-rj',
    },
  ],
  size: '42px',
};

function RestaurantCardWrapper() {
  return (
    <div style={{ width: '350px', height: '500px' }}>
      <RestaurantCard {...args} />
    </div>
  );
}
const meta: Meta<typeof RestaurantCardWrapper> = {
  title: 'RestaurantCardWrapper',
  component: RestaurantCardWrapper,
};
export default meta;
type Story = StoryObj<typeof RestaurantCardWrapper>;
export const Default: Story = {
  args: {},
};
