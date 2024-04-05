import { MouseEvent, useCallback, useState } from 'react';

interface UseOnClickBlockProps {
  callback: (e?: MouseEvent) => void;
}

const useOnClickBlock = ({ callback }: UseOnClickBlockProps) => {
  const [isDragging, setIsDragging] = useState<boolean>(false);

  const handleMouseDown = useCallback(() => {
    setIsDragging(false);
  }, []);

  const handleMouseMove = useCallback(() => {
    setIsDragging(true);
  }, []);

  const handleClick = (e: MouseEvent) => {
    if (isDragging) return;
    callback(e);
  };

  return { onMouseDown: handleMouseDown, onMouseMove: handleMouseMove, onClick: handleClick };
};

export default useOnClickBlock;
