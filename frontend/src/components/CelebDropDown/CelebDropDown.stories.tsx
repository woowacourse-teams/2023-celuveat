import type { Meta, StoryObj } from '@storybook/react';
import CelebDropDown from './CelebDropDown';

const meta: Meta<typeof CelebDropDown> = {
  title: 'Selector/CelebDropDown',
  component: CelebDropDown,
};

export default meta;

type Story = StoryObj<typeof CelebDropDown>;

export const Default: Story = {
  args: {
    options: [
      {
        value: 1,
        label: '히밥',
      },
      {
        value: 2,
        label: '뚱시경',
      },
      {
        value: 3,
        label: '뚱종원',
      },
      {
        value: 4,
        label: '99대장 나선욱',
      },
      {
        value: 5,
        label: '감사합니두~',
      },
    ],
    isOpen: true,
  },
};
