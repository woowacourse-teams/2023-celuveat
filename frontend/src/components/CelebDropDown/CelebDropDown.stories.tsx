import type { Meta, StoryObj } from '@storybook/react';
import CelebDropDown from './CelebDropDown';

const meta: Meta<typeof CelebDropDown> = {
  title: 'Selector/CelebDropDown',
  component: CelebDropDown,
};

export default meta;

type Story = StoryObj<typeof CelebDropDown>;

export const Default: Story = {};
