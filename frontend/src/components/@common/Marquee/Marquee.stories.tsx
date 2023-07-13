import type { Meta, StoryObj } from '@storybook/react';
import Tags from '~/components/@common/Tags';
import Marquee from './Marquee';

const meta: Meta<typeof Marquee> = {
  title: 'Marquee',
  component: Marquee,
};

export default meta;

type Story = StoryObj<typeof Marquee>;

export const Default: Story = {
  args: {
    width: 781,
    children: (
      <Tags
        texts={[
          '성시경의 먹을텐데',
          '쯔양',
          '떵개떵',
          '상해기',
          '성시경의 먹을텐데',
          '쯔양',
          '떵개떵',
          '상해기',
          '성시경의 먹을텐데',
          '쯔양',
          '떵개떵',
          '상해기',
          '성시경의 먹을텐데',
          '쯔양',
          '떵개떵',
          '상해기',
          '성시경의 먹을텐데',
          '쯔양',
          '떵개떵',
          '상해기',
          '성시경의 먹을텐데',
          '쯔양',
          '떵개떵',
          '상해기',
          '성시경의 먹을텐데',
          '쯔양',
        ]}
      />
    ),
  },
};
