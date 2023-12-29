import type { Meta, StoryObj } from '@storybook/react';
import InfoDropDown from './InfoDropDown';

const meta: Meta<typeof InfoDropDown> = {
  title: 'Selector/InfoDropDown',
  component: InfoDropDown,
  parameters: {
    layout: 'centered',
  },
};

export default meta;

type Story = StoryObj<typeof InfoDropDown>;

export const Default: Story = {};
