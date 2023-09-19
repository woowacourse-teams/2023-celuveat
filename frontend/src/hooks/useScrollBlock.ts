import { useEffect } from 'react';

type Ref = React.MutableRefObject<Element>;

const useScrollBlock = (refs: Ref[] | Ref) => {
  const blockScroll = (ref: React.MutableRefObject<Element>) => {
    ref.current.addEventListener('touchmove', e => e.preventDefault(), { passive: false });
  };

  useEffect(() => {
    if (Array.isArray(refs)) {
      refs.forEach(ref => blockScroll(ref));
    } else {
      blockScroll(refs);
    }
  }, [refs]);
};

export default useScrollBlock;
