import type { Coordinate } from '~/@types/map.types';

export type Quadrant = 1 | 2 | 3 | 4;

const getQuadrant = (center: Coordinate, target: Coordinate): Quadrant => {
  const dx = target.lng - center.lng;
  const dy = target.lat - center.lat;

  if (dx > 0 && dy > 0) return 1;

  if (dx < 0 && dy > 0) return 2;

  if (dx < 0 && dy < 0) return 3;

  if (dx > 0 && dy < 0) return 4;

  return 1;
};

export default getQuadrant;
