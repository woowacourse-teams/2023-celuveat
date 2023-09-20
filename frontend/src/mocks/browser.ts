import { setupWorker } from 'msw';
import { WishListPageSuccessHandler, DetailPageSuccessHandler, MainPageSuccessHandler } from '~/mocks/handler';

export const worker = setupWorker(
  ...WishListPageSuccessHandler,
  ...DetailPageSuccessHandler,
  ...MainPageSuccessHandler,
);
