import LoginModalContent from '~/components/LoginModalContent';
import LoginPageUI from '~/components/LoginPageUI';

function SignUpPage() {
  return (
    <LoginPageUI title="로그인 및 회원가입">
      <LoginModalContent />
    </LoginPageUI>
  );
}

export default SignUpPage;
