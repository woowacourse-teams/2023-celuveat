import React from 'react';
import { Modal } from '~/components/@common/Modal';
import PopUp from '~/components/@common/PopUp/PopUp';
import useToastState from '~/hooks/store/useToastState';

interface PopUpContainerProps {
  imgUrl: string;
}

function PopUpContainer({ imgUrl }: PopUpContainerProps) {
  const { text, isSuccess, isOpen } = useToastState(state => ({
    text: state.text,
    isSuccess: state.isSuccess,
    isOpen: state.isOpen,
  }));

  return <Modal>{isOpen && <PopUp text={text} isSuccess={isSuccess} imgUrl={imgUrl} />}</Modal>;
}

export default React.memo(PopUpContainer);
