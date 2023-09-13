import axios from 'axios';
import { useMemo } from 'react';

const useAPIClient = () => {
  const apiClient = useMemo(
    () =>
      axios.create({
        baseURL: `${process.env.BASE_URL}`,
        headers: {
          'Content-type': 'application/json',
        },
      }),
    [],
  );

  const apiUserClient = useMemo(
    () =>
      axios.create({
        baseURL: `${process.env.BASE_URL}`,
        headers: {
          'Content-type': 'application/json',
        },
        withCredentials: true,
      }),
    [],
  );

  return { apiClient, apiUserClient };
};

export default useAPIClient;
