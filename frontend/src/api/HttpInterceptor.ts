/* eslint-disable class-methods-use-this */
import { AxiosError, AxiosInstance, InternalAxiosRequestConfig, AxiosResponse } from 'axios';

abstract class HttpInterceptor {
  instance;

  constructor(axiosInstance: AxiosInstance) {
    this.instance = axiosInstance;
  }

  onRequest(config: InternalAxiosRequestConfig): InternalAxiosRequestConfig {
    return config;
  }

  onRequestError(error: AxiosError): Promise<AxiosError> {
    return Promise.reject(error);
  }

  onResponse(response: AxiosResponse): AxiosResponse {
    return response;
  }

  onResponseError(error: AxiosError): Promise<AxiosError | Error> {
    return Promise.reject(error);
  }

  public setupInterceptorsTo(): AxiosInstance {
    this.instance.interceptors.request.use(this.onRequest, this.onRequestError);
    this.instance.interceptors.response.use(this.onResponse, this.onResponseError);
    return this.instance;
  }
}

export default HttpInterceptor;
