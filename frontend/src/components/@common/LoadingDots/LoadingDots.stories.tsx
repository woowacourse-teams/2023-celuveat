import type { Meta, StoryObj } from '@storybook/react';
import LoadingDots from './LoadingDots';

const meta: Meta<typeof LoadingDots> = {
  title: 'Loading/LoadingDots',
  component: LoadingDots,
};

export default meta;

type Story = StoryObj<typeof LoadingDots>;

export const Default: Story = {
  args: { size: 30 },
};
