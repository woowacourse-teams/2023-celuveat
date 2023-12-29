import type { Meta, StoryObj } from '@storybook/react';
import RestaurantReviewList from '~/components/RestaurantReviewList';

const meta: Meta<typeof RestaurantReviewList> = {
  title: 'RestaurantReviewList',
  component: RestaurantReviewList,
};

export default meta;

type Story = StoryObj<typeof RestaurantReviewList>;

export const Default: Story = {
  args: {},
};
