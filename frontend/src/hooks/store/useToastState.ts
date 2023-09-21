import { create } from 'zustand';

interface ImageState {
  url: string;
  alt: string;
}
interface ToastState {
  isOpen: boolean;
  text: string;
  image: ImageState | null;
  isSuccess: boolean;
}

interface ToastAction {
  close: VoidFunction;
  open: VoidFunction;
  onSuccess: (text: string, image?: ImageState) => void;
  onFailure: (text: string) => void;
}

const useToastState = create<ToastState & ToastAction>(set => ({
  text: '',
  image: null,
  isOpen: false,
  isSuccess: false,

  open: () => set({ isOpen: true }),
  close: () => set({ isOpen: false }),
  onSuccess: (text: string, image: ImageState = null) => set({ isSuccess: true, text, isOpen: true, image }),
  onFailure: (text: string) => set({ isSuccess: false, text, isOpen: true, image: null }),
}));

export default useToastState;
