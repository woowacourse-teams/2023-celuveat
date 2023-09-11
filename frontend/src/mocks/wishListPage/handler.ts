import { rest } from 'msw';
import { mockRestaurantListData } from '~/mocks/mainPage/fixures';

export const WishListPageSuccessHandler = [
  rest.get('/restaurants/like', (req, res, ctx) => {
    const data = mockRestaurantListData.content.filter(restaurantItem => {
      return restaurantItem.isLiked;
    });

    return res(ctx.status(200), ctx.json(data));
  }),
];

export const WishListPageErrorHandler = [
  rest.get('/restaurants/like', (req, res, ctx) => {
    return res(ctx.status(401));
  }),
];
