import styled from 'styled-components';
import LoginButton from '~/components/@common/LoginButton';
import LoginPageUI from '~/components/LoginPageUI';

function SignUpPage() {
  return (
    <LoginPageUI title="로그인 및 회원가입">
      <StyledLoginContainer>
        <LoginButton type="kakao" />
        <LoginButton type="google" />
      </StyledLoginContainer>
    </LoginPageUI>
  );
}

export default SignUpPage;

const StyledLoginContainer = styled.div`
  display: flex;
  flex-direction: column;

  margin-bottom: 1.6rem;
`;
