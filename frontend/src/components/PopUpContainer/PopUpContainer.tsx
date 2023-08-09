import { useEffect } from 'react';
import { Modal } from '~/components/@common/Modal';
import PopUp from '~/components/@common/PopUp/PopUp';
import useToastState from '~/hooks/store/useToastState';

interface PopUpContainerProps {
  imgUrl: string;
}

function PopUpContainer({ imgUrl }: PopUpContainerProps) {
  const { text, isSuccess, isOpen, close } = useToastState(state => ({
    text: state.text,
    close: state.close,
    isSuccess: state.isSuccess,
    isOpen: state.isOpen,
  }));

  useEffect(() => {
    if (isOpen) {
      close();
    }
  }, []);

  return <Modal>{isOpen && <PopUp text={text} isSuccess={isSuccess} imgUrl={imgUrl} />}</Modal>;
}

export default PopUpContainer;
