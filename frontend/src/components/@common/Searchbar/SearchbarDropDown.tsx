import { ChangeEventHandler, useCallback, useRef } from 'react';
import { Option } from '~/@types/utils.types';

interface SeachbarDropDownProps {
  options: Option[];
  setOptions: ChangeEventHandler<HTMLInputElement>;
}

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

function SearchbarDropDown({ options, setOptions }: SeachbarDropDownProps) {
  const { inputRef, inputDom, selectorRef } = useSearchBarRef();

  return (
    <div>
      <input type="text" onChange={setOptions} ref={inputRef} />
      <ul ref={selectorRef}>
        {options.map(option => (
          <button
            type="button"
            key={option.key}
            onClick={() => {
              inputDom.value = option.value;
            }}
          >
            {option.value}
          </button>
        ))}
      </ul>
    </div>
  );
}

export default SearchbarDropDown;
