import { useEffect, memo } from 'react';
import { shallow } from 'zustand/shallow';
import { Modal } from '~/components/@common/Modal';
import PopUp from '~/components/@common/PopUp/PopUp';
import useToastState from '~/hooks/store/useToastState';

interface PopUpContainerProps {
  isShowImg?: boolean;
}

function PopUpContainer({ isShowImg = false }: PopUpContainerProps) {
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

  useEffect(() => {
    if (isOpen) {
      close();
    }
  }, []);

  return <Modal>{isOpen && <PopUp text={text} isSuccess={isSuccess} imgUrl={imgUrl} isShowImg={isShowImg} />}</Modal>;
}

export default memo(PopUpContainer);
