import { rest } from 'msw';

import restaurants from '../../data/restaurants';
import correction from '../../data/correction';
import reviews from '../../data/reviews';
import { profile } from './../../data/user';
import { videos, originVideos } from '../../data/videos';

import type { Celeb } from '~/@types/celeb.types';
import type { RestaurantData, VideoList } from '~/@types/api.types';
import { HyphenatedDate } from '~/@types/date.types';
import { parseFormDataForReview } from '~/mocks/utils';

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
    const queryParams = req.url.searchParams;
    const celebId = Number(queryParams.get('celebId')) || null;
    const restaurantId = Number(queryParams.get('restaurantId')) || null;
    const page = Number(queryParams.get('page')) || 0;
    const pageSize = 6;

    const restaurantVideos = originVideos
      .filter(({ restaurant_id }) => (restaurantId ? restaurantId === restaurant_id : true))
      .filter(({ celeb_id }) => (celebId ? celebId === celeb_id : true))
      .map(({ id }) => videos.find(({ videoId }) => videoId === id));

    const content = restaurantVideos.slice(page * pageSize, (page + 1) * pageSize);

    const restaurantVideoList: VideoList = {
      content,
      totalPage: Math.ceil(restaurantVideos.length / pageSize),
      currentPage: page,
      pageSize,
      totalElementsCount: restaurantVideos.length,
      currentElementsCount: content.length,
    };

    return res(ctx.status(200), ctx.json(restaurantVideoList));
  }),

  rest.get('/restaurants/:restaurantId/nearby', (req, res, ctx) => {
    const queryParams = req.url.searchParams;
    const { restaurantId } = req.params;
    const page: number = Number(queryParams.get('page')) || 0;
    const pageSize = 6;

    const restaurant = restaurants.find(({ id }) => id === Number(restaurantId));
    const nearbyRestaurants = restaurants.filter(({ distance }) => Math.abs(restaurant.distance - distance) < 500);
    const content = nearbyRestaurants.slice(page * pageSize, (page + 1) * pageSize);

    const nearbyRestaurantList = {
      content,
      totalPage: Math.ceil(nearbyRestaurants.length / pageSize),
      currentPage: page,
      pageSize,
      totalElementsCount: nearbyRestaurants.length,
      currentElementsCount: content.length,
    };

    return res(ctx.status(200), ctx.json(nearbyRestaurantList));
  }),

  rest.post('/restaurants/:restaurantId/correction', async (req, res, ctx) => {
    const { restaurantId } = req.params;
    const { contents } = await req.json();

    correction.push({ restaurantId: Number(restaurantId), contents });

    ctx.status(200);
  }),

  rest.get('/reviews', (req, res, ctx) => {
    const reviewsData = { reviews, totalElementsCount: reviews.length };

    return res(ctx.status(200), ctx.json(reviewsData));
  }),

  rest.post('/reviews', async (req, res, ctx) => {
    const { JSESSION } = req.cookies;
    const { images, content, rate, restaurantId } = parseFormDataForReview(req.body as string);

    const date = new Date();
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();

    // 사용자가 올린 이미지를 CDN에 저장, 그로 인한 url 변경하는 동작을 msw 코드로 구현
    const makeImage = (count: number) => {
      return Array.from({ length: count }).map(() => 'https://t1.daumcdn.net/cfile/tistory/224CEE3C577E3C7503');
    };

    reviews.push({
      id: reviews.length + 1,
      nickname: '푸만능',
      memberId: 1,
      profileImageUrl: 'https://a0.muscache.com/im/pictures/user/93c7d7c8-86d9-4390-ba09-a8e6f4eb7f0f.jpg?im_w=240',
      content,
      isLiked: false,
      likeCount: 97,
      rate,
      createdDate: `${year}-${month}-${day}` as HyphenatedDate,
      reviewImageUrls: makeImage(images.length),
    });

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    return res(ctx.status(201));
  }),

  rest.patch('/reviews/:reviewId', async (req, res, ctx) => {
    const { reviewId } = req.params;
    const { JSESSION } = req.cookies;

    const { images, content, rate } = parseFormDataForReview(req.body as string);

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    const review = reviews.find(({ id }) => Number(reviewId) === id);

    review['content'] = content;
    review['reviewImageUrls'] = images;

    return res(ctx.status(204));
  }),

  rest.delete('/reviews/:reviewId', (req, res, ctx) => {
    const { reviewId } = req.params;
    const { JSESSION } = req.cookies;

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    for (let i = 0; i < reviews.length; i++) {
      if (reviews[i].id === Number(reviewId)) {
        reviews.splice(i, 1);
        break;
      }
    }

    return res(ctx.status(204));
  }),

  rest.post('/reviews/:reviewId/like', async (req, res, ctx) => {
    const { JSESSION } = req.cookies;
    const { reviewId } = req.params;

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    const review = reviews.find(review => review.id === Number(reviewId));

    if (review.memberId === profile.memberId) {
      return res(ctx.status(404), ctx.json({ message: 'Bad Request' }));
    }

    if (review.isLiked) {
      review['isLiked'] = false;
      review['likeCount'] -= 1;
    } else {
      review['isLiked'] = true;
      review['likeCount'] += 1;
    }

    return res(ctx.status(200));
  }),

  rest.post('/reviews/:reviewId/report', async (req, res, ctx) => {
    const { JSESSION } = req.cookies;
    const { content } = await req.json();

    if (JSESSION === undefined) {
      return res(ctx.status(401), ctx.json({ message: '만료된 세션입니다.' }));
    }

    return res(ctx.status(200));
  }),
];

export const DetailPageErrorHandler = [
  rest.get('/restaurants/like', (req, res, ctx) => {
    return res(ctx.status(401));
  }),
];
