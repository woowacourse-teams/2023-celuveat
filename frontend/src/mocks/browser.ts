import { setupWorker } from 'msw';
import {
  WishListPageSuccessHandler,
  newMainPageHandler,
  DetailPageSuccessHandler,
  MainPageSuccessHandler,
} from '~/mocks/handler';

export const worker = setupWorker(
  ...WishListPageSuccessHandler,
  ...DetailPageSuccessHandler,
  ...MainPageSuccessHandler,
  ...newMainPageHandler,
);
