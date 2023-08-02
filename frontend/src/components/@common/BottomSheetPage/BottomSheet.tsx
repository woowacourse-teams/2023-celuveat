import styled, { css } from 'styled-components';
import { useRef, useState } from 'react';
import BottomSheetHeader from './BottomSheetHeader';
import { BORDER_RADIUS } from '~/styles/common';
import useOnClickOutside from '~/hooks/useOnClickOutside';

interface BottomSheetProps {
  children: React.ReactNode;
}

function BottomSheet({ children }: BottomSheetProps) {
  const [isOpen, setIsOpen] = useState(false);
  const ref = useRef<HTMLDivElement>();

  const close = () => setIsOpen(false);

  useOnClickOutside(ref, close);

  return (
    <Wrapper isOpen={isOpen} ref={ref}>
      <BottomSheetHeader setIsOpen={setIsOpen}>지도에 표시된 음식점 100개</BottomSheetHeader>
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
      position: unset;

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
