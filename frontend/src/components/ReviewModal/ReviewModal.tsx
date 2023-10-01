import Modal from '~/components/@common/Modal';
import LoginModal from '~/components/LoginModal';
import RestaurantReviewList from '~/components/RestaurantReviewList';
import { ReviewForm, ReviewDeleteForm, ReviewReportForm } from '~/components/ReviewForm';

import { useReviewModalContext } from '~/hooks/context/ReviewModalProvider';
import useRestaurantReview from '~/hooks/server/useRestaurantReview';

const REVIEW_FORM_TITLE = {
  create: '리뷰 작성하기',
  update: '리뷰 수정하기',
  delete: '리뷰 삭제하기',
  report: '리뷰 신고하기',
  all: '리뷰 모두 보기',
} as const;

function ReviewModal() {
  const { restaurantReviewsData } = useRestaurantReview();
  const { formType, isModalOpen, closeModal } = useReviewModalContext();

  if (formType === null) {
    return <LoginModal isOpen={isModalOpen} close={closeModal} />;
  }

  return (
    <div>
      <Modal title={REVIEW_FORM_TITLE[formType]} isOpen={isModalOpen} close={closeModal}>
        <>
          {formType === 'create' && <ReviewForm type="create" />}
          {formType === 'update' && <ReviewForm type="update" />}
          {formType === 'delete' && <ReviewDeleteForm />}
          {formType === 'report' && <ReviewReportForm />}
          {formType === 'all' && <RestaurantReviewList reviews={restaurantReviewsData.reviews} isModal={isModalOpen} />}
        </>
      </Modal>
    </div>
  );
}

export default ReviewModal;
