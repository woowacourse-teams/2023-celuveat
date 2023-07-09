import type { Meta, StoryObj } from '@storybook/react';
import SearchbarDropDown from './SearchbarDropDown';

const meta: Meta<typeof SearchbarDropDown> = {
  title: 'SearchbarDropDown',
  component: SearchbarDropDown,
};

export default meta;

type Story = StoryObj<typeof SearchbarDropDown>;

export const Default: Story = {
  args: {
    options: [
      {
        key: 1,
        value: '나는 푸만능이다.',
      },
      {
        key: 2,
        value: '나는 푸만능이다.',
      },
      {
        key: 3,
        value: '나는 푸만능이다.',
      },
      {
        key: 4,
        value: '나는 푸만능이다.',
      },
      {
        key: 5,
        value: '나는 푸만능이다.',
      },
    ],
  },
};
