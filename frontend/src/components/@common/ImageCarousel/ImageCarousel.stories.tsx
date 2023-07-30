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
    imageUrls: [
      'https://picsum.photos/315/300',
      'https://picsum.photos/315/300',
      'https://picsum.photos/315/300',
      'https://picsum.photos/315/300',
      'https://picsum.photos/315/300',
      'https://picsum.photos/315/300',
      'https://picsum.photos/315/300',
      'https://picsum.photos/315/300',
    ],
  },
};

export const OneImage: Story = {
  args: { imageUrls: ['https://picsum.photos/315/300'] },
};
