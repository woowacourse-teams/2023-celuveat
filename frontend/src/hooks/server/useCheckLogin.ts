import { useQuery } from '@tanstack/react-query';
import { getProfile } from '~/api/user';

import type { ProfileData } from '~/@types/api.types';

const useCheckLogin = () => {
  const { data } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: getProfile,
  });

  return {
    isLogin: Boolean(data),
  };
};

export default useCheckLogin;
