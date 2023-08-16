import type { Meta, StoryObj } from '@storybook/react';
import WithdrawalModalContent from '~/components/WithdrawalModalContent/';

const meta: Meta<typeof WithdrawalModalContent> = {
  title: 'WithdrawalModalContent',
  component: WithdrawalModalContent,
};

export default meta;

type Story = StoryObj<typeof WithdrawalModalContent>;

export const Default: Story = {};
