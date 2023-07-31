import type { Meta, StoryObj } from '@storybook/react';
import WaterMarkImage from './WaterMarkImage';

const meta: Meta<typeof WaterMarkImage> = {
  title: 'WaterMarkImage',
  component: WaterMarkImage,
};

export default meta;

type Story = StoryObj<typeof WaterMarkImage>;

export const Default: Story = {
  args: {
    waterMark: '@d0dam',
    imageUrl: 'https://picsum.photos/315/300',
  },
};
