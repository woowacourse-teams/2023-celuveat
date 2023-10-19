import { setupWorker } from 'msw';
import {
  WishListPageSuccessHandler,
  newMainPageHandler,
  DetailPageSuccessHandler,
  MainPageSuccessHandler,
} from '~/mocks/handler';
import { latestPageHandler } from '~/mocks/handler/latestPages/handler';
import { regionPageHandler } from '~/mocks/handler/regionPage/handler';

export const worker = setupWorker(
  ...WishListPageSuccessHandler,
  ...DetailPageSuccessHandler,
  ...MainPageSuccessHandler,
  ...newMainPageHandler,
  ...regionPageHandler,
  ...latestPageHandler,
);
