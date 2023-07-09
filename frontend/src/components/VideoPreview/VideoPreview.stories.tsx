import type { Meta, StoryObj } from '@storybook/react';
import VideoPreview from './VideoPreview';

const meta: Meta<typeof VideoPreview> = {
  title: 'VideoPreview',
  component: VideoPreview,
};

export default meta;

type Story = StoryObj<typeof VideoPreview>;

export const Default: Story = {
  args: {
    title: 'ENG) 7500원에 돈가스 무한리필??? 대학교 근처 무한리필 돈까스 집 돈까스 먹방',
    celebName: '히밥 @hebeap',
    viewCount: 6_3000,
    videoUrl: 'https://www.youtube.com/embed/D3lU6KokgVs',
    publishedDate: '2023-07-02T12:00:23Z',
    profileImageUrl:
      'https://yt3.ggpht.com/sL5ugPfl9vvwRwhf6l5APY__BZBw8qWiwgHs-uVsMPFoD5-a4opTJIcRSyrY8aY5LEESOMWJ=s88-c-k-c0x00ffffff-no-rj',
  },
};

export const TextOverFlow: Story = {
  args: {
    title:
      'ENG) 7500원에 돈가스 무한리필??? 대학교 근처 무한리필 돈까스 집 돈까스 먹방 ENG) 7500원에 돈가스 무한리필??? 대학교 근처 무한리필 돈까스 집 돈까스 먹방 ENG) 7500원에 돈가스 무한리필??? 대학교 근처 무한리필 돈까스 집 돈까스 먹방',
    celebName:
      '히밥 @hebeap 히밥 @hebeap 히밥 @hebeap 히밥 @hebeap 히밥 @hebeap 히밥 @hebeap 히밥 @hebeap 히밥 @hebeap 히밥 @hebeap',
    viewCount: 6_3000,
    videoUrl: 'https://www.youtube.com/embed/D3lU6KokgVs',
    publishedDate: '2023-07-02T12:00:23Z',
    profileImageUrl:
      'https://yt3.ggpht.com/sL5ugPfl9vvwRwhf6l5APY__BZBw8qWiwgHs-uVsMPFoD5-a4opTJIcRSyrY8aY5LEESOMWJ=s88-c-k-c0x00ffffff-no-rj',
  },
};
