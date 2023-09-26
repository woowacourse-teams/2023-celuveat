import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { getAccessToken, getLogout } from '~/api/user';

import usePathNameState from '~/hooks/store/usePathnameState';

const useAuth = () => {
  const navigator = useNavigate();
  const path = usePathNameState(state => state.path);

  const doLoginMutation = useMutation({
    mutationFn: getAccessToken,
    onSuccess: () => {
      navigator(path);
    },
    onError: () => {
      navigator('/');
      alert('서버 문제로 인해 로그인에 실패하였습니다.');
    },
  });

  const doLogoutMutation = useMutation({
    mutationFn: getLogout,
    onSuccess: () => {
      window.location.reload();
    },
    onError: () => {
      navigator('/');
      alert('서버 문제로 인해 로그아웃에 실패하였습니다.');
    },
  });

  return {
    doLoginMutation: doLoginMutation.mutate,
    doLogoutMutation: doLogoutMutation.mutate,
  };
};

export default useAuth;
