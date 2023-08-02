import { useEffect, useRef } from 'react';
import styled from 'styled-components';
import { shallow } from 'zustand/shallow';
import { FONT_SIZE } from '~/styles/common';
import useBottomSheetStatus from '~/hooks/store/useBottomSheetStatus';
import useTouchMoveDirection from '~/hooks/useTouchMoveDirection';

interface BottomSheetHeaderProps {
  children: string;
}

function BottomSheetHeader({ children }: BottomSheetHeaderProps) {
  const { open, close } = useBottomSheetStatus(
    state => ({
      open: state.open,
      close: state.close,
    }),
    shallow,
  );

  const ref = useRef<HTMLDivElement>();
  const { movingDirection } = useTouchMoveDirection(ref);

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
