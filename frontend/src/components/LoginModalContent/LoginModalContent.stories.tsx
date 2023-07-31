import type { Meta, StoryObj } from '@storybook/react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginModalContent from './LoginModalContent';

const meta: Meta<typeof LoginModalContent> = {
  title: 'Modal/LoginModalContent',
  component: LoginModalContent,
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

type Story = StoryObj<typeof LoginModalContent>;

export const Default: Story = {
  args: {},
};
