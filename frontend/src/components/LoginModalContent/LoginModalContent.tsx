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
  display: flex;
  flex-direction: column;

  margin-bottom: 1.6rem;
`;
