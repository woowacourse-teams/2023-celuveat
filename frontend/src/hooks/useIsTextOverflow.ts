import { useEffect, useRef, useState } from 'react';

const checkIsTextOverflow = <T extends HTMLElement>(target: T) => target.clientHeight < target.scrollHeight;

const useIsTextOverflow = () => {
  const ref = useRef<HTMLDivElement>(null);
  const [isTextOverflow, setIsTextOverflow] = useState(false);

  const onResize = () => {
    if (ref.current && checkIsTextOverflow(ref.current)) {
      setIsTextOverflow(true);
      return;
    }

    setIsTextOverflow(false);
  };

  useEffect(() => {
    onResize();

    window.addEventListener('resize', onResize);

    return () => {
      window.removeEventListener('resize', onResize);
    };
  }, []);

  return { ref, isTextOverflow };
};

export default useIsTextOverflow;
