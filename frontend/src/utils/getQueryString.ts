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

  if (celebId !== -1 && celebId) params = { ...params, celebId: String(celebId) };
  if (category !== '전체' && category) params = { ...params, category };
  if (page !== 0 && page) params = { ...params, page: String(page) };

  const searchParams = new URLSearchParams(Object.assign(params));

  return searchParams.toString();
};

const getQuerySting = (target: string | string[][] | Record<string, string> | URLSearchParams) => {
  const searchParams = new URLSearchParams(target);
  const result = searchParams.toString();

  const hasQuery = result.length > 0;

  return hasQuery ? `?${searchParams.toString()}` : '';
};

export const getUrlStringWithQuery = (url: string) => `${url}${getQuerySting(window.location.search)}`;
