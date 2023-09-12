import { create } from 'zustand';

interface BaseURLState {
  baseURL: string;
}

interface BaseURLAction {
  setMSW: VoidFunction;
  setOrigin: VoidFunction;
}

const { BASE_URL } = process.env;

const useBaseURLState = create<BaseURLState & BaseURLAction>(set => ({
  baseURL: BASE_URL,

  setMSW: () => set({ baseURL: '/' }),
  setOrigin: () => set({ baseURL: BASE_URL }),
}));

export default useBaseURLState;
