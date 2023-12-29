import Dialog from '../@common/Dialog';
import RestaurantReviewList from '../RestaurantReviewList/RestaurantReviewList';
import { ReviewDeleteForm, ReviewForm, ReviewReportForm } from '../ReviewForm';

const REVIEW_FORM_TITLE = {
  create: '리뷰 작성하기',
  update: '리뷰 수정하기',
  delete: '리뷰 삭제하기',
  report: '리뷰 신고하기',
  all: '리뷰 모두 보기',
} as const;

interface FormModalProps {
  type: keyof typeof REVIEW_FORM_TITLE;
  reviewId?: number;
}

function FormModal({ type, reviewId }: FormModalProps) {
  return (
    <div>
      {type === 'create' && (
        <Dialog title={REVIEW_FORM_TITLE[type]}>
          <ReviewForm type="create" reviewId={reviewId} />
        </Dialog>
      )}
      {type === 'update' && (
        <Dialog title={REVIEW_FORM_TITLE[type]}>
          <ReviewForm type="update" reviewId={reviewId} />
        </Dialog>
      )}
      {type === 'delete' && (
        <Dialog title={REVIEW_FORM_TITLE[type]}>
          <ReviewDeleteForm reviewId={reviewId} />
        </Dialog>
      )}
      {type === 'report' && (
        <Dialog title={REVIEW_FORM_TITLE[type]}>
          <ReviewReportForm reviewId={reviewId} />
        </Dialog>
      )}
      {type === 'all' && (
        <Dialog title={REVIEW_FORM_TITLE[type]}>
          <RestaurantReviewList />
        </Dialog>
      )}
    </div>
  );
}

export default FormModal;
