import { AxiosError } from 'axios';
import { shallow } from 'zustand/shallow';
import { useNavigate } from 'react-router-dom';
import { useMutation, useQuery } from '@tanstack/react-query';

import useToastState from '~/hooks/store/useToastState';

import { deleteUserData, getProfile } from '~/api/user';

import type { ProfileData } from '~/@types/api.types';

const useDeleteUser = () => {
  const navigator = useNavigate();

  const { data: profileData } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: getProfile,
    retry: 1,
  });

  const { onFailure, close } = useToastState(
    state => ({
      onSuccess: state.onSuccess,
      onFailure: state.onFailure,
      close: state.close,
    }),
    shallow,
  );

  const onWithdraw = useMutation({
    mutationFn: deleteUserData,
    onSuccess: () => {
      window.location.href = '/';
    },
    onError: (error: AxiosError) => {
      switch (error.response.status) {
        case 401:
          onFailure('로그인 후 다시 시도해주세요.');
          break;
        default:
          onFailure(error.response.data as string);
      }
    },
  });

  return {
    deleteUser: () => {
      onWithdraw.mutate(profileData.oauthServer);
      close();
    },
    onCancelDeleteUser: () => {
      navigator(-1);
    },
  };
};

export default useDeleteUser;
