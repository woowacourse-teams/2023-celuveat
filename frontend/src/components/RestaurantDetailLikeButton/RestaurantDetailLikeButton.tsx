import type { Restaurant } from '~/@types/restaurant.types';
import { Modal, ModalContent } from '../@common/Modal';
import useToggleLikeNotUpdate from '~/hooks/server/useToggleLikeNotUpdate';
import LoginModalContent from '../LoginModalContent';
import WhiteLove from '~/assets/icons/love.svg';

interface RestaurantDetailLikeButtonProps {
  restaurant: Restaurant;
}

function RestaurantDetailLikeButton({ restaurant }: RestaurantDetailLikeButtonProps) {
  const { isModalOpen, isLiked, closeModal, toggleRestaurantLike } = useToggleLikeNotUpdate(restaurant);

  return (
    <>
      <button type="button" onClick={toggleRestaurantLike}>
        {isLiked ? (
          <>
            <WhiteLove width={30} />
            <div>위시리스트에서 삭제하기</div>
          </>
        ) : (
          <>
            <WhiteLove width={30} fill="#fff" />
            <div>위시리스트에 저장하기</div>
          </>
        )}
      </button>
      <Modal>
        <ModalContent isShow={isModalOpen} title="로그인 및 회원 가입" closeModal={closeModal}>
          <LoginModalContent />
        </ModalContent>
      </Modal>
    </>
  );
}

export default RestaurantDetailLikeButton;
