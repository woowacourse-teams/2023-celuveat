import { useEffect, RefObject } from 'react';

export default function useOnClickOutside<T extends HTMLElement = HTMLElement>(
  ref: RefObject<T>,
  handler: (event?: Event | MouseEvent) => void,
) {
  useEffect(() => {
    function onClickHandler(event: Event | MouseEvent) {
      if (!ref?.current || ref?.current.contains(event?.target as Node)) {
        return;
      }
      handler(event);
    }
    window.addEventListener('click', onClickHandler);
    return () => {
      window.removeEventListener('click', onClickHandler);
    };
  }, [ref, handler]);
}
