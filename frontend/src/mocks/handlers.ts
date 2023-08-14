import { rest } from 'msw';

import * as Fixture from '~/mocks/fixtures';

export const handlers = [
  rest.get('/restaurants', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(Fixture.restaurantListData));
  }),

  rest.get('/profile', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(Fixture.profileData));
  }),

  rest.get('/restaurants/like', (req, res, ctx) => {
    const data = Fixture.restaurantListData.content.filter(restaurantItem => {
      return restaurantItem.isLiked;
    });

    return res(ctx.status(200), ctx.json(data));
  }),

  rest.get('/restaurants/:restaurantId/reviews', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(Fixture.restaurantReviews));
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

export const errorPostLike400handlers = [
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
    return res(ctx.status(400));
  }),
];

export const errorPostLike403Handlers = [
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
    return res(ctx.status(403));
  }),
];
export const errorPostLike404Handlers = [
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
    return res(ctx.status(404));
  }),
];

export const errorPostLike500Handlers = [
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
    return res(ctx.status(500));
  }),
];
