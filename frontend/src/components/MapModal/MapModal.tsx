import { keyframes, styled } from 'styled-components';
import { BORDER_RADIUS } from '~/styles/common';
import ExitButton from '../../assets/icons/exit.svg';

type ModalProps = {
  children: React.ReactNode;
  modalOpen: boolean;
  isVisible: boolean;
  onClickExit: () => void;
};

function MapModal({ children, modalOpen, onClickExit, isVisible }: ModalProps) {
  return (
    <StyledMapModalContainer modalOpen={modalOpen} isVisible={isVisible}>
      <StyledModalContent modalOpen={modalOpen}>
        <StyledExitButton type="button" onClick={onClickExit}>
          <ExitButton />
        </StyledExitButton>
        {children}
      </StyledModalContent>
    </StyledMapModalContainer>
  );
}

export default MapModal;

const StyledModalContent = styled.div<{ modalOpen: boolean }>`
  position: absolute;
  bottom: 0;
  z-index: 1000;

  width: 50%;

  padding: 2rem;

  border-radius: ${BORDER_RADIUS.sm} ${BORDER_RADIUS.sm} 0 0;
  background: var(--white);

  animation: ${({ modalOpen }) => (modalOpen ? slideUp : slideDown)} 0.4s ease-out;
`;

const StyledExitButton = styled.button`
  position: absolute;
  top: 4px;
  right: 4px;

  border: none;
  background-color: transparent;
`;

const slideUp = keyframes`
  0% { 
    transform: translateY(100%);
    opacity: 0;
  }
  100% {
    transform: translateY(0);
    opacity: 1;
  }
`;

const slideDown = keyframes`
  0% {
    transform: translateY(0%);
    opacity: 1;
  }
  100% {
    transform: translateY(100%);
    opacity: 0;
  }
`;

const StyledMapModalContainer = styled.div<{ modalOpen: boolean; isVisible: boolean }>`
  display: flex;
  justify-content: center;

  position: relative;

  width: 100%;

  visibility: ${({ modalOpen, isVisible }) => (modalOpen || isVisible ? 'visible' : 'hidden')};
`;
