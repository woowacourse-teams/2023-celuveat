import type { Meta, StoryObj } from '@storybook/react';
import VideoCarousel from './VideoCarousel';
import type { Video } from '~/@types/api.types';

const meta: Meta<typeof VideoCarousel> = {
  title: 'VideoCarousel',
  component: VideoCarousel,
};

export default meta;

type Story = StoryObj<typeof VideoCarousel>;

const video: Video = {
  celebId: 2,
  youtubeChannelName: '@d0dam',
  profileImageUrl: 'asd',
  videoId: 1,
  name: '히밥',
  youtubeVideoKey: 'https://www.youtube.com/embed/D3lU6KokgVs',
  uploadDate: '2023-07-02T12:00:23Z',
};

export const Default: Story = {
  args: {
    title: '셀럽의 다른 영상',
    videos: [video, video, video, video, video, video],
  },
};
