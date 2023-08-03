import { create } from 'zustand';
import { persist, createJSONStorage } from 'zustand/middleware';

type TokenState = {
  token: string;
  updateToken: (token: string) => void;
  clearToken: () => void;
};

const useTokenState = create<TokenState>()(
  persist(
    set => ({
      token: '',
      updateToken: token => set({ token }),
      clearToken: () => set({ token: '' }),
    }),
    {
      name: 'CELUVEAT-STORAGE', // name of the item in the storage (must be unique)
      storage: createJSONStorage(() => localStorage), // (optional) by default, 'localStorage' is used
    },
  ),
);

export default useTokenState;
