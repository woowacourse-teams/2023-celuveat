import type { Meta, StoryObj } from '@storybook/react';
import MapModal from './MapModal';

const meta: Meta<typeof MapModal> = {
  title: 'MapModal',
  component: MapModal,
};

export default meta;

type Story = StoryObj<typeof MapModal>;

export const Default: Story = {
  args: {
    modalOpen: true,
    isVisible: false,
    onClickExit: () => {},
    modalRestaurantInfo: {
      id: 1,
      isLiked: false,
      name: '김천재의육회반한연어 신논현본점',
      category: '요리주점',
      roadAddress: '서울 강남구 강남대로118길 47 2층',
      phoneNumber: '0507-1415-1113',
      naverMapUrl:
        'https://map.naver.com/v5/entry/place/38252334?lng=127.02682069999999&lat=37.50750299999999&placePath=%2Fhome&entry=plt',

      images: [
        {
          id: 1,
          name: 'RawFishEater_김천재의육회반한연어_신논현본점.png',
          author: '@RawFishEater',
          sns: '@RawFishEater',
        },
      ],
    },
  },
};
