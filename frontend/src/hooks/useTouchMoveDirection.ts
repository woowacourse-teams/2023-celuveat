/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useRef, useEffect, MutableRefObject, useState } from 'react';

interface MovingDirection {
  X: 'none' | 'left' | 'right';
  Y: 'none' | 'up' | 'down';
}

interface Coordinate {
  X: number;
  Y: number;
}

interface BottomSheetMetrics {
  touchStart: {
    sheet: Coordinate;
    touch: Coordinate;
  };
  touchMove: {
    sheet: Coordinate;
    touch: Coordinate;
  };
  touchEnd: {
    sheet: Coordinate;
    touch: Coordinate;
  };
}

const initialMetrics: BottomSheetMetrics = {
  touchStart: {
    sheet: { X: 0, Y: 0 }, // touchstart에서 BottomSheet의 최상단 모서리의 Y값
    touch: { X: 0, Y: 0 }, // touchstart에서 터치 포인트의 Y값
  },
  touchMove: {
    sheet: { X: 0, Y: 0 },
    touch: { X: 0, Y: 0 },
  },
  touchEnd: {
    sheet: { X: 0, Y: 0 },
    touch: { X: 0, Y: 0 },
  },
};

function useTouchMoveDirection(sheetRef: React.MutableRefObject<HTMLDivElement>) {
  const [movingDirection, setMovingDirection] = useState<MovingDirection>({ X: 'none', Y: 'none' });
  const metrics = useRef<BottomSheetMetrics>(initialMetrics);

  useEffect(() => {
    const handleTouchStart = (e: TouchEvent) => {
      if (!e) return;

      e.stopPropagation();

      const { touchStart } = metrics.current;
      const { sheet, touch } = touchStart;

      sheet.X = sheetRef?.current?.getBoundingClientRect()?.x;
      sheet.Y = sheetRef?.current?.getBoundingClientRect()?.y;

      touch.X = e?.touches[0]?.clientX;
      touch.Y = e?.touches[0]?.clientY;
    };

    const handleTouchEnd = (e: TouchEvent) => {
      if (!e) return;

      e.stopPropagation();

      const { touchStart } = metrics.current;

      const { clientX, clientY } = e.changedTouches[0];
      const distanceX = touchStart.touch.X - clientX;
      const distanceY = touchStart.touch.Y - clientY;

      if (distanceY > 7.5) {
        setMovingDirection(prev => ({ ...prev, Y: 'up' }));
      } else if (distanceY < -7.5) {
        setMovingDirection(prev => ({ ...prev, Y: 'down' }));
      } else {
        setMovingDirection(prev => ({ ...prev, Y: 'none' }));
      }

      if (distanceX > 7.5) {
        setMovingDirection(prev => ({ ...prev, X: 'left' }));
      } else if (distanceX < -7.5) {
        setMovingDirection(prev => ({ ...prev, X: 'right' }));
      } else {
        setMovingDirection(prev => ({ ...prev, X: 'none' }));
      }

      metrics.current = initialMetrics;
    };

    sheetRef?.current?.addEventListener('touchstart', handleTouchStart);
    sheetRef?.current?.addEventListener('touchend', handleTouchEnd);

    return () => {
      sheetRef?.current?.removeEventListener('touchstart', handleTouchStart);
      sheetRef?.current?.removeEventListener('touchend', handleTouchEnd);
    };
  }, []);

  return { movingDirection, metrics };
}

export default useTouchMoveDirection;
