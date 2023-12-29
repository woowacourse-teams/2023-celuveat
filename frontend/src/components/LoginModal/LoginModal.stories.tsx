import type { Meta, StoryObj } from '@storybook/react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginModal from './LoginModal';

const meta: Meta<typeof LoginModal> = {
  title: 'Modal/LoginModal',
  component: LoginModal,
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

type Story = StoryObj<typeof LoginModal>;

export const Default: Story = {};
