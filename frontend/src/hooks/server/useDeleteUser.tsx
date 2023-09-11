import { useCallback } from 'react';
import { useNavigate } from 'react-router-dom';

import { useMutation, useQueryClient } from '@tanstack/react-query';
import { AxiosError } from 'axios';

import { shallow } from 'zustand/shallow';

import useToastState from '~/hooks/store/useToastState';

import { deleteUserData } from '~/api/oauth';
import type { ProfileData } from '~/@types/api.types';

const useDeleteUser = () => {
  const qc = useQueryClient();
  const navigator = useNavigate();

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

  const deleteUser = useCallback(() => {
    const profileData: ProfileData = qc.getQueryData(['profile']);
    onWithdraw.mutate(profileData.oauthServer);
    close();
    window.location.href = '/';
  }, []);

  const onCancelDeleteUser = useCallback(() => {
    navigator(-1);
  }, []);

  return { onCancelDeleteUser, deleteUser };
};

export default useDeleteUser;
