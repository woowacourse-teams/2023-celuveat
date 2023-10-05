import type { Meta, StoryObj } from '@storybook/react';
import CategoryNavbar from './CategoryNavbar';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';

const meta: Meta<typeof CategoryNavbar> = {
  title: 'Selector/CategoryNavbar',
  component: CategoryNavbar,
};

export default meta;

type Story = StoryObj<typeof CategoryNavbar>;

export const Default: Story = {
  args: {
    categories: RESTAURANT_CATEGORY,
  },
};
