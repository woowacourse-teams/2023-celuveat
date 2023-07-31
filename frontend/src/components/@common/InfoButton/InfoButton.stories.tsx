import type { Meta, StoryObj } from '@storybook/react';
import InfoButton from './InfoButton';

const meta: Meta<typeof InfoButton> = {
  title: 'InfoButton',
  component: InfoButton,
};

export default meta;

type Story = StoryObj<typeof InfoButton>;

export const Default: Story = {
  args: {},
};
