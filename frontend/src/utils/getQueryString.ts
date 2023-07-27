import type { CoordinateBoundary } from '~/@types/map.types';
import type { RestaurantCategory } from '~/@types/restaurant.types';

interface ParamTypes extends CoordinateBoundary {
  celebId?: string;
  category?: RestaurantCategory;
}

const getQueryString = ({
  boundary,
  celebId,
  category,
}: {
  boundary: CoordinateBoundary;
  celebId: number;
  category: RestaurantCategory;
}) => {
  let params: ParamTypes = boundary;
  if (celebId !== null) params = { ...params, celebId: String(celebId) };

  if (category !== '전체') params = { ...params, category };

  const queryString = new URLSearchParams(Object.assign(params)).toString();

  return queryString;
};

export default getQueryString;
