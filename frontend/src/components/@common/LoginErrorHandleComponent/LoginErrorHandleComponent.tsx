import { useQuery } from '@tanstack/react-query';
import { useEffect } from 'react';
import { ProfileData } from '~/@types/api.types';
import { getProfile } from '~/api/user';
import useNavigateSignUp from '~/hooks/useNavigateSignUp';

interface LoginErrorHandleComponentProps {
  children: React.ReactNode;
}

function LoginErrorHandleComponent({ children }: LoginErrorHandleComponentProps) {
  const { goSignUp } = useNavigateSignUp();

  const { isLoading, isFetching, isSuccess } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: getProfile,
    retry: 1,
  });

  useEffect(() => {
    if (!isFetching && !isSuccess) {
      goSignUp();
    }
  }, [isFetching, isSuccess]);

  if (isLoading) return null;

  return <main>{children}</main>;
}

export default LoginErrorHandleComponent;
