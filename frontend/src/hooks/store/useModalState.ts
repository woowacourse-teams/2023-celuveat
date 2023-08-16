import { create } from 'zustand';

interface ModalState {
  isModalOpen: boolean;
  targetId: number;
}

interface ModalAction {
  close: VoidFunction;
  open: VoidFunction;
  setId: (id: number) => void;
}

const useModalState = create<ModalState & ModalAction>(set => ({
  isModalOpen: false,
  targetId: 0,

  close: () => set({ isModalOpen: false }),
  open: () => set({ isModalOpen: true }),
  setId: (id: number) => set({ targetId: id }),
}));

export default useModalState;
