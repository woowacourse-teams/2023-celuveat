import { BASE_URL } from '../constants/api';

import type { Oauth } from '~/@types/oauth.types';
import { apiClient } from '~/api';
import { userInstance, userMSWInstance } from '~/api/User';

const getAccessToken = async (type: Oauth, code: string) => {
  const response = await apiClient.get(`${BASE_URL}/api/oauth/login/${type}?code=${code}`);
  return response.data;
};

export const getLogout = async (type: Oauth) => {
  const response = await userInstance.post(`${BASE_URL}/api/oauth/logout/${type}`);
  return response.data;
};

export const getMSWLogout = async (type: Oauth) => {
  const response = await userMSWInstance.post(`${BASE_URL}/api/oauth/logout/${type}`);
  return response.data;
};

export const getProfile = async () => {
  const response = await userInstance.get('profile');
  return response.data;
};

export const getMSWProfile = async () => {
  const response = await userMSWInstance.get('profile');
  return response.data;
};

export default getAccessToken;
