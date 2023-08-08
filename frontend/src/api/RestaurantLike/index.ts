import axios from 'axios';
import HttpRestaurantLikeInterceptor from '~/api/RestaurantLike/HttpRestaurantInterceptor';

import { SERVER_URL } from '~/constants/api';
import getToken from '~/utils/getToken';

const axiosUserApi = (url: string, options?: Record<string, string>) => {
  const token = getToken();
  const instance = axios.create({
    baseURL: SERVER_URL,
    headers: {
      'Content-type': 'application/json',
      Cookies: `JSESSIONID=${token}`,
      ...options,
    },
  });

  return instance;
};

const httpUserInterceptor = new HttpRestaurantLikeInterceptor(axiosUserApi(SERVER_URL));
const userInstance = httpUserInterceptor.setupInterceptorsTo();

export default userInstance;
