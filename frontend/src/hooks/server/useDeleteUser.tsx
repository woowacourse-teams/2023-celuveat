import { useCallback } from 'react';
import { useNavigate } from 'react-router-dom';

import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';

import { shallow } from 'zustand/shallow';

import useToastState from '~/hooks/store/useToastState';
import useTokenState from '~/hooks/store/useTokenState';

import { deleteUserData } from '~/api/oauth';

const useDeleteUser = () => {
  const navigator = useNavigate();
  const oauth = useTokenState(state => state.oauth);
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
      navigator('/');
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

  const deleteUser = useCallback(() => {
    if (oauth !== '') {
      onWithdraw.mutate(oauth);
    }

    close();
  }, []);

  const onCancelDeleteUser = useCallback(() => {
    navigator('/');
  }, []);

  return { onCancelDeleteUser, deleteUser };
};

export default useDeleteUser;
