import axios from 'axios';

import { SERVER_URL } from '~/constants/api';

interface TokenStateType {
  state: {
    token: string;
  };
}

const tokenState: TokenStateType = JSON.parse(localStorage.getItem('CELUVEAT-STORAGE') || '');

const { token } = tokenState.state;

export const userApiClient = axios.create({
  baseURL: SERVER_URL,
  headers: {
    'Content-type': 'application/json',
    Cookies: `JSESSIONID=${token}`,
  },
});

const postRestaurantLike = async (id: number) => {
  await userApiClient.post(`/restaurants/${id}/like`);
};

export default postRestaurantLike;
