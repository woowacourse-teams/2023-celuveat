import { useQueryClient } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ProfileData } from '~/@types/api.types';

import LoginPageUI from '~/components/LoginPageUI';
import WithdrawalModalContent from '~/components/WithdrawalModalContent';

function WithdrawalPage() {
  const navigator = useNavigate();

  const qc = useQueryClient();
  const profileData: ProfileData = qc.getQueryData(['profile']);

  useEffect(() => {
    if (!profileData) {
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
