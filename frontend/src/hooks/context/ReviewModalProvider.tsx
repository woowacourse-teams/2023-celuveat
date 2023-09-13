import { createContext, useContext, useMemo, useState } from 'react';
import useBooleanState from '../useBooleanState';

interface ReviewModalContextState {
  formType: string;
  isModalOpen: boolean;
  openModal: VoidFunction;
  closeModal: VoidFunction;
  clickUpdateReview: VoidFunction;
  clickDeleteReview: VoidFunction;
  openCreateReview: VoidFunction;
  openShowAll: VoidFunction;
}

const ReviewModalContext = createContext<ReviewModalContextState | null>(null);

export const useReviewModalContext = () => useContext(ReviewModalContext);

function ReviewModalProvider({ children }: { children: React.ReactNode }) {
  const [formType, setFormType] = useState('');
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);

  const value = useMemo(
    () => ({
      formType,
      openModal,
      isModalOpen,
      closeModal,
      clickUpdateReview: () => {
        setFormType('update');
        openModal();
      },
      clickDeleteReview: () => {
        setFormType('delete');
        openModal();
      },
      openCreateReview: () => {
        setFormType('create');
        openModal();
      },
      openShowAll: () => {
        setFormType('all');
        openModal();
      },
    }),
    [formType, isModalOpen],
  );

  return <ReviewModalContext.Provider value={value}>{children}</ReviewModalContext.Provider>;
}

export default ReviewModalProvider;
