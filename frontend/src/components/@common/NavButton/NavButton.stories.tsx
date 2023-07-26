import type { Meta, StoryObj } from '@storybook/react';
import NavButton from './NavButton';
import FastFoodIcon from '~/assets/icons/fastFood.svg';

const meta: Meta<typeof NavButton> = {
  title: 'NavButton',
  component: NavButton,
};

export default meta;

type Story = StoryObj<typeof NavButton>;

export const Default: Story = {
  args: {
    label: '캠핑장',
    icon: <FastFoodIcon />,
    isShow: true,
  },
};
