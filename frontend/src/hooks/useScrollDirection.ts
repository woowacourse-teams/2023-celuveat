import { useEffect, useState } from 'react';

function useScrollDirection() {
  const [scrollDirection, setScrollDirection] = useState({ x: 'none', y: 'none' });
  const [prevScrollX, setPrevScrollX] = useState(window.scrollX);
  const [prevScrollY, setPrevScrollY] = useState(window.scrollY);

  useEffect(() => {
    const handleScroll = () => {
      const currentScrollX = window.scrollX;
      const currentScrollY = window.scrollY;

      let xDirection = 'none';
      let yDirection = 'none';

      if (currentScrollX > prevScrollX) {
        xDirection = 'right';
      } else if (currentScrollX < prevScrollX) {
        xDirection = 'left';
      }

      if (currentScrollY > prevScrollY) {
        yDirection = 'down';
      } else if (currentScrollY < prevScrollY) {
        yDirection = 'up';
      }

      setScrollDirection({ x: xDirection, y: yDirection });
      setPrevScrollX(currentScrollX);
      setPrevScrollY(currentScrollY);
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, [prevScrollX, prevScrollY]);

  return scrollDirection;
}

export default useScrollDirection;
