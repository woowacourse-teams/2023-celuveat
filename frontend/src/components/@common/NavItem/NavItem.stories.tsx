import type { Meta, StoryObj } from '@storybook/react';
import FastFoodIcon from '~/assets/icons/fastFood.svg';
import NavItem from '~/components/@common/NavItem';

const meta: Meta<typeof NavItem> = {
  title: 'NavItem',
  component: NavItem,
};

export default meta;

type Story = StoryObj<typeof NavItem>;

export const Default: Story = {
  args: {
    label: '캠핑장',
    icon: <FastFoodIcon />,
    isShow: true,
  },
};
