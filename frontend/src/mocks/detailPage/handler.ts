import { rest } from 'msw';
import restaurants from '../data/restaurants';
import { mockVideoList, mockRestaurantReviews } from '~/mocks/detailPage/fixures';
import { mockRestaurantListData } from '~/mocks/mainPage/fixures';

import type { RestaurantData } from '~/@types/api.types';
import { Celeb } from '~/@types/celeb.types';

export const DetailPageSuccessHandler = [
  rest.get('/restaurants/:restaurantsId', (req, res, ctx) => {
    const queryParams = req.url.searchParams;
    const celebId = Number(queryParams.get('celebId'));
    const { restaurantsId } = req.params;

    const restaurant: RestaurantData = restaurants.find(({ id }) => {
      return id === Number(restaurantsId);
    });

    const { celebs, ...etc } = restaurant;

    function moveCelebToFrontById(celebs: Celeb[], targetId: number): Celeb[] {
      const targetIndex = celebs.findIndex(celeb => celeb.id === targetId);

      if (targetIndex === -1) return celebs;

      const newArray = [...celebs];
      const [movedCeleb] = newArray.splice(targetIndex, 1);
      newArray.unshift(movedCeleb);

      return newArray;
    }

    const sortedCelebs = moveCelebToFrontById(celebs, celebId);

    return res(ctx.status(200), ctx.json({ celebs: sortedCelebs, ...etc }));
  }),

  rest.get('/videos', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockVideoList));
  }),

  rest.get('/restaurants/:restaurantId/nearby', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockRestaurantListData));
  }),

  rest.post('/restaurants/:restaurantId/correction', (req, res, ctx) => {
    ctx.status(200);
  }),

  rest.get('/reviews', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockRestaurantReviews));
  }),

  rest.post('/reviews', (req, res, ctx) => {
    const { JSESSION } = req.cookies;

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    return res(ctx.status(201));
  }),

  rest.patch('/reviews/:reviewId', (req, res, ctx) => {
    const { JSESSION } = req.cookies;

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    return res(ctx.status(204));
  }),

  rest.patch('/reviews/:reviewId', (req, res, ctx) => {
    const { JSESSION } = req.cookies;

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    return res(ctx.status(204));
  }),

  rest.delete('/reviews/:reviewId', (req, res, ctx) => {
    const { JSESSION } = req.cookies;

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    return res(ctx.status(204));
  }),
];

export const DetailPageErrorHandler = [
  rest.get('/restaurants/like', (req, res, ctx) => {
    return res(ctx.status(401));
  }),
];
