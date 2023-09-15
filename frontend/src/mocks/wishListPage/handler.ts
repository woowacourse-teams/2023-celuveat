import { rest } from 'msw';
import restaurants from '../data/restaurants';

export const WishListPageSuccessHandler = [
  rest.get('/restaurants/like', (req, res, ctx) => {
    const { JSESSION } = req.cookies;

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    return res(ctx.status(200), ctx.json(restaurants.filter(restaurant => restaurant.isLiked)));
  }),
];

export const WishListPageErrorHandler = [
  rest.get('/restaurants/like', (req, res, ctx) => {
    return res(ctx.status(401));
  }),
];
