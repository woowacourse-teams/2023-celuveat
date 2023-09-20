import { cloneElement } from 'react';
import { createPortal } from 'react-dom';

interface PortalProps {
  children: React.ReactElement;
  close: VoidFunction;
  isOpen: boolean;
}

function Portal({ children, close, isOpen }: PortalProps) {
  return isOpen && createPortal(cloneElement(children, { close }), document.querySelector('#modal'));
}

export default Portal;
