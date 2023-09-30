import useToggleLikeNotUpdate from '~/hooks/server/useToggleLikeNotUpdate';
import LoginModal from '~/components/LoginModal';
import WhiteLove from '~/assets/icons/love.svg';
import type { Restaurant } from '~/@types/restaurant.types';

interface RestaurantDetailLikeButtonProps {
  restaurant: Restaurant;
  showText?: boolean;
}

function RestaurantDetailLikeButton({ restaurant, showText = true }: RestaurantDetailLikeButtonProps) {
  const { isModalOpen, isLiked, closeModal, toggleRestaurantLike } = useToggleLikeNotUpdate(restaurant);

  return (
    <>
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

      <LoginModal isOpen={isModalOpen} close={closeModal} />
    </>
  );
}

export default RestaurantDetailLikeButton;
