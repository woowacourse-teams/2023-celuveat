import { create } from 'zustand';

interface ToastState {
  isLiked: boolean;
  isOpen: boolean;
  text: string;
  isSuccess: boolean;
}

interface ToastAction {
  onSuccess: (text: string) => void;
  onFailure: (text: string) => void;
  close: VoidFunction;
}

const useToastState = create<ToastState & ToastAction>(set => ({
  isLiked: true,
  isOpen: false,
  text: '',
  isSuccess: false,

  onSuccess: (text: string) => set(state => ({ isLiked: !state.isLiked, isSuccess: true, text, isOpen: true })),
  onFailure: (text: string) => set({ isSuccess: false, text, isOpen: true }),
  close: () => set({ isOpen: false }),
}));

export default useToastState;
