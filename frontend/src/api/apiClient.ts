import axios from 'axios';

export const apiUserClient = axios.create({
  baseURL: `${process.env.BASE_URL}`,
  headers: {
    'Content-type': 'application/json',
  },
  withCredentials: true,
});

export const apiClient = axios.create({
  baseURL: `${process.env.BASE_URL}`,
  headers: {
    'Content-type': 'application/json',
  },
});

export const apiUserFilesClient = axios.create({
  baseURL: `${process.env.BASE_URL}`,
  headers: {
    'Content-Type': 'multipart/form-data',
  },
  withCredentials: true,
});
