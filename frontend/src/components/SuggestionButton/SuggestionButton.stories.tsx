import type { Meta, StoryObj } from '@storybook/react';
import SuggestionButton from './SuggestionButton';

const meta: Meta<typeof SuggestionButton> = {
  title: 'SuggestionButton',
  component: SuggestionButton,
};

export default meta;

type Story = StoryObj<typeof SuggestionButton>;

export const Default: Story = {};
