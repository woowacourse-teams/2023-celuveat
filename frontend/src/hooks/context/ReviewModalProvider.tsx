import { createContext, useContext, useMemo, useState } from 'react';
import { useQueryClient } from '@tanstack/react-query';

import useBooleanState from '~/hooks/useBooleanState';

import type { ProfileData } from '~/@types/api.types';
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
  const qc = useQueryClient();
  const profileData: ProfileData = qc.getQueryData(['profile']);

  const [formType, setFormType] = useState<ReviewFormType>(null);
  const [reviewId, setReviewId] = useState(null);

  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);

  const openReviewModal = (reviewFormType: ReviewFormType) => {
    if (!profileData && reviewFormType !== 'all') {
      setFormType(null);
      openModal();
      return;
    }

    setFormType(reviewFormType);
    openModal();
  };

  const value = useMemo(
    () => ({ reviewId, formType, isModalOpen, setReviewId, openModal, closeModal, openReviewModal }),
    [reviewId, formType, isModalOpen],
  );

  return <ReviewModalContext.Provider value={value}>{children}</ReviewModalContext.Provider>;
}

export default ReviewModalProvider;
