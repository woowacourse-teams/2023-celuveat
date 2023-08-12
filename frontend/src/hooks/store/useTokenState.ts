import { create } from 'zustand';
import { persist, createJSONStorage } from 'zustand/middleware';
import { Oauth } from '~/@types/oauth.types';

type TokenState = {
  token: string;
  oauth: Oauth | '';
  updateToken: (token: string) => void;
  updateOauth: (oauth: Oauth) => void;
  clearToken: () => void;
};

const useTokenState = create<TokenState>()(
  persist(
    set => ({
      token: '',
      oauth: '',
      updateToken: token =>
        set(() => ({
          token,
        })),
      updateOauth: oauth =>
        set(() => ({
          oauth,
        })),
      clearToken: () => set({ token: '', oauth: '' }),
    }),
    {
      name: 'CELUVEAT-STORAGE', // name of the item in the storage (must be unique)
      storage: createJSONStorage(() => localStorage), // (optional) by default, 'localStorage' is used
    },
  ),
);

export default useTokenState;
