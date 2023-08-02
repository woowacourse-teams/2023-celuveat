import { useEffect, useRef } from 'react';
import styled, { css } from 'styled-components';
import { shallow } from 'zustand/shallow';
import { BORDER_RADIUS, FONT_SIZE, paintSkeleton } from '~/styles/common';
import useBottomSheetStatus from '~/hooks/store/useBottomSheetStatus';
import useTouchMoveDirection from '~/hooks/useTouchMoveDirection';

interface BottomSheetHeaderProps {
  isLoading: boolean;
  children: string;
}

function BottomSheetHeader({ children, isLoading }: BottomSheetHeaderProps) {
  const { open, close } = useBottomSheetStatus(state => ({ open: state.open, close: state.close }), shallow);
  const ref = useRef<HTMLDivElement>();
  const { movingDirection } = useTouchMoveDirection(ref);

  useEffect(() => {
    if (movingDirection.Y === 'up') open();
    if (movingDirection.Y === 'down') close();
  }, [movingDirection]);

  return (
    <Wrapper onClick={open} ref={ref}>
      <StyledBottomSheetTitle isLoading={isLoading}>{!isLoading && children}</StyledBottomSheetTitle>
    </Wrapper>
  );
}

export default BottomSheetHeader;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;
  min-width: 120px;
  min-height: 74px;

  font-size: ${FONT_SIZE.md};
  font-weight: 900;
  text-align: center;
  padding-top: 3.2rem;
  padding-bottom: 2.4rem;
`;

const StyledBottomSheetTitle = styled.div<{ isLoading: boolean }>`
  ${({ isLoading }) =>
    isLoading &&
    css`
      ${paintSkeleton}
      width: 200px;
      height: 16px;
      align-self: center;

      border-radius: ${BORDER_RADIUS.xs};
    `}
`;
