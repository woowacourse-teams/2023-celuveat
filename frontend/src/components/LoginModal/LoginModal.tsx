import { styled } from 'styled-components';
import Modal from 'celuveat-react-modal';
import LoginButton from '~/components/@common/LoginButton';
import useMediaQuery from '~/hooks/useMediaQuery';

interface LoginModalProps {
  isOpen: boolean;
  close: VoidFunction;
}

function LoginModal({ isOpen, close }: LoginModalProps) {
  const { isMobile } = useMediaQuery();

  return (
    <Modal isOpen={isOpen} close={close} title="로그인 및 회원가입" isMobile={isMobile}>
      <StyledLoginModalContent>
        <LoginButton type="kakao" />
        <LoginButton type="google" />
      </StyledLoginModalContent>
    </Modal>
  );
}

export default LoginModal;

const StyledLoginModalContent = styled.div`
  display: flex;
  flex-direction: column;

  margin-bottom: 1.6rem;
`;
