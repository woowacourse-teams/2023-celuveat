import { useCallback, useState } from 'react';

function useBooleanState(defaultValue?: boolean) {
  const [value, setValue] = useState(!!defaultValue);

  const setTrue = useCallback(() => setValue(true), []);
  const setFalse = useCallback(() => setValue(false), []);
  const toggle = useCallback(() => setValue(x => !x), []);

  return { value, setTrue, setFalse, toggle, setValue };
}

export default useBooleanState;
