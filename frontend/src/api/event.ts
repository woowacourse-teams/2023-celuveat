import { apiFilesClient } from './apiClient';

export const postEventForm = async (body: FormData) => {
  const response = await apiFilesClient.post(`/event`, body);
  return response;
};
