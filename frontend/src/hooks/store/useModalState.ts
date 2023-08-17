import { create } from 'zustand';

interface ModalState {
  isModalOpen: boolean;
  targetId: number;
  content: string;
}

interface ModalAction {
  close: VoidFunction;
  open: VoidFunction;
  setId: (id: number) => void;
  setContent: (content: string) => void;
}

const useModalState = create<ModalState & ModalAction>(set => ({
  isModalOpen: false,
  targetId: 0,
  content: '',

  close: () => set({ isModalOpen: false }),
  open: () => set({ isModalOpen: true }),
  setId: (id: number) => set({ targetId: id }),
  setContent: (content: string) => set({ content }),
}));

export default useModalState;
