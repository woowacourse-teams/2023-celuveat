import type { Meta, StoryObj } from '@storybook/react';
import RestaurantReviewList from '~/components/RestaurantReviewList';
import { restaurantReviews } from '~/mocks/fixtures';

const meta: Meta<typeof RestaurantReviewList> = {
  title: 'RestaurantReviewList',
  component: RestaurantReviewList,
};

export default meta;

type Story = StoryObj<typeof RestaurantReviewList>;

export const Default: Story = {
  args: {
    reviews: restaurantReviews.reviews,
  },
};

export const Modal: Story = {
  args: {
    reviews: restaurantReviews.reviews,
    isModal: true,
  },
};
