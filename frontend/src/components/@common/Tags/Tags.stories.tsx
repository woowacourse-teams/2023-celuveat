import type { Meta, StoryObj } from '@storybook/react';
import Tags from './Tags';

const meta: Meta<typeof Tags> = {
  title: 'Tags',
  component: Tags,
};

export default meta;

type Story = StoryObj<typeof Tags>;

export const Default: Story = {
  args: { texts: ['성시경의 먹을텐데', '쯔양', '떵개떵', '상해기'] },
};
