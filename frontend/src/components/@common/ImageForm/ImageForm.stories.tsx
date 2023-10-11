import type { Meta, StoryObj } from '@storybook/react';
import ImageForm from './ImageForm';

const meta: Meta<typeof ImageForm> = {
  title: 'ImageForm',
  component: ImageForm,
};

export default meta;

type Story = StoryObj<typeof ImageForm>;

export const Default: Story = {
  args: {},
};
