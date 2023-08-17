import type { Meta, StoryObj } from '@storybook/react';
import LoginPageUI from '~/components/LoginPageUI';

const meta: Meta<typeof LoginPageUI> = {
  title: 'LoginPageUI',
  component: LoginPageUI,
};

export default meta;

type Story = StoryObj<typeof LoginPageUI>;

export const Default: Story = {
  args: {
    children: <div>hi</div>,
  },
};
