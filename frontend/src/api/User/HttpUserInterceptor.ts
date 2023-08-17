/* eslint-disable no-param-reassign */
/* eslint-disable @typescript-eslint/dot-notation */
import axios, { InternalAxiosRequestConfig, AxiosInstance, AxiosError, AxiosResponse } from 'axios';
import getToken from '~/utils/getToken';

const onRequest = (config: InternalAxiosRequestConfig) => {
  const token = getToken();
  document.cookie = `JSESSIONID=${token}; domain=www.celuveat.com path=/`;
  config.headers['Cookie'] = `JSESSIONID=${token}`;

  return config;
};

const onRequestError = (error: AxiosError): Promise<AxiosError> => Promise.reject(error);

const onResponse = (response: AxiosResponse): AxiosResponse => response;

const onResponseError = (error: AxiosError) => {
  if (axios.isAxiosError(error)) {
    const { status } = error.response as AxiosResponse;

    switch (status) {
      case 500: {
        error.response.data = '서버에서 오류가 생겼습니다.';
        break;
      }

      default: {
        error.response.data = '알 수 없는 에러가 발생했습니다.';
        break;
      }
    }
  }

  return Promise.reject(error);
};

export const setupInterceptorsTo = (axiosInstance: AxiosInstance): AxiosInstance => {
  axiosInstance.interceptors.request.use(onRequest, onRequestError);
  axiosInstance.interceptors.response.use(onResponse, onResponseError);
  return axiosInstance;
};

export default setupInterceptorsTo;
