import { useCallback, useState } from 'react';

interface UseOnClickBlockProps {
  callback: () => void;
}

const useOnClickBlock = ({ callback }: UseOnClickBlockProps) => {
  const [isDragging, setIsDragging] = useState<boolean>(false);

  const handleMouseDown = useCallback(() => {
    setIsDragging(false);
  }, []);

  const handleMouseMove = useCallback(() => {
    setIsDragging(true);
  }, []);

  const handleClick = () => {
    if (isDragging) return;
    callback();
  };

  return { onMouseDown: handleMouseDown, onMouseMove: handleMouseMove, onClick: handleClick };
};

export default useOnClickBlock;
