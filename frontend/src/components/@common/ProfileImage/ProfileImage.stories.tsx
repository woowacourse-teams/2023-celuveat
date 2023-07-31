import type { Meta, StoryObj } from '@storybook/react';
import ProfileImage from './ProfileImage';

const meta: Meta<typeof ProfileImage> = {
  title: 'ProfileImage',
  component: ProfileImage,
};

export default meta;

type Story = StoryObj<typeof ProfileImage>;

export const Default: Story = {
  args: {
    name: '누군가',
    imageUrl: 'https://avatars.githubusercontent.com/u/51052049?v=4',
    size: '64px',
  },
};
