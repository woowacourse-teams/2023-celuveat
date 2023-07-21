import type { Meta, StoryObj } from '@storybook/react';
import LoadingCeluveat from './LoadingCeluveat';

const meta: Meta<typeof LoadingCeluveat> = {
  title: 'Loading/LoadingCeluveat',
  component: LoadingCeluveat,
};

export default meta;

type Story = StoryObj<typeof LoadingCeluveat>;

export const Default: Story = {
  args: { size: 30 },
};
