import { rest } from 'msw';

import { mockCelebs, mockProfileData, mockRestaurantListData } from '~/mocks/mainPage/fixures';

export const MainPageSuccessHandler = [
  rest.get('/restaurants', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockRestaurantListData));
  }),

  rest.get('/celebs', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockCelebs));
  }),

  rest.get('/oauth/login/:oauthType', (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.get('/oauth/logout/:oauthType', (req, res, ctx) => {
    return res(ctx.status(204));
  }),

  rest.delete('/oauth/withdraw/:oauthType', (req, res, ctx) => {
    return res(ctx.status(204));
  }),

  rest.get('/members/my', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockProfileData));
  }),

  rest.post('/restaurants/:restaurantId/like', (req, res, ctx) => {
    return res(ctx.status(200));
  }),
];

export const MainPageErrorHandler = [
  rest.post('/restaurants/:restaurantId/like', (req, res, ctx) => {
    return res(ctx.status(401));
  }),

  rest.delete('/oauth/withdraw/:oauthType', (req, res, ctx) => {
    return res(ctx.status(401));
  }),
];
