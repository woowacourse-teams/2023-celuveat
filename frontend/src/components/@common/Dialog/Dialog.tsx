import { Modal } from 'celuveat-ui-library';
import React, { ReactNode } from 'react';
import styled from 'styled-components';
import Exit from '~/assets/icons/exit.svg';
import useCeluveatModal from '~/hooks/useCeluveatModal';

interface Props {
  title?: string;
  children: ReactNode;
}

function Dialog({ title, children }: Props) {
  const { closeModal } = useCeluveatModal();

  return (
    <>
      <Modal.Overlay as={<StyledOverlay />} />
      <StyledContent>
        <StyledTitle>{title}</StyledTitle>
        <StyledExitButton onClick={closeModal} />
        {children}
      </StyledContent>
    </>
  );
}

export default Dialog;

const StyledOverlay = styled.div`
  position: absolute;
  top: 0;

  width: 100%;
  height: 100%;

  background-color: black;

  opacity: 0.2;
`;

const StyledContent = styled.div`
  position: fixed;
  top: 50%;
  left: 50vw;

  min-width: 540px;
  max-width: 66.6%;

  padding: 2.4rem;
  margin: 0 auto;

  border-radius: 12px;
  background-color: white;

  transform: translateX(-50%) translateY(-50%);
`;

const StyledTitle = styled.h4`
  text-align: center;

  margin-bottom: 2.4rem;
`;

const StyledExitButton = styled(Exit)`
  position: absolute;
  top: 12px;
  right: 12px;

  cursor: pointer;
`;
