import type { CoordinateBoundary } from '~/@types/map.types';
import type { RestaurantCategory } from '~/@types/restaurant.types';
import { GetRestaurantsQueryParams } from '~/hooks/server/useRestaurant';

interface ParamTypes extends CoordinateBoundary {
  celebId?: string;
  category?: RestaurantCategory;
  page?: string;
  sort: 'distance' | 'like';
}

const getQueryString = ({ boundary, celebId, category, page, sort }: GetRestaurantsQueryParams) => {
  let params: ParamTypes = { ...boundary, sort };

  if (celebId !== -1) params = { ...params, celebId: String(celebId) };

  if (category !== '전체') params = { ...params, category };

  if (page !== 0) params = { ...params, page: String(page) };

  const queryString = new URLSearchParams(Object.assign(params)).toString();

  return queryString;
};

export default getQueryString;
