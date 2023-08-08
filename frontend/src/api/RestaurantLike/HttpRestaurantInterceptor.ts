/* eslint-disable class-methods-use-this */
/* eslint-disable no-param-reassign */
import axios, { InternalAxiosRequestConfig, AxiosInstance, AxiosError, AxiosResponse } from 'axios';
import { isEmptyString } from '~/utils/compare';

class HttpRestaurantLikeInterceptor {
  instance: AxiosInstance;

  constructor(axiosInstance: AxiosInstance) {
    this.instance = axiosInstance;
  }

  onRequest(config: InternalAxiosRequestConfig) {
    return config;
  }

  onRequestError(error: AxiosError): Promise<AxiosError> {
    return Promise.reject(error);
  }

  onResponse(response: AxiosResponse): AxiosResponse {
    if (isEmptyString(response.data)) {
      response.data = '좋아요 성공했습니다!!';
    }

    return response;
  }

  onResponseError(error: AxiosError) {
    if (axios.isAxiosError(error)) {
      const { status } = error.response as AxiosResponse;

      switch (status) {
        case 400: {
          error.response.data = '로그인이 필요없습니다.';
          break;
        }

        case 403: {
          error.response.data = '권한이 없습니다.';
          break;
        }

        case 404: {
          error.response.data = '알 수 없는 요청입니다.';
          break;
        }

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
  }

  public setupInterceptorsTo(): AxiosInstance {
    this.instance.interceptors.request.use(this.onRequest, this.onRequestError);
    this.instance.interceptors.response.use(this.onResponse, this.onResponseError);
    return this.instance;
  }
}

export default HttpRestaurantLikeInterceptor;
