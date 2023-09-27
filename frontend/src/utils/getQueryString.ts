import type { CoordinateBoundary } from '~/@types/map.types';
import type { RestaurantCategory, RestaurantsQueryParams } from '~/@types/restaurant.types';

interface ParamTypes extends CoordinateBoundary {
  celebId?: string;
  category?: RestaurantCategory;
  page?: string;
  sort: 'distance' | 'like';
}

export const getRestaurantQueryString = ({ boundary, celebId, category, page, sort }: RestaurantsQueryParams) => {
  let params: ParamTypes = { ...boundary, sort };

  if (celebId !== -1) params = { ...params, celebId: String(celebId) };
  if (category !== '전체') params = { ...params, category };
  if (page !== 0) params = { ...params, page: String(page) };

  return getQuerySting(Object.assign(params));
};

const getQuerySting = (target: string | string[][] | Record<string, string> | URLSearchParams) => {
  const searchParams = new URLSearchParams(target);
  return searchParams.toString();
};

export const getUrlStringWithQuery = (url: string) => `${url}${getQuerySting(window.location.search)}`;
