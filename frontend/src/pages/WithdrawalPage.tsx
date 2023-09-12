import { useQuery } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ProfileData } from '~/@types/api.types';

import LoginPageUI from '~/components/LoginPageUI';
import WithdrawalModalContent from '~/components/WithdrawalModalContent';
import useUser from '~/hooks/server/useUser';

function WithdrawalPage() {
  const navigator = useNavigate();
  const { getProfile } = useUser();

  const { isSuccess } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: () => getProfile(),
    staleTime: Infinity,
  });

  useEffect(() => {
    if (!isSuccess) {
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
