import type { Meta, StoryObj } from '@storybook/react';
import StarRating from './StarRating';

const meta: Meta<typeof StarRating> = {
  title: 'StarRating',
  component: StarRating,
};

export default meta;

type Story = StoryObj<typeof StarRating>;

export const Default: Story = {
  args: {
    rate: 3.5,
  },
};
