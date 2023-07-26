import type { Meta, StoryObj } from '@storybook/react';
import CategoryNavbar from './CategoryNavbar';
import FastFood from '~/assets/icons/fastFood.svg';
import { RestaurantCategory } from '~/@types/restaurant.types';

interface Category {
  label: RestaurantCategory;
  icon: React.ReactNode;
}

const categories: Category[] = [
  {
    label: '일식당',
    icon: <FastFood />,
  },
  {
    label: '한식',
    icon: <FastFood />,
  },
  {
    label: '초밥, 롤',
    icon: <FastFood />,
  },
  {
    label: '생선회',
    icon: <FastFood />,
  },
  {
    label: '양식',
    icon: <FastFood />,
  },
  {
    label: '육류, 고기요리',
    icon: <FastFood />,
  },
  {
    label: '이자카야',
    icon: <FastFood />,
  },
  {
    label: '돼지고기구이',
    icon: <FastFood />,
  },
  {
    label: '요리주점',
    icon: <FastFood />,
  },
  {
    label: '와인',
    icon: <FastFood />,
  },
];

const meta: Meta<typeof CategoryNavbar> = {
  title: 'Selector/CategoryNavbar',
  component: CategoryNavbar,
};

export default meta;

type Story = StoryObj<typeof CategoryNavbar>;

export const Default: Story = {
  args: {
    categories,
  },
};
