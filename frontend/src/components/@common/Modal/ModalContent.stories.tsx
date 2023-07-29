import type { Meta, StoryObj } from '@storybook/react';
import ModalContent from './ModalContent';

const meta: Meta<typeof ModalContent> = {
  title: 'ModalContent',
  component: ModalContent,
};

export default meta;

type Story = StoryObj<typeof ModalContent>;

export const LoginModal: Story = {
  args: {
    isShow: true,
    title: '로그인 또는 회원가입',
    handleModalShow: () => {},
    children: '모달 내용',
  },
};
