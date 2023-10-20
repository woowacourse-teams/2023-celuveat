import { useEffect, useRef, useState } from 'react';

const MOBILE_MEDIA_QUERY = '(max-width: 780px)';

const useMediaQuery = () => {
  const [matches, setMatches] = useState(() => window.matchMedia(MOBILE_MEDIA_QUERY).matches);
  const matchMediaRef = useRef<MediaQueryList | null>(null);

  useEffect(() => {
    const matchMedia = window.matchMedia(MOBILE_MEDIA_QUERY);
    matchMediaRef.current = matchMedia;
    function handleChange() {
      setMatches(window.matchMedia(MOBILE_MEDIA_QUERY).matches);
    }
    matchMediaRef.current.addEventListener('change', handleChange);

    return () => {
      matchMediaRef.current?.removeEventListener('change', handleChange);
    };
  }, []);

  return { isMobile: matches };
};

export default useMediaQuery;
