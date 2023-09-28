import { useQueryClient } from '@tanstack/react-query';
import { createContext, useContext, useState } from 'react';
import { ProfileData } from '~/@types/api.types';
import useBooleanState from '../useBooleanState';

interface ReviewModalContextState {
  reviewId: number;
  setReviewId: React.Dispatch<number>;
  formType: string;
  isModalOpen: boolean;
  openModal: VoidFunction;
  closeModal: VoidFunction;
  clickUpdateReview: VoidFunction;
  clickDeleteReview: VoidFunction;
  clickReportReview: VoidFunction;
  openCreateReview: VoidFunction;
  openShowAll: VoidFunction;
}

const ReviewModalContext = createContext<ReviewModalContextState | null>(null);

export const useReviewModalContext = () => useContext(ReviewModalContext);

function ReviewModalProvider({ children }: { children: React.ReactNode }) {
  const qc = useQueryClient();
  const profileData: ProfileData = qc.getQueryData(['profile']);
  const [formType, setFormType] = useState('');
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);
  const [reviewId, setReviewId] = useState(null);

  // eslint-disable-next-line react/jsx-no-constructed-context-values
  const value = {
    reviewId,
    setReviewId,
    formType,
    openModal,
    isModalOpen,
    closeModal,
    clickUpdateReview: () => {
      if (!profileData) {
        setFormType('');

        openModal();
        return;
      }
      setFormType('update');
      openModal();
    },
    clickReportReview: () => {
      if (!profileData) {
        setFormType('');

        openModal();
        return;
      }
      setFormType('report');
      openModal();
    },
    clickDeleteReview: () => {
      if (!profileData) {
        setFormType('');
        openModal();
        return;
      }
      setFormType('delete');
      openModal();
    },
    openCreateReview: () => {
      if (!profileData) {
        setFormType('');
        openModal();
        return;
      }
      setFormType('create');
      openModal();
    },
    openShowAll: () => {
      setFormType('all');
      openModal();
    },
  };

  return <ReviewModalContext.Provider value={value}>{children}</ReviewModalContext.Provider>;
}

export default ReviewModalProvider;
