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

  const queryString = new URLSearchParams(Object.assign(params)).toString();

  return queryString;
};

const getQuerySting = () => {
  const result: string[] = [];
  const searchParams = new URLSearchParams(window.location.search);

  searchParams.forEach((value, key) => {
    result.push(`${key}=${value}`);
  });

  return result.length > 0 ? `?${result.join('&')}` : '';
};

export const getUrlStringWithQuery = (url: string) => `${url}${getQuerySting()}`;
