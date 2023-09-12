import axios from 'axios';

export const userInstance = axios.create({
  baseURL: `${process.env.BASE_URL_DEV}`,
  headers: {
    'Content-type': 'application/json',
  },
  withCredentials: true,
});

export const userMSWInstance = axios.create({
  baseURL: '/',
  headers: {
    'Content-type': 'application/json',
  },
  withCredentials: true,
});
