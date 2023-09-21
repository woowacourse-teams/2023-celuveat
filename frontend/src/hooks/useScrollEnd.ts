import { useEffect, useState } from 'react';

interface UseScrollEndProps {
  direction: 'X' | 'Y';
  threshold: number;
  onEnd?: VoidFunction;
}

const useScrollEnd = ({ direction, threshold = 0, onEnd }: UseScrollEndProps) => {
  const [isEnd, setIsEnd] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      if (direction === 'X') {
        const { scrollX, innerWidth } = window;
        const documentWidth = document.documentElement.scrollWidth;
        const isXEnd = scrollX + innerWidth + threshold >= documentWidth;

        setIsEnd(isXEnd);
        if (isXEnd && onEnd) onEnd();
      }

      if (direction === 'Y') {
        const { scrollY, innerHeight } = window;
        const documentHeight = document.documentElement.scrollHeight;
        const isYEnd = scrollY + innerHeight + threshold >= documentHeight;

        setIsEnd(isYEnd);
        if (isYEnd && onEnd) onEnd();
      }
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return { isEnd };
};

export default useScrollEnd;
