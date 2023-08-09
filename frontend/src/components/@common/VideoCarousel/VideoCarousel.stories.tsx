import type { Meta, StoryObj } from '@storybook/react';
import VideoCarousel from './VideoCarousel';

const meta: Meta<typeof VideoCarousel> = {
  title: 'VideoCarousel',
  component: VideoCarousel,
};

export default meta;

type Story = StoryObj<typeof VideoCarousel>;

const video = {
  videoId: 1,
  name: '히밥',
  youtubeUrl: 'https://www.youtube.com/embed/D3lU6KokgVs?autoplay=1',
  uploadDate: '2023-07-02T12:00:23Z',
};

export const Default: Story = {
  args: {
    title: '셀럽의 다른 영상',
    videos: [video, video, video, video, video, video],
  },
};
