import styled, { css } from 'styled-components';
import { useRef } from 'react';
import { shallow } from 'zustand/shallow';
import BottomSheetHeader from './BottomSheetHeader';
import { BORDER_RADIUS } from '~/styles/common';
import useOnClickOutside from '~/hooks/useOnClickOutside';
import useBottomSheetStatus from '~/hooks/store/useBottomSheetStatus';

interface BottomSheetProps {
  children: React.ReactNode;
  title: string;
  isLoading: boolean;
}

function BottomSheet({ children, title = '', isLoading }: BottomSheetProps) {
  const { isOpen, close } = useBottomSheetStatus(state => ({ isOpen: state.isOpen, close: state.close }), shallow);
  const ref = useRef<HTMLDivElement>();

  useOnClickOutside(ref, close);

  return (
    <Wrapper isOpen={isOpen} ref={ref}>
      <BottomSheetHeader isLoading={isLoading}>{title}</BottomSheetHeader>
      <StyledContent>{children}</StyledContent>
    </Wrapper>
  );
}

export default BottomSheet;

const Wrapper = styled.div<{ isOpen: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;

  position: fixed;
  bottom: 0;
  z-index: 10;

  width: 100%;
  height: 74px;

  background-color: var(--white);

  border-top-left-radius: ${BORDER_RADIUS.lg};
  border-top-right-radius: ${BORDER_RADIUS.lg};

  transition: transform 0.8s ease-in-out;

  ${({ isOpen }) =>
    isOpen &&
    css`
      position: sticky;

      transform: translateY(-36vh);
    `}

  &::before {
    display: block;

    position: absolute;
    top: 8px;
    left: 50%;

    width: 40px;
    height: 4px;

    border-radius: ${BORDER_RADIUS.sm};
    background-color: var(--gray-2);

    transform: translateX(-20px);
    content: '';
  }
`;

const StyledContent = styled.div`
  width: 100%;

  background-color: var(--white);
`;
