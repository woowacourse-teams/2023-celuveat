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
      {
        imgUrl: 'https://t1.daumcdn.net/cfile/tistory/224CEE3C577E3C7503',
        imgFile: 'asdf' as unknown as Blob,
      },
      {
        imgUrl: 'https://t1.daumcdn.net/cfile/tistory/224CEE3C577E3C7503',
        imgFile: 'asdf' as unknown as Blob,
      },
    ],
  },
};
