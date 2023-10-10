import { create } from 'zustand';
import { persist, createJSONStorage } from 'zustand/middleware';

interface PathState {
  path: string;
}

interface PathAction {
  setPath: (path: string) => void;
}

const usePathNameState = create(
  persist<PathState & PathAction>(
    set => ({
      path: '/',
      setPath: (path: string) => set({ path }),
    }),
    {
      name: 'CELUVEAT-PATHNAME-STORAGE',
      storage: createJSONStorage(() => sessionStorage),
    },
  ),
);

export default usePathNameState;
