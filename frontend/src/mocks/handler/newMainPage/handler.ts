import { rest } from 'msw';

import restaurants from '../../data/restaurants';
import { RECOMMENDED_REGION } from '~/constants/recommendedRegion';

export const newMainPageHandler = [
  rest.get('/address', (req, res, ctx) => {
    const queryParams = req.url.searchParams;
    const regionKey = queryParams.get('value') as keyof typeof RECOMMENDED_REGION;
    const regions = RECOMMENDED_REGION[regionKey].split(',');
    console.log(regions);

    const restaurantFilteredByRegion = restaurants.filter(({ roadAddress }) =>
      regions.some(region => roadAddress.includes(region)),
    );

    return res(ctx.status(200), ctx.json({ content: restaurantFilteredByRegion }));
  }),
];
