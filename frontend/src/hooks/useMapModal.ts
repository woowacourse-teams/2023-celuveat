import { useState } from 'react';

const useMapModal = (initValue: boolean) => {
  const [modalOpen, setModalOpen] = useState(initValue);
  const [isVisible, setIsLoading] = useState(initValue);

  const closeModal = () => {
    setModalOpen(false);
    setTimeout(() => setIsLoading(false), 400);
  };

  const openModal = () => {
    setIsLoading(true);
    setModalOpen(true);
  };

  return { modalOpen, isVisible, closeModal, openModal };
};

export default useMapModal;
