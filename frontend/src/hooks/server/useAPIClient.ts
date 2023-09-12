import axios from 'axios';
import useBaseURLState from '~/hooks/store/useBaseURLState';

const useAPIClient = () => {
  const [baseURL] = useBaseURLState(state => [state.baseURL]);

  const apiClient = axios.create({
    baseURL,
    headers: {
      'Content-type': 'application/json',
    },
  });

  return { apiClient };
};

export default useAPIClient;
