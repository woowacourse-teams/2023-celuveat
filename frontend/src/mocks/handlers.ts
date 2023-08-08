import { rest } from 'msw';

import * as Fixture from '~/mocks/fixtures';

export const handlers = [
  rest.get('/restaurants', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(Fixture.restaurantListData));
  }),

  rest.get('/restaurants/like', (req, res, ctx) => {
    const data = Fixture.restaurantListData.content.filter(restaurantItem => {
      return restaurantItem.isLiked;
    });

    return res(ctx.status(200), ctx.json(data));
  }),

  rest.post('/restaurants/:restaurantId/like', (req, res, ctx) => {
    const { restaurantId } = req.params;

    const newData = Fixture.restaurantListData.content.map(restaurantItem =>
      restaurantItem.id === Number(restaurantId)
        ? { ...restaurantItem, isLiked: !restaurantItem.isLiked }
        : restaurantItem,
    );

    Fixture.restaurantListData.content = newData;

    return res(ctx.status(200));
  }),
];

export const errorHandlers = [
  rest.get('/restaurants', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(Fixture.restaurantListData));
  }),

  rest.post('/restaurants/:restaurantId/like', (req, res, ctx) => {
    return res(ctx.status(404));
  }),
];
