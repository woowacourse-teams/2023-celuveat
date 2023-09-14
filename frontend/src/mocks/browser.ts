import { setupWorker } from 'msw';
import { DetailPageSuccessHandler, DetailPageErrorHandler } from '~/mocks/detailPage/handler';
import { MainPageSuccessHandler, MainPageErrorHandler } from '~/mocks/mainPage/handler';
import { WishListPageSuccessHandler, WishListPageErrorHandler } from '~/mocks/wishListPage/handler';

export const worker = setupWorker(
  ...WishListPageSuccessHandler,
  ...DetailPageSuccessHandler,
  ...MainPageSuccessHandler,
);
