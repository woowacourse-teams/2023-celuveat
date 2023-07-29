import type { Meta, StoryObj } from '@storybook/react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginButton from './LoginButton';

const meta: Meta<typeof LoginButton> = {
  title: 'Oauth/LoginButton',
  component: LoginButton,
  decorators: [
    Story => (
      <BrowserRouter>
        <Routes>
          <Route path="/*" element={<Story />} />
        </Routes>
      </BrowserRouter>
    ),
  ],
};

export default meta;

type Story = StoryObj<typeof LoginButton>;

export const Google: Story = {
  args: { type: 'google' },
};

export const KaKao: Story = {
  args: { type: 'kakao' },
};

export const Naver: Story = {
  args: { type: 'naver' },
};
