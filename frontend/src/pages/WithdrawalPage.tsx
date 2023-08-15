import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import LoginPageUI from '~/components/LoginPageUI';
import PopUpContainer from '~/components/PopUpContainer';
import WithdrawalModalContent from '~/components/WithdrawalModalContent';
import useTokenState from '~/hooks/store/useTokenState';

import { isEmptyString } from '~/utils/compare';

function WithdrawalPage() {
  const navigator = useNavigate();
  const token = useTokenState(state => state.token);

  useEffect(() => {
    if (isEmptyString(token)) {
      navigator('/signUp');
    }
  }, []);

  return (
    <>
      <LoginPageUI title="회원 탈퇴하기">
        <WithdrawalModalContent />
      </LoginPageUI>
      <PopUpContainer />
    </>
  );
}

export default WithdrawalPage;
