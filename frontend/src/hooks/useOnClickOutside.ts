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
    function onTouchHandler(event: TouchEvent) {
      if (!ref?.current || ref?.current.contains(event?.target as Node)) {
        return;
      }
      handler(event);
    }
    window.addEventListener('click', onClickHandler);
    window.addEventListener('touchstart', onTouchHandler);
    return () => {
      window.removeEventListener('click', onClickHandler);
      window.removeEventListener('touchstart', onTouchHandler);
    };
  }, [ref, handler]);
}
