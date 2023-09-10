import { useEffect, useRef, useState } from 'react';

interface UseIntersectionObserverProps {
  ref: Element;
  onVisible: VoidFunction;
  onEscape: VoidFunction;
  threshold: number;
}

const useIntersectionObserver = ({ ref, onVisible, onEscape, threshold = 1.0 }: UseIntersectionObserverProps) => {
  const targetRef = useRef(ref);
  const [isVisible, setIsVisible] = useState(true);

  useEffect(() => {
    const observer = new IntersectionObserver(
      entries => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            onVisible();
            setIsVisible(true);
          } else {
            onEscape();
            setIsVisible(false);
          }
        });
      },
      { threshold },
    );

    if (targetRef.current) {
      observer.observe(targetRef.current);
    }

    return () => {
      if (targetRef.current) {
        observer.unobserve(targetRef.current);
      }
    };
  }, []);

  return { isVisible };
};

export default useIntersectionObserver;
