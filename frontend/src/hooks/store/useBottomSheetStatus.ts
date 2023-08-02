import { create } from 'zustand';

interface BottomSheetState {
  isOpen: boolean;
  open: VoidFunction;
  close: VoidFunction;
}

const useBottomSheetStatus = create<BottomSheetState>(set => ({
  isOpen: false,
  open: () => set(() => ({ isOpen: true })),
  close: () => set(() => ({ isOpen: false })),
}));

export default useBottomSheetStatus;
