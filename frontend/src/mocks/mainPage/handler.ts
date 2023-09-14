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
    const code = req.url.searchParams.get('code') ?? null;

    if (code === null) {
      return res(ctx.status(401), ctx.json({ message: '인증되지 않은 code입니다.' }));
    }

    const currentDate = new Date();
    const sixHoursInMilliseconds = 6 * 60 * 60 * 1000;
    const expirationDate = new Date(currentDate.getTime() + sixHoursInMilliseconds);
    return res(ctx.cookie('JSESSION', `${code}`, { expires: expirationDate }), ctx.status(200));
  }),

  rest.get('/oauth/logout/:oauthType', (req, res, ctx) => {
    const { JSESSION } = req.cookies;

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    return res(ctx.status(200), ctx.cookie('JSESSION', '', { expires: new Date() }));
  }),

  rest.delete('/oauth/withdraw/:oauthType', (req, res, ctx) => {
    const { JSESSION } = req.cookies;

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    // 회원 탈퇴 시에 쿠키 바로 만료
    return res(ctx.status(204), ctx.cookie('JSESSION', '', { expires: new Date() }));
  }),

  rest.get('/members/my', (req, res, ctx) => {
    const { JSESSION } = req.cookies;

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    // 쿠키 갱신
    return res(ctx.status(200), ctx.json(mockProfileData));
  }),

  rest.post('/restaurants/:restaurantId/like', (req, res, ctx) => {
    const { JSESSION } = req.cookies;

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

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
