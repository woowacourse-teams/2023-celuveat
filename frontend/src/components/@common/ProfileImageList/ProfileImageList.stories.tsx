import type { Meta, StoryObj } from '@storybook/react';
import ProfileImageList from './ProfileImageList';

const meta: Meta<typeof ProfileImageList> = {
  title: 'ProfileImageList',
  component: ProfileImageList,
};

export default meta;

type Story = StoryObj<typeof ProfileImageList>;

export const Default: Story = {
  args: {
    celebs: [
      {
        name: '누군가',
        profileImageUrl: 'https://avatars.githubusercontent.com/u/51052049?v=4',
        id: 1,
        youtubeChannelName: '@d0dam',
      },
      {
        name: '누군가',
        profileImageUrl: 'https://avatars.githubusercontent.com/u/51052049?v=4',
        id: 2,
        youtubeChannelName: '@d0dam',
      },
      {
        name: '누군가',
        profileImageUrl: 'https://avatars.githubusercontent.com/u/51052049?v=4',
        id: 3,
        youtubeChannelName: '@d0dam',
      },
      {
        name: '누군가',
        profileImageUrl: 'https://avatars.githubusercontent.com/u/51052049?v=4',
        id: 4,
        youtubeChannelName: '@d0dam',
      },
    ],
    size: 42,
  },
};
