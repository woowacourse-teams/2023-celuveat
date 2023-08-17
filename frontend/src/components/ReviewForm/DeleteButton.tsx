import useRestaurantReview from '~/hooks/server/useRestaurantReview';
import useModalState from '~/hooks/store/useModalState';
import TextButton from '../@common/Button';

function DeleteButton() {
  const reviewId = useModalState(state => state.targetId);

  const { deleteReview } = useRestaurantReview();

  const onDeleteReview: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.preventDefault();

    deleteReview(reviewId);
    window.location.reload();
  };

  return <TextButton type="submit" onClick={onDeleteReview} colorType="dark" text="삭제하기" width="100%" />;
}

export default DeleteButton;
