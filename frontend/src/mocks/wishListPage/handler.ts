import { rest } from 'msw';
import { mockRestaurantListData } from '~/mocks/mainPage/fixures';

export const WishListPageSuccessHandler = [
  rest.get('/restaurants/like', (req, res, ctx) => {
    const { JSESSION } = req.cookies;

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

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
