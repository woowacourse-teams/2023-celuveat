import type { Meta, StoryObj } from '@storybook/react';
import CategoryNavbar from './CategoryNavbar';
import Izakaya from '~/assets/icons/restaurantCategory/izakaya.svg';
import { RestaurantCategory } from '~/@types/restaurant.types';

interface Category {
  label: RestaurantCategory;
  icon: React.ReactNode;
}

const categories: Category[] = [
  {
    label: '일식당',
    icon: <Izakaya />,
  },
  {
    label: '한식',
    icon: <Izakaya />,
  },
  {
    label: '초밥,롤',
    icon: <Izakaya />,
  },
  {
    label: '생선회',
    icon: <Izakaya />,
  },
  {
    label: '양식',
    icon: <Izakaya />,
  },
  {
    label: '육류,고기요리',
    icon: <Izakaya />,
  },
  {
    label: '이자카야',
    icon: <Izakaya />,
  },
  {
    label: '돼지고기구이',
    icon: <Izakaya />,
  },
  {
    label: '요리주점',
    icon: <Izakaya />,
  },
  {
    label: '와인',
    icon: <Izakaya />,
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
