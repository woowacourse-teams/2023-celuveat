import useRestaurantReview from '~/hooks/server/useRestaurantReview';
import TextButton from '../@common/Button';

function DeleteButton({ reviewId }: { reviewId: number }) {
  const { deleteReview } = useRestaurantReview();

  const onDeleteReview: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.preventDefault();

    deleteReview(reviewId);
    window.location.reload();
  };

  return <TextButton type="submit" onClick={onDeleteReview} colorType="dark" text="삭제하기" width="100%" />;
}

export default DeleteButton;
