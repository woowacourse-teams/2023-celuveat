import type { Meta, StoryObj } from '@storybook/react';
import InfoDropDown from './InfoDropDown';

const meta: Meta<typeof InfoDropDown> = {
  title: 'Selector/InfoDropDown',
  component: InfoDropDown,
};

export default meta;

const options = [
  {
    id: 1,
    value: '로그인',
  },
  {
    id: 2,
    value: '회원가입',
  },
  {
    id: 3,
    value: '기타',
  },
  {
    id: 4,
    value: '등 등',
  },
];

type Story = StoryObj<typeof InfoDropDown>;

export const Default: Story = {
  args: {
    options,
  },
};
