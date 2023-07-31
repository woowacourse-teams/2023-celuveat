import type { Meta, StoryObj } from '@storybook/react';
import PageNationBar from './PageNationBar';

const meta: Meta<typeof PageNationBar> = {
  title: 'PageNationBar',
  component: PageNationBar,
};

export default meta;

type Story = StoryObj<typeof PageNationBar>;

export const Default: Story = {
  args: {
    totalPage: 18,
    currentPage: 15,
    clickPageButton: () => {},
  },
};
