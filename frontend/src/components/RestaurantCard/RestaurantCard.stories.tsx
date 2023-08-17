import type { Meta, StoryObj } from '@storybook/react';
import RestaurantCard from './RestaurantCard';

const args = {
  restaurant: {
    id: 1,
    name: '스시렌',
    category: '일식당',
    distance: 256,
    roadAddress: '서울 강남구 선릉로146길 27-8 2F',
    lat: 37.5222779,
    lng: 127.0423149,
    phoneNumber: '010-8072-2032',
    naverMapUrl: 'https://naver.me/58HxhMsl',
    images: [
      {
        id: 1,
        name: 'https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzAzMjhfMjY1%2FMDAxNjc5OTk0NjYxMDI5.Mo-i3h1Q8kR4yi0hOL2lQZdA6t6uiQ599aBNnnJ83q8g._NGlnMeHtVCiJVWenUbbtICefoddkW1Wg0g3PCxn9Q4g.JPEG.twinkle_paul%2F100V7467-2.jpg',
        author: '@mheebab',
        sns: 'YOUTUBE',
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
