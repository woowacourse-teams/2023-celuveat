import LoginPageUI from '~/components/LoginPageUI';
import WithdrawalModalContent from '~/components/WithdrawalModalContent';
import LoginErrorHandleComponent from '~/components/@common/LoginErrorHandleComponent';

function WithdrawalPage() {
  return (
    <LoginErrorHandleComponent>
      <LoginPageUI title="회원 탈퇴하기">
        <WithdrawalModalContent />
      </LoginPageUI>
    </LoginErrorHandleComponent>
  );
}

export default WithdrawalPage;
