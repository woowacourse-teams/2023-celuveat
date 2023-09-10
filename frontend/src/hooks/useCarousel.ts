/* eslint-disable no-param-reassign */
import { useEffect, useRef, useState } from 'react';

type MovingDirection = 'none' | 'left' | 'right';

interface Coordinate {
  X: number;
  Y: number;
}

const useCarousel = (carouselRef: React.MutableRefObject<HTMLDivElement>, imagesLength: number) => {
  const startPosition = useRef<Coordinate>({ X: 0, Y: 0 });
  const isMoving = useRef<boolean>(false);
  const movingDistance = useRef<number>(0);
  const movingDirection = useRef<MovingDirection>('none');
  const [currentIndex, setCurrentIndex] = useState<number>(0);

  const handleTouchStart = (e: TouchEvent) => {
    if (imagesLength === 1) return;

    e.stopPropagation();

    startPosition.current.X = e.touches[0].clientX;
    startPosition.current.Y = e.touches[0].clientY;
    isMoving.current = true;
  };

  const handleTouchMove = (e: TouchEvent) => {
    if (imagesLength === 1) return;

    e.stopPropagation();

    const movement = e.touches[0].clientX - startPosition.current.X;
    movingDistance.current = Math.round(Math.sqrt(movement ** 2));

    carouselRef.current.style.transform = `translateX(${movement}px)`;
    if (movement > 0) movingDirection.current = 'right';
    if (movement < 0) movingDirection.current = 'left';
  };

  const handleTouchEnd = (e: TouchEvent) => {
    if (imagesLength === 1) return;

    e.stopPropagation();

    const SwipeBreakpoint = (e.target as HTMLImageElement).width * 0.3;
    const isSwipeConditionFulfilled = movingDistance.current >= SwipeBreakpoint;

    if (isSwipeConditionFulfilled && movingDirection.current === 'left')
      setCurrentIndex(prevIndex => (imagesLength === prevIndex + 1 ? 0 : prevIndex + 1));
    if (isSwipeConditionFulfilled && movingDirection.current === 'right')
      setCurrentIndex(prevIndex => (prevIndex === 0 ? imagesLength - 1 : prevIndex - 1));

    isMoving.current = false;
  };

  useEffect(() => {
    (() => {
      carouselRef.current.addEventListener('touchstart', handleTouchStart);
      carouselRef.current.addEventListener('touchmove', handleTouchMove);
      carouselRef.current.addEventListener('touchend', handleTouchEnd);
    })();

    return () => {
      carouselRef.current.removeEventListener('touchstart', handleTouchStart);
      carouselRef.current.removeEventListener('touchmove', handleTouchMove);
      carouselRef.current.removeEventListener('touchend', handleTouchEnd);
    };
  }, [carouselRef]);

  const goToPrevious: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.stopPropagation();
    setCurrentIndex(prevIndex => prevIndex - 1);
  };
  const goToNext: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.stopPropagation();
    setCurrentIndex(prevIndex => prevIndex + 1);
  };

  return { currentIndex, goToPrevious, goToNext, isMoving: isMoving.current };
};

export default useCarousel;
