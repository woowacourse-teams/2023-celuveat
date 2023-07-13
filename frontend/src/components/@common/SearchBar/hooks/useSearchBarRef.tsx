import { useCallback, useRef } from 'react';

function useSearchBarRef() {
  const inputRef = useRef<HTMLInputElement>(null);
  const selectorRef = useRef<HTMLUListElement>(null);

  const callbackInputRef = useCallback((node: HTMLInputElement) => {
    const removeSelectorDom = () => {
      selectorRef.current.style.display = 'none';
    };

    const showSelectorDom = () => (event: MouseEvent) => {
      event.stopPropagation();
      selectorRef.current.style.display = 'block';
    };

    inputRef.current = node;

    inputRef.current.addEventListener('click', showSelectorDom());
    document.addEventListener('click', removeSelectorDom);

    return () => {
      document.removeEventListener('click', removeSelectorDom);
      if (inputRef.current && selectorRef.current) {
        inputRef.current.removeEventListener('click', showSelectorDom());
      }
    };
  }, []);

  return {
    inputRef: callbackInputRef,
    inputDom: inputRef.current,
    selectorRef,
  };
}

export default useSearchBarRef;
