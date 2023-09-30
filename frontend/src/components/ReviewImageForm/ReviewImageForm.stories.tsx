import type { Meta, StoryObj } from '@storybook/react';
import ReviewImageForm from './ReviewImageForm';

const meta: Meta<typeof ReviewImageForm> = {
  title: 'ReviewImageForm',
  component: ReviewImageForm,
};

export default meta;

type Story = StoryObj<typeof ReviewImageForm>;

export const Default: Story = {
  args: {
    images: [
      'https://recipe1.ezmember.co.kr/cache/rpt/2020/07/19/5ae89a0fb5824ae08fcf0e17cfabbfe7.jpg',
      'https://t1.daumcdn.net/cfile/tistory/224CEE3C577E3C7503',
    ],
  },
};
