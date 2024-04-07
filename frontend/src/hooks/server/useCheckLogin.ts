import { useQuery } from '@tanstack/react-query';
import { getProfile } from '~/api/user';

import type { ProfileData } from '~/@types/api.types';

const useCheckLogin = () =>
  useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: getProfile,
  });

export default useCheckLogin;
