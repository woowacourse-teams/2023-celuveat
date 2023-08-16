import { create } from 'zustand';

interface ToastState {
  isOpen: boolean;
  text: string;
  imgUrl: string;
  isSuccess: boolean;
}

interface ToastAction {
  close: VoidFunction;

  onSuccess: (text: string, imgUrl: string) => void;
  onFailure: (text: string) => void;
}

const useToastState = create<ToastState & ToastAction>(set => ({
  isOpen: false,
  text: '',
  isSuccess: false,
  imgUrl: '',

  close: () => set({ isOpen: false }),
  onSuccess: (text: string, imgUrl: string) => set({ isSuccess: true, text, isOpen: true, imgUrl }),
  onFailure: (text: string) => set({ isSuccess: false, text, isOpen: true, imgUrl: '' }),
}));

export default useToastState;
