import { rest } from 'msw';
import type { RestaurantData, RestaurantDetailData } from '~/@types/api.types';

import { mockVideoList, mockRestaurantReviews } from '~/mocks/detailPage/fixures';
import { mockRestaurantListData } from '~/mocks/mainPage/fixures';

export const DetailPageSuccessHandler = [
  rest.get('/restaurants/:restaurantsId', (req, res, ctx) => {
    const { restaurantsId } = req.params;

    const data: RestaurantData = mockRestaurantListData.content.find(restaurantItem => {
      return restaurantItem.id === Number(restaurantsId);
    });

    const result: RestaurantDetailData = {
      id: data.id,
      name: data.name,
      category: data.category,
      roadAddress: data.roadAddress,
      lat: data.lat,
      lng: data.lng,
      distance: 12,
      phoneNumber: data.phoneNumber,
      naverMapUrl: data.naverMapUrl,
      likeCount: 12,
      viewCount: 1112,
      celebs: data.celebs,
      images: data.images,
    };

    return res(ctx.status(200), ctx.json(result));
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
