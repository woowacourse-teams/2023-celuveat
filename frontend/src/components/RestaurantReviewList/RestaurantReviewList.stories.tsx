import type { Meta, StoryObj } from '@storybook/react';
import RestaurantReviewList from '~/components/RestaurantReviewList';
import { mockRestaurantReviews } from '~/mocks/detailPage/fixures';

const meta: Meta<typeof RestaurantReviewList> = {
  title: 'RestaurantReviewList',
  component: RestaurantReviewList,
};

export default meta;

type Story = StoryObj<typeof RestaurantReviewList>;

export const Default: Story = {
  args: {
    reviews: mockRestaurantReviews.reviews,
  },
};

export const Modal: Story = {
  args: {
    reviews: mockRestaurantReviews.reviews,
    isModal: true,
  },
};
