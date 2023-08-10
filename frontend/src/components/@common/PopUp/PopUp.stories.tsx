import type { Meta, StoryObj } from '@storybook/react';
import PopUp from './PopUp';

const meta: Meta<typeof PopUp> = {
  title: 'PopUp',
  component: PopUp,
};

export default meta;

type Story = StoryObj<typeof PopUp>;

export const SuccessPopUp: Story = {
  args: {
    text: '위시리스트에 저장됨',
    isSuccess: true,
    imgUrl: 'bGl3b29f7Iqk7Iuc7JWE7Jik66eI7Lig.jpeg',
  },
};

export const FailurePopUp: Story = {
  args: {
    text: '위시리스트에서 삭제됨.',
    isSuccess: false,
    imgUrl: 'bGl3b29f7Iqk7Iuc7JWE7Jik66eI7Lig.jpeg',
  },
};
