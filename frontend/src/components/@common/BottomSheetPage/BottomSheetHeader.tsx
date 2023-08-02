import { useEffect, useRef } from 'react';
import styled from 'styled-components';
import useBottomSheet from './useBottomSheet';
import { FONT_SIZE } from '~/styles/common';

interface BottomSheetHeaderProps {
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
  children: string;
}

function BottomSheetHeader({ children, setIsOpen }: BottomSheetHeaderProps) {
  const ref = useRef<HTMLDivElement>();
  const { movingDirection } = useBottomSheet(ref, setIsOpen);

  const open = () => setIsOpen(true);
  const close = () => setIsOpen(false);

  useEffect(() => {
    if (movingDirection.Y === 'up') open();
    if (movingDirection.Y === 'down') close();
  }, [movingDirection]);

  return (
    <Wrapper onClick={open} ref={ref}>
      {children}
    </Wrapper>
  );
}

export default BottomSheetHeader;

const Wrapper = styled.div`
  width: 100%;
  min-height: 74px;

  font-size: ${FONT_SIZE.md};
  font-weight: 900;
  text-align: center;
  padding-top: 3.2rem;
  padding-bottom: 2.4rem;
`;
