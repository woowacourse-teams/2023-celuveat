import { FetchNextPageOptions, InfiniteData, InfiniteQueryObserverResult } from '@tanstack/react-query';
import { useEffect } from 'react';
import { RestaurantListData } from '~/@types/api.types';

interface UseInfiniteScrollProps {
  fetchNextPage: (options?: FetchNextPageOptions) => Promise<InfiniteQueryObserverResult<RestaurantListData, unknown>>;
  isFetchingNextPage: boolean;
  restaurantDataPages: InfiniteData<RestaurantListData>;
}

const useInfiniteScroll = ({ isFetchingNextPage, fetchNextPage, restaurantDataPages }: UseInfiniteScrollProps) => {
  useEffect(() => {
    window.addEventListener('scroll', () => {
      const scrollPosition = window.pageYOffset + window.innerHeight;
      const documentHeight = document.body.offsetHeight;

      if (scrollPosition >= documentHeight && !isFetchingNextPage) fetchNextPage();
    });
  }, [restaurantDataPages]);
};

export default useInfiniteScroll;
