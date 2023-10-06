import { Suspense } from 'react';
import RestaurantDetail from './RestaurantDetail';
import Skeleton from './Skeleton';

function RestaurantDetailPage() {
  return (
    <Suspense fallback={<Skeleton />}>
      <RestaurantDetail />
    </Suspense>
  );
}

export default RestaurantDetailPage;
