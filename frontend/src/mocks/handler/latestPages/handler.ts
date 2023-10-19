import { rest } from 'msw';
import restaurants from '~/mocks/data/restaurants';

export const latestPageHandler = [
  rest.get('/main-page/latest', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(restaurants.slice(0, 10)));
  }),
];
