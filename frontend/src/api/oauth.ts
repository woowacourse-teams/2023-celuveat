import axios from 'axios';
import { BASE_URL } from '../constants/api';

import type { Oauth } from '~/@types/oauth.types';

const userApiClient = axios.create({
  baseURL: `${process.env.BASE_URL}/api`,
  headers: {
    'Content-type': 'application/json',
  },
});

const getAccessToken = async (type: Oauth, code: string) => {
  const response = await userApiClient.get(`${BASE_URL}/api/oauth/login/${type}?code=${code}`);
  // 통신 에러 되었을 때 로직 추가
  return response.data;
};

export default getAccessToken;
