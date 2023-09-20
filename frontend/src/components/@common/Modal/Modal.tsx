import { cloneElement, useRef } from 'react';
import styled, { css } from 'styled-components';
import useMediaQuery from '~/hooks/useMediaQuery';
import Exit from '~/assets/icons/exit.svg';
import Portal from '~/components/@common/Portal';
import { hideScrollBar } from '~/styles/common';

interface ModalProps {
  title?: string;
  isOpen: boolean;
  close: VoidFunction;
  children: React.ReactElement;
}

function Modal({ children, close, isOpen, title }: ModalProps) {
  const modalContentRef = useRef<HTMLDivElement>(null);
  const { isMobile } = useMediaQuery();

  return (
    <Portal isOpen={isOpen} close={close}>
      <StyledModalContentWrapper isShow={isOpen} isMobile={isMobile}>
        <StyledModalOverlay onClick={close} />
        <StyledModalContent ref={modalContentRef} isShow={isOpen} isMobile={isMobile}>
          <StyledModalHeader>
            <StyledExitButton onClick={close} />
            {title && <StyledModalTitleText>{title}</StyledModalTitleText>}
          </StyledModalHeader>
          <StyledModalBody>{cloneElement(children, { modalContentRef })}</StyledModalBody>
        </StyledModalContent>
      </StyledModalContentWrapper>
    </Portal>
  );
}

export default Modal;

const StyledExitButton = styled(Exit)`
  cursor: pointer;
`;

const StyledModalContentWrapper = styled.div<{ isShow: boolean; isMobile: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;

  position: fixed;
  top: 0;
  left: 0;
  z-index: 999;

  width: 100%;
  height: 100%;

  opacity: 0;
  visibility: hidden;

  ${({ isMobile }) =>
    isMobile &&
    css`
      flex-direction: column;
      justify-content: flex-end;
    `}

  ${({ isShow }) =>
    isShow &&
    css`
      visibility: visible;

      opacity: 1;
      transition: opacity ease 0.25s;
    `}
`;

const StyledModalOverlay = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  z-index: 1;

  width: 100%;
  height: 100%;

  background-color: black;

  opacity: 0.2;
`;

const StyledModalContent = styled.div<{ isShow: boolean; isMobile: boolean }>`
  display: flex;
  flex-direction: column;

  position: relative;
  z-index: 10;

  width: 33%;
  min-width: 500px;
  max-width: 600px;
  min-height: 100px;
  max-height: 600px;

  padding: 2rem;

  border-radius: 5px;
  background: #fff;

  transition: transform ease 0.3s 0.1s;
  transform: translateY(80px);

  ${({ isMobile }) =>
    isMobile &&
    css`
      width: 100%;
      min-width: 100%;
      max-width: 100%;
    `}

  ${({ isShow }) =>
    isShow &&
    css`
      transform: translateY(0);
    `}
`;

const StyledModalHeader = styled.h5`
  display: flex;
  align-items: center;
  margin-bottom: 2.4rem;
`;

const StyledModalTitleText = styled.span`
  margin: 0 auto;
`;

const StyledModalBody = styled.div`
  ${hideScrollBar}
  overflow-y: auto;
`;
