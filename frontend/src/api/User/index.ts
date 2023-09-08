import axios from 'axios';
import { setupInterceptorsTo } from '~/api/User/HttpUserInterceptor';

const axiosUserApi = (options?: Record<string, string>) => {
  const instance = axios.create({
    baseURL: `${process.env.BASE_URL}`,
    headers: {
      'Content-type': 'application/json',
      ...options,
    },
    withCredentials: true,
  });

  return instance;
};

const mswUserApi = (options?: Record<string, string>) => {
  const instance = axios.create({
    baseURL: '/',
    headers: {
      'Content-type': 'application/json',
      ...options,
    },
    withCredentials: true,
  });

  return instance;
};

export const userInstance = setupInterceptorsTo(axiosUserApi());

export const userMSWInstance = setupInterceptorsTo(mswUserApi());
