import { createContext, useContext, useMemo, useState } from 'react';

import useBooleanState from '~/hooks/useBooleanState';
import useCheckLogin from '~/hooks/server/useCheckLogin';

import type { ReviewFormType } from '~/@types/review.types';

interface ReviewModalContextState {
  reviewId: number;
  setReviewId: React.Dispatch<number>;
  formType: ReviewFormType;
  isModalOpen: boolean;
  openModal: VoidFunction;
  closeModal: VoidFunction;
  openReviewModal: (reviewFormType: ReviewFormType) => void;
}

const ReviewModalContext = createContext<ReviewModalContextState | null>(null);

export const useReviewModalContext = () => useContext(ReviewModalContext);

interface ReviewModalProviderProps {
  children: React.ReactNode;
}

function ReviewModalProvider({ children }: ReviewModalProviderProps) {
  const { isLogin } = useCheckLogin();

  const [formType, setFormType] = useState<ReviewFormType>(null);
  const [reviewId, setReviewId] = useState(null);

  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);

  const openReviewModal = (reviewFormType: ReviewFormType) => {
    if (!isLogin && reviewFormType !== 'all') {
      setFormType(null);
      openModal();
      return;
    }

    setFormType(reviewFormType);
    openModal();
  };

  const value = useMemo(
    () => ({ reviewId, formType, isModalOpen, setReviewId, openModal, closeModal, openReviewModal }),
    [reviewId, formType, isModalOpen, openReviewModal, setReviewId, openModal, closeModal],
  );

  return <ReviewModalContext.Provider value={value}>{children}</ReviewModalContext.Provider>;
}

export default ReviewModalProvider;
