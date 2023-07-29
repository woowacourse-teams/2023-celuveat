import styled from 'styled-components';
import LoginButton from '~/components/@common/LoginButton';

function LoginModalContent() {
  return (
    <StyledLoginModalContent>
      <LoginButton type="naver" />
      <LoginButton type="kakao" />
      <LoginButton type="google" />
    </StyledLoginModalContent>
  );
}

export default LoginModalContent;

const StyledLoginModalContent = styled.div`
  a + a {
    margin-top: 1.6rem;
  }
`;
