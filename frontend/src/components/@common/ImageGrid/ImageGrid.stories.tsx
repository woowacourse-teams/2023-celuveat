import type { Meta, StoryObj } from '@storybook/react';
import ImageGrid from './ImageGrid';

const meta: Meta<typeof ImageGrid> = {
  title: 'ImageGrid',
  component: ImageGrid,
};

export default meta;

type Story = StoryObj<typeof ImageGrid>;

export const Default: Story = {
  args: {
    images: [
      {
        waterMark: '@d0dam',
        url: 'https://random.imagecdn.app/500/500',
      },
      {
        waterMark: '@jjj',
        url: 'https://random.imagecdn.app/500/500',
      },
      {
        waterMark: '@mallangcow',
        url: 'https://random.imagecdn.app/500/500',
      },
      {
        waterMark: '@royeee',
        url: 'https://random.imagecdn.app/500/500',
      },
      {
        waterMark: '@dogydogy',
        url: 'https://random.imagecdn.app/500/500',
      },
    ],
  },
};
