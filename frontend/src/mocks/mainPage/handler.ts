import { rest } from 'msw';

import restaurants from '../data/restaurants';
import celebs from '../data/celebs';
import { profile } from '../data/user';

import type { Celeb } from '~/@types/celeb.types';
import type { RestaurantData, RestaurantListData } from '~/@types/api.types';

export const MainPageSuccessHandler = [
  rest.get('/restaurants', (req, res, ctx) => {
    const pageSize = 18;

    const queryParams = req.url.searchParams;
    const sort = queryParams.get('sort') || 'distance';
    const page = Number(queryParams.get('page')) || 0;
    const celebId = Number(queryParams.get('celebId')) || null;
    const category = queryParams.get('category') || null;
    const lowLatitude = Number(queryParams.get('lowLatitude'));
    const highLatitude = Number(queryParams.get('highLatitude'));
    const lowLongitude = Number(queryParams.get('lowLongitude'));
    const highLongitude = Number(queryParams.get('highLongitude'));

    const filteredRestaurants = restaurants.filter(({ celebs, category: restaurantCategory }) => {
      const hasCelebId = celebId ? celebs.map(({ id }) => id).includes(celebId) : true;
      const isMatchCategory = category ? category === restaurantCategory : true;
      return hasCelebId && isMatchCategory;
    });

    const sortedRestaurants = filteredRestaurants
      .filter(restaurant => {
        return (
          restaurant.lat >= lowLatitude &&
          restaurant.lat <= highLatitude &&
          restaurant.lng >= lowLongitude &&
          restaurant.lng <= highLongitude
        );
      })
      .sort((prev, current) => {
        if (sort === 'like') return current.likeCount - prev.likeCount;
        return prev.distance - current.distance;
      });

    function moveCelebToFrontById(celebs: Celeb[], targetId: number): Celeb[] {
      const targetIndex = celebs.findIndex(celeb => celeb.id === targetId);

      if (targetIndex === -1) return celebs;

      const newArray = [...celebs];
      const [movedCeleb] = newArray.splice(targetIndex, 1);
      newArray.unshift(movedCeleb);

      return newArray;
    }

    const content: RestaurantData[] = sortedRestaurants
      .slice(page * pageSize, (page + 1) * pageSize)
      .map(({ celebs, ...etc }) => {
        const sortedCelebs: Celeb[] = moveCelebToFrontById(celebs, celebId);
        return { celebs: sortedCelebs, ...etc };
      });

    const restaurantListData: RestaurantListData = {
      content,
      currentElementsCount: content.length,
      currentPage: page,
      pageSize,
      totalElementsCount: sortedRestaurants.length,
      totalPage: Math.ceil(sortedRestaurants.length / pageSize),
    };

    return res(ctx.status(200), ctx.json(restaurantListData));
  }),

  rest.get('/celebs', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(celebs));
  }),

  rest.get('/oauth/login/:oauthType', (req, res, ctx) => {
    const code = req.url.searchParams.get('code') ?? null;

    if (code === null) {
      return res(ctx.status(401), ctx.json({ message: '인증되지 않은 code입니다.' }));
    }

    const currentDate = new Date();
    const sixHoursInMilliseconds = 6 * 60 * 60 * 1000;
    const expirationDate = new Date(currentDate.getTime() + sixHoursInMilliseconds);
    window.location.href = '/';

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
    return res(ctx.status(200), ctx.json(profile));
  }),

  rest.post('/restaurants/:restaurantId/like', (req, res, ctx) => {
    const { JSESSION } = req.cookies;
    const { restaurantId } = req.params;

    const restaurant = restaurants.find(restaurant => restaurant.id === Number(restaurantId));
    restaurant.isLiked ? (restaurant['isLiked'] = false) : (restaurant['isLiked'] = true);

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
