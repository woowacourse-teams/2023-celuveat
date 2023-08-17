import styled from 'styled-components';
import LoginButton from '~/components/@common/LoginButton';

function LoginModalContent() {
  return (
    <StyledLoginModalContent>
      <LoginButton type="kakao" />
      <LoginButton type="google" />
    </StyledLoginModalContent>
  );
}

export default LoginModalContent;

const StyledLoginModalContent = styled.div`
  div + div {
    margin-top: 1.6rem;
  }
`;
