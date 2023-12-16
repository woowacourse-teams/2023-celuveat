import { styled } from 'styled-components';
import LoginButton from '~/components/@common/LoginButton';
import Dialog from '../@common/Dialog';

function LoginModal() {
  return (
    <Dialog title="로그인 및 회원가입">
      <StyledLoginModalContent>
        <LoginButton type="kakao" />
        <LoginButton type="google" />
      </StyledLoginModalContent>
    </Dialog>
  );
}

export default LoginModal;

const StyledLoginModalContent = styled.div`
  display: flex;
  flex-direction: column;

  margin-bottom: 1.6rem;
`;
