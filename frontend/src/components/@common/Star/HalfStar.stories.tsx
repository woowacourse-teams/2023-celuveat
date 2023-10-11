import type { Meta, StoryObj } from '@storybook/react';
import Star from './HalfStar';

const meta: Meta<typeof Star> = {
  title: 'Star',
  component: Star,
};

export default meta;

type Story = StoryObj<typeof Star>;

export const Default: Story = {
  args: {
    isLeft: true,
    isFilled: false,
  },
};
