import type { Meta, StoryObj } from '@storybook/react';
import All from '~/assets/icons/category/all.svg';
import NavItem from './NavItem';

const meta: Meta<typeof NavItem> = {
  title: 'NavItem',
  component: NavItem,
};

export default meta;

type Story = StoryObj<typeof NavItem>;

export const Default: Story = {
  args: {
    label: '전체',
    icon: <All />,
    isShow: true,
  },
};
