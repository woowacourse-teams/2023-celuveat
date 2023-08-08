import { create } from 'zustand';

interface ToastState {
  isOpen: boolean;
  text: string;
  isSuccess: boolean;
}

interface ToastAction {
  close: VoidFunction;

  onSuccess: (text: string) => void;
  onFailure: (text: string) => void;
}

const useToastState = create<ToastState & ToastAction>(set => ({
  isOpen: false,
  text: '',
  isSuccess: false,

  close: () => set({ isOpen: false }),
  onSuccess: (text: string) => set(() => ({ isSuccess: true, text, isOpen: true })),
  onFailure: (text: string) => set({ isSuccess: false, text, isOpen: true }),
}));

export default useToastState;
