import { rest } from 'msw';
import { region } from '~/mocks/data/region';
import { RECOMMENDED_REGION } from '~/constants/recommendedRegion';
import restaurants from '~/mocks/data/restaurants';

export const regionPageHandler = [
  rest.get('/main-page/region', (req, res, ctx) => {
    const queryParams = req.url.searchParams;
    const regionKey = queryParams.get('codes');
    const page = Number(queryParams.get('page'));

    const searchRegionCode = regionKey.split(',').map(code => Number(code));
    let resultRegion: keyof typeof RECOMMENDED_REGION | null = null;

    Object.entries(RECOMMENDED_REGION).forEach(([region, regionData]) => {
      if (regionData.code[0] === searchRegionCode[0] && regionData.code[1] === searchRegionCode[1]) {
        resultRegion = region as keyof typeof RECOMMENDED_REGION;
        return;
      }
    });

    if (!resultRegion) {
      return res(ctx.status(500));
    }

    const filteredRegionData = restaurants.filter(restaurant => {
      return region[resultRegion][0].includes(restaurant.id);
    });

    const pageData = {
      content: filteredRegionData,
      currentElementsCount: filteredRegionData.length,
      currentPage: page,
      totalElementsCount: 18,
      pageSize: 18,
      totalPage: Math.floor(18 / filteredRegionData.length) + 1,
    };

    return res(ctx.status(200), ctx.json(pageData));
  }),
];
