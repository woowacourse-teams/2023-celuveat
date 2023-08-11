import axios from 'axios';
import { setupInterceptorsTo } from '~/api/User/HttpUserInterceptor';

import getToken from '~/utils/getToken';

const axiosUserApi = (options?: Record<string, string>) => {
  const instance = axios.create({
    baseURL: `${process.env.BASE_URL}/api`,
    headers: {
      'Content-type': 'application/json',
      ...options,
    },
    withCredentials: true,
  });

  return instance;
};

const mswUserApi = (options?: Record<string, string>) => {
  const token = getToken();
  const instance = axios.create({
    baseURL: '/',
    headers: {
      'Content-type': 'application/json',
      Cookies: `JSESSIONID=${token}`,
      ...options,
    },
  });

  return instance;
};

export const userInstance = setupInterceptorsTo(axiosUserApi());

export const userMSWInstance = setupInterceptorsTo(mswUserApi());
