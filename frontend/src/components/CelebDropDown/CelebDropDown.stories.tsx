import type { Meta, StoryObj } from '@storybook/react';
import CelebDropDown from './CelebDropDown';

const meta: Meta<typeof CelebDropDown> = {
  title: 'Selector/CelebDropDown',
  component: CelebDropDown,
};

export default meta;

type Story = StoryObj<typeof CelebDropDown>;

export const Default: Story = {
  args: {
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
        name: '정찬성',
        youtubeChannelName: '@Korean_zzombi',
        profileImageUrl:
          'https://yt3.googleusercontent.com/sL5ugPfl9vvwRwhf6l5APY__BZBw8qWiwgHs-uVsMPFoD5-a4opTJIcRSyrY8aY5LEESOMWJ=s176-c-k-c0x00ffffff-no-rj',
      },
      {
        id: 3,
        name: '정찬',
        youtubeChannelName: '@Korean_zzombi',
        profileImageUrl:
          'https://yt3.googleusercontent.com/sL5ugPfl9vvwRwhf6l5APY__BZBw8qWiwgHs-uVsMPFoD5-a4opTJIcRSyrY8aY5LEESOMWJ=s176-c-k-c0x00ffffff-no-rj',
      },
      {
        id: 4,
        name: '정성',
        youtubeChannelName: '@Korean_zzombi',
        profileImageUrl:
          'https://yt3.googleusercontent.com/sL5ugPfl9vvwRwhf6l5APY__BZBw8qWiwgHs-uVsMPFoD5-a4opTJIcRSyrY8aY5LEESOMWJ=s176-c-k-c0x00ffffff-no-rj',
      },
      {
        id: 5,
        name: '정찬성1',
        youtubeChannelName: '@Korean_zzombi',
        profileImageUrl:
          'https://yt3.googleusercontent.com/sL5ugPfl9vvwRwhf6l5APY__BZBw8qWiwgHs-uVsMPFoD5-a4opTJIcRSyrY8aY5LEESOMWJ=s176-c-k-c0x00ffffff-no-rj',
      },
    ],
  },
};
