import { createPortal } from 'react-dom';

interface ModalProps {
  children: React.ReactNode;
}

function Modal({ children }: ModalProps) {
  return createPortal(children, document.querySelector('#modal'));
}

export default Modal;
