import type { CoordinateBoundary } from '~/@types/map.types';

interface ParamTypes extends CoordinateBoundary {
  celebId?: string;
  category?: string;
}

const getQueryString = ({
  boundary,
  celebId,
  category,
}: {
  boundary: CoordinateBoundary;
  celebId: number;
  category: string;
}) => {
  let params: ParamTypes = boundary;
  if (celebId !== -1) params = { ...params, celebId: String(celebId) };

  if (category !== '전체') params = { ...params, category };

  const queryString = new URLSearchParams(Object.assign(params)).toString();

  return `${queryString}`;
};

export default getQueryString;
