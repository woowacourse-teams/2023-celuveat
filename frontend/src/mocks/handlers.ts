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
    return res(ctx.status(200), ctx.json(Fixture.restaurantWishListData));
  }),

  rest.delete('/oauth/withdraw/:oauthType', (req, res, ctx) => {
    return res(ctx.status(204));
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

  rest.get('/reviews', (req, res, ctx) => {
    const restaurantId = req.url.searchParams.get('restaurantId');
    console.log(restaurantId);

    return res(ctx.status(200), ctx.json(Fixture.restaurantReviews));
  }),

  rest.post('/reviews', (req, res, ctx) => {
    return res(ctx.status(201));
  }),

  rest.patch('/reviews/:reviewId', (req, res, ctx) => {
    return res(ctx.status(204));
  }),

  rest.patch('/reviews/:reviewId', (req, res, ctx) => {
    return res(ctx.status(204));
  }),

  rest.delete('/reviews/:reviewId', (req, res, ctx) => {
    return res(ctx.status(204));
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

export const error401handlers = [
  rest.get('/restaurants/:restaurantId/reviews', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(Fixture.restaurantReviews));
  }),

  rest.post('/restaurants/:restaurantId/reviews', (req, res, ctx) => {
    return res(ctx.status(401));
  }),

  rest.patch('/restaurants/:restaurantId/like/:reviewId', (req, res, ctx) => {
    return res(ctx.status(401));
  }),

  rest.patch('/restaurants/:restaurantId/reviews/:reviewId', (req, res, ctx) => {
    return res(ctx.status(401));
  }),

  rest.delete('/restaurants/:restaurantId/reviews/:reviewId', (req, res, ctx) => {
    return res(ctx.status(401));
  }),

  rest.delete('/oauth/withdraw/:oauthType', (req, res, ctx) => {
    return res(ctx.status(401));
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
