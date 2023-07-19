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
    name: '도담',
    youtubeChannelName: '@d0dam',
    subscriberCount: 650_000,
    restaurantCount: 123,
    youtubeChannelUrl: 'https://www.youtube.com/watch?v=D3lU6KokgVs',
    profileImageUrl: 'https://avatars.githubusercontent.com/u/51052049?v=4',
    backgroundImageUrl: null,
  },
};
