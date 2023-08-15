import type { Meta, StoryObj } from '@storybook/react';
import CelebNavbar from './CelebNavbar';

const meta: Meta<typeof CelebNavbar> = {
  title: 'Selector/CelebNavbar',
  component: CelebNavbar,
};

export default meta;

type Story = StoryObj<typeof CelebNavbar>;

export const Default: Story = {
  args: {},
};
