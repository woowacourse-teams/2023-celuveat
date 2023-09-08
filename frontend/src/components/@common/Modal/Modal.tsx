import { createContext, useContext } from 'react';
import { createPortal } from 'react-dom';

interface ContextProps {
  open: () => void;
  close: () => void;
  isOpen: boolean;
}

interface ModalProps extends ContextProps {
  children: React.ReactNode;
}

const ModalContext = createContext<ContextProps | null>(null);

export const useModalContext = () => useContext(ModalContext);

function Modal({ children, open, close, isOpen }: ModalProps) {
  return createPortal(
    <ModalContext.Provider value={{ open, close, isOpen }}>{children}</ModalContext.Provider>,
    document.querySelector('#modal'),
  );
}

export default Modal;
