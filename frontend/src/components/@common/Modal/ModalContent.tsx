import styled, { css } from 'styled-components';
import Exit from '~/assets/icons/exit.svg';
import useMediaQuery from '~/hooks/useMediaQuery';

interface ModalContentProps {
  isShow?: boolean;
  title: string;
  closeModal: () => void;
  children: React.ReactNode;
}

function ModalContent({ isShow = false, title, closeModal, children }: ModalContentProps) {
  const { isMobile } = useMediaQuery();

  return (
    <StyledModalContentWrapper isShow={isShow} isMobile={isMobile}>
      <StyledModalOverlay onClick={closeModal} />
      <StyledModalContent isShow={isShow} isMobile={isMobile}>
        <StyledModalHeader>
          <StyledExitButton onClick={closeModal} />
          <StyledModalTitleText>{title}</StyledModalTitleText>
        </StyledModalHeader>
        <StyledModalBody>{children}</StyledModalBody>
      </StyledModalContent>
    </StyledModalContentWrapper>
  );
}

export default ModalContent;

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

  background: rgb(0 0 0 / 50%);
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

  padding: 2rem;

  border-radius: 5px;
  background: #fff;

  transition: transform ease 0.3s 0.1s;
  transform: translateY(80px);

  overflow-y: auto;

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
`;

const StyledModalTitleText = styled.span`
  margin: 0 auto;
`;

const StyledModalBody = styled.div`
  margin-top: 2.4rem;
`;
