import type { Meta, StoryObj } from '@storybook/react';
import type { Option } from '~/@types/utils.types';
import SearchbarDropDown from './SearchbarDropDown';

const defaultOptions: Option[] = [
  {
    key: 1,
    value: '성시경의 먹을텐데',
  },
  {
    key: 2,
    value: '뚱시경의 또먹을텐데',
  },
  {
    key: 3,
    value: '찌디',
  },
  {
    key: 4,
    value: '푸만능의 먹고 싶다',
  },
];

const meta: Meta<typeof SearchbarDropDown> = {
  title: 'SearchbarDropDown',
  component: SearchbarDropDown,
};

export default meta;

type Story = StoryObj<typeof SearchbarDropDown>;

export const Default: Story = {
  args: {
    options: defaultOptions,
  },
};
