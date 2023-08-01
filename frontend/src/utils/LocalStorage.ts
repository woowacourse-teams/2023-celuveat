const LOCAL_STORAGE_KEY = 'CELUVEAT_STORAGE_KEY';

const BrowserStorage = {
  get: () => {
    const item = localStorage.getItem(LOCAL_STORAGE_KEY);
    return item ?? '';
  },

  set: (value: string) => {
    localStorage.setItem(LOCAL_STORAGE_KEY, value);
  },

  clear: () => {
    localStorage.removeItem(LOCAL_STORAGE_KEY);
  },
};

export default BrowserStorage;
