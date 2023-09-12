import axios from 'axios';
import { useMemo } from 'react';
import useBaseURLState from '~/hooks/store/useBaseURLState';

const useAPIClient = () => {
  const [baseURL] = useBaseURLState(state => [state.baseURL]);

  const apiClient = useMemo(
    () =>
      axios.create({
        baseURL,
        headers: {
          'Content-type': 'application/json',
        },
      }),
    [baseURL],
  );

  return { apiClient };
};

export default useAPIClient;
