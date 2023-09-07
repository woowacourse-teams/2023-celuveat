import { useEffect, memo } from 'react';
import { shallow } from 'zustand/shallow';
import { Modal } from '~/components/@common/Modal';
import PopUp from '~/components/@common/PopUp/PopUp';
import useToastState from '~/hooks/store/useToastState';
import useBooleanState from '~/hooks/useBooleanState';

function PopUpContainer() {
  const { text, isSuccess, imgUrl, isOpen, close } = useToastState(
    state => ({
      text: state.text,
      close: state.close,
      isSuccess: state.isSuccess,
      isOpen: state.isOpen,
      imgUrl: state.imgUrl,
    }),
    shallow,
  );
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);

  useEffect(() => {
    if (isOpen) {
      close();
    }
  }, []);

  return (
    <Modal open={openModal} close={closeModal} isOpen={isModalOpen}>
      {isOpen && <PopUp text={text} isSuccess={isSuccess} imgUrl={imgUrl} />}
    </Modal>
  );
}

export default memo(PopUpContainer);
