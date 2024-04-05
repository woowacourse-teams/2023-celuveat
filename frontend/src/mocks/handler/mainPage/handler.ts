import { rest } from 'msw';
import restaurants from '~/mocks/data/restaurants';
import { recommendation } from '~/mocks/data/recommendation';
import { RECOMMENDED_REGION } from '~/constants/recommendedRegion';
import { getRandomResponse } from '~/utils/getRandomResponse';

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
    /** 에러바운더리를 테스트하려면 이 주석을 해제해주세요 */
    // return getRandomResponse(
    //   res(ctx.status(200), ctx.json(recommendation)),
    //   res(ctx.status(400)),
    // );

    return res(ctx.status(200), ctx.json(recommendation));
  }),
];
