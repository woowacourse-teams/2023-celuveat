import { setupWorker } from 'msw';
import { DetailPageSuccessHandler, DetailPageErrorHandler } from '~/mocks/detailPage/handler';
import { MainPageSuccessHandler, MainPageErrorHandler } from '~/mocks/mainPage/handler';
import { WishListPageSuccessHandler, WishListPageErrorHandler } from '~/mocks/wishListPage/handler';

const successHandler = [...DetailPageSuccessHandler, ...MainPageSuccessHandler, ...WishListPageSuccessHandler];
const errorHandler = [...DetailPageErrorHandler, ...MainPageErrorHandler, ...WishListPageErrorHandler];

export const worker = setupWorker(...successHandler);
