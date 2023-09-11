import { useEffect } from 'react';

const useScrollBlock = (refs: React.MutableRefObject<Element>[]) => {
  const blockScroll = (ref: React.MutableRefObject<Element>) => {
    ref.current.addEventListener('touchmove', e => e.preventDefault(), { passive: false });
  };

  useEffect(() => {
    refs.forEach(ref => blockScroll(ref));
  }, []);
};

export default useScrollBlock;
