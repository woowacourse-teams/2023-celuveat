import type { Meta, StoryObj } from '@storybook/react';
import type { CelebsSearchBarOptionType } from '~/@types/celebs.types';
import SearchBarDropDown from './SearchBarDropDown';

const defaultOptions: CelebsSearchBarOptionType[] = [
  {
    id: 1,
    name: '성시경',
    profileImageUrl: 'https://avatars.githubusercontent.com/u/51052049?v=4',
    youtubeChannelName: '성시경의 먹을텐데',
  },
  {
    id: 2,
    name: '뚱시경',
    profileImageUrl: 'https://avatars.githubusercontent.com/u/51052049?v=4',
    youtubeChannelName: '뚱시경의 또 먹을텐데',
  },
  {
    id: 3,
    name: '쯔양',
    profileImageUrl: 'https://avatars.githubusercontent.com/u/51052049?v=4',
    youtubeChannelName: '쯔양의 먹을텐데',
  },
  {
    id: 4,
    name: '히밥',
    profileImageUrl: 'https://avatars.githubusercontent.com/u/51052049?v=4',
    youtubeChannelName: '히밥의 먹을텐데',
  },
];

const meta: Meta<typeof SearchBarDropDown> = {
  title: 'SearchBarDropDown',
  component: SearchBarDropDown,
};

export default meta;

type Story = StoryObj<typeof SearchBarDropDown>;

export const Default: Story = {
  args: {
    options: defaultOptions,
  },
};
