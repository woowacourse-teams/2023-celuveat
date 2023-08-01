import { apiClient } from '~/api';

import type { Oauth } from '~/@types/oauth.types';

const getAccessToken = async (type: Oauth, code: string) => {
  const response = await apiClient.get(`${process.env.BASE_URL}/api/oauth/login/${type}?code=${code}`);

  return response.data;
};

export default getAccessToken;
