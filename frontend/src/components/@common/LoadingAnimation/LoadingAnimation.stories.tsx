import type { Meta, StoryObj } from '@storybook/react';
import LoadingAnimation from './LoadingAnimation';

const meta: Meta<typeof LoadingAnimation> = {
  title: 'LoadingAnimation',
  component: LoadingAnimation,
};

export default meta;

type Story = StoryObj<typeof LoadingAnimation>;

export const Default: Story = {
  args: { size: 30 },
};
