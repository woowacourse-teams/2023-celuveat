import type { Meta, StoryObj } from '@storybook/react';
import ImageCarousel from './ImageCarousel';

const meta: Meta<typeof ImageCarousel> = {
  title: 'ImageCarousel',
  component: ImageCarousel,
};

export default meta;

type Story = StoryObj<typeof ImageCarousel>;

export const Default: Story = {
  args: {
    images: [
      { id: 1, name: 'https://picsum.photos/315/300', author: '@d0dam', sns: 'youtube' },
      { id: 2, name: 'https://picsum.photos/315/300', author: '@d0dam', sns: 'youtube' },
      { id: 3, name: 'https://picsum.photos/315/300', author: '@d0dam', sns: 'youtube' },
      { id: 4, name: 'https://picsum.photos/315/300', author: '@d0dam', sns: 'youtube' },
    ],
  },
};

export const OneImage: Story = {
  args: {
    images: [{ id: 1, name: 'https://picsum.photos/315/300', author: '@d0dam', sns: 'youtube' }],
  },
};
