import type { Meta, StoryObj } from '@storybook/react';
import Modal from './Modal';

const meta: Meta<typeof Modal> = {
  title: 'Modal',
  component: Modal,
};

export default meta;

type Story = StoryObj<typeof Modal>;

export const LoginModal: Story = {
  args: {
    title: '로그인 또는 회원가입',
    children: <span>모달 내용</span>,
    isOpen: true,
    close: () => {},
  },
};
