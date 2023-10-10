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
        imgUrl: '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
        imgFile: 'asdf' as unknown as Blob,
      },
      {
        imgUrl: '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
        imgFile: 'asdf' as unknown as Blob,
      },
    ],
  },
};
