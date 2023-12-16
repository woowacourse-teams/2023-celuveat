import { useQuery } from '@tanstack/react-query';
import useToggleLikeNotUpdate from '~/hooks/server/useToggleLikeNotUpdate';
import WhiteLove from '~/assets/icons/love.svg';
import { RestaurantData } from '~/@types/api.types';
import { getRestaurantDetail } from '~/api/restaurant';

interface RestaurantDetailLikeButtonProps {
  showText?: boolean;
  restaurantId: string;
  celebId: string;
}

function RestaurantDetailLikeButton({ showText = true, restaurantId, celebId }: RestaurantDetailLikeButtonProps) {
  const {
    data: { celebs, ...restaurant },
  } = useQuery<RestaurantData>({
    queryKey: ['restaurantDetail', restaurantId, celebId],
    queryFn: async () => getRestaurantDetail(restaurantId, celebId),
    suspense: true,
  });

  const { isLiked, toggleRestaurantLike } = useToggleLikeNotUpdate(restaurant);

  return (
    <button type="button" onClick={toggleRestaurantLike}>
      {isLiked ? (
        <>
          <WhiteLove width={30} fill="#fff" />
          {showText && <div>위시리스트에서 삭제하기</div>}
        </>
      ) : (
        <>
          <WhiteLove width={30} />
          {showText && <div>위시리스트에 저장하기</div>}
        </>
      )}
    </button>
  );
}

export default RestaurantDetailLikeButton;
