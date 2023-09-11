import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import LoginPageUI from '~/components/LoginPageUI';
import WithdrawalModalContent from '~/components/WithdrawalModalContent';

import { isLogin } from '~/utils/cookies';

function WithdrawalPage() {
  const navigator = useNavigate();

  useEffect(() => {
    if (!isLogin()) {
      navigator('/signUp');
    }
  }, []);

  return (
    <LoginPageUI title="회원 탈퇴하기">
      <WithdrawalModalContent />
    </LoginPageUI>
  );
}

export default WithdrawalPage;
