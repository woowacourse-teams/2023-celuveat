import useRestaurantReview from '~/hooks/server/useRestaurantReview';
import useModalState from '~/hooks/store/useModalState';

function DeleteButton() {
  const reviewId = useModalState(state => state.targetId);

  const { deleteReview } = useRestaurantReview();

  const onDeleteReview: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.preventDefault();

    deleteReview(reviewId);
  };

  return (
    <button type="submit" onClick={onDeleteReview}>
      삭제하기
    </button>
  );
}

export default DeleteButton;
