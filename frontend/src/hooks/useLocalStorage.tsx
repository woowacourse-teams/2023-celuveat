import { useCallback, useState } from 'react';
import BrowserStorage from '~/utils/LocalStorage';

function useLocalStorage() {
  const [value, setValue] = useState(BrowserStorage.get());

  const changeStorageValue = useCallback((v: string) => {
    BrowserStorage.set(v);
    setValue(v);
  }, []);

  const clearStorage = useCallback(() => {
    BrowserStorage.clear();
    setValue('');
  }, []);

  return { value, changeStorageValue, clearStorage };
}

export default useLocalStorage;
