import { styled } from 'styled-components';
import LoginButton from '~/components/@common/LoginButton';

function LoginModal() {
  return (
    <StyledLoginModalContent>
      <LoginButton type="kakao" />
      <LoginButton type="google" />
    </StyledLoginModalContent>
  );
}

export default LoginModal;

const StyledLoginModalContent = styled.div`
  display: flex;
  flex-direction: column;

  margin-bottom: 1.6rem;
`;
