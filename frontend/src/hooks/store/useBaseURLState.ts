import { create } from 'zustand';

interface BaseURLState {
  baseURL: string;
}

interface BaseURLAction {
  setMSW: VoidFunction;
  setDev: VoidFunction;
  setProd: VoidFunction;
}

const useBaseURLState = create<BaseURLState & BaseURLAction>(set => ({
  baseURL: process.env.BASE_URL_DEV,

  setMSW: () => set({ baseURL: '/' }),
  setDev: () => set({ baseURL: process.env.BASE_URL_DEV }),
  setProd: () => set({ baseURL: process.env.BASE_URL_PROD }),
}));

export default useBaseURLState;
