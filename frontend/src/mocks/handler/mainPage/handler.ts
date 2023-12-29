import { rest } from 'msw';
import restaurants from '~/mocks/data/restaurants';
import { recommendation } from '~/mocks/data/recommendation';
import { RECOMMENDED_REGION } from '~/constants/recommendedRegion';
import { getRandomNumber } from '~/utils/getRandomNumber';

export const newMainPageHandler = [
  rest.get('/address', (req, res, ctx) => {
    const queryParams = req.url.searchParams;
    const regionKey = queryParams.get('codes') as keyof typeof RECOMMENDED_REGION;
    const regions = RECOMMENDED_REGION[regionKey].name;

    const restaurantFilteredByRegion = restaurants.filter(({ roadAddress }) =>
      regions.some(region => roadAddress.includes(region)),
    );

    return res(ctx.status(200), ctx.json({ content: restaurantFilteredByRegion }));
  }),

  rest.get('/main-page/recommendation', (req, res, ctx) => {
    const responses = [res(ctx.status(401)), res(ctx.status(401)), res(ctx.status(200), ctx.json(recommendation))];
    return responses[getRandomNumber()];
    // return res(ctx.status(400), ctx.json(recommendation));
    // return res(ctx.status(200), ctx.json(recommendation));
  }),
];
