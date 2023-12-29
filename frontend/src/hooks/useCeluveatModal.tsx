import { useModal } from 'celuveat-ui-library';
import SuggestionForm from '~/components/Form/SuggestionForm';
import FormModal from '~/components/FormModal/FormModal';
import LoginModal from '~/components/LoginModal';

const REVIEW_FORM_TITLE = {
  create: '리뷰 작성하기',
  update: '리뷰 수정하기',
  delete: '리뷰 삭제하기',
  report: '리뷰 신고하기',
  all: '리뷰 모두 보기',
} as const;

const useCeluveatModal = () => {
  const { openModal, closeModal } = useModal();

  const openLoginModal = () => {
    openModal({ content: <LoginModal /> });
  };

  const openReviewFormModal = ({ type, reviewId }: { type: keyof typeof REVIEW_FORM_TITLE; reviewId?: number }) => {
    openModal({ content: <FormModal type={type} reviewId={reviewId} /> });
  };

  const openSuggestionModal = () => {
    openModal({ content: <SuggestionForm /> });
  };

  return { openLoginModal, openReviewFormModal, openSuggestionModal, closeModal };
};

export default useCeluveatModal;
