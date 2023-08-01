import React from 'react';
import { styled } from 'styled-components';
import Logo from '~/assets/logo.png';
import { Modal, ModalContent } from '~/components/@common/Modal';
import InfoDropDown from '~/components/InfoDropDown';
import LoginModalContent from '~/components/LoginModalContent';
import useBooleanState from '~/hooks/useBooleanState';

const options = [
  { id: 1, value: '로그인' },
  { id: 2, value: '회원가입' },
];

function Header() {
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);

  const handleInfoDropDown = (event: React.MouseEvent<HTMLElement>) => {
    const currentOption = event.currentTarget.dataset.name;

    if (currentOption === '로그인') openModal();
  };

  return (
    <>
      <StyledHeader>
        <StyledLogo alt="홈" src={Logo} role="button" />
        <InfoDropDown options={options} externalOnClick={handleInfoDropDown} isOpen={isModalOpen} label="로그인" />
      </StyledHeader>
      <Modal>
        <ModalContent isShow={isModalOpen} title="로그인 및 회원 가입" closeModal={closeModal}>
          <LoginModalContent />
        </ModalContent>
      </Modal>
    </>
  );
}

export default Header;

const StyledHeader = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;

  position: sticky;
  top: 0;
  z-index: 20;

  width: 100%;
  height: 80px;

  padding: 1.2rem 2.4rem;

  background-color: var(--white);
  border-bottom: 1px solid var(--gray-1);
`;

const StyledLogo = styled.img`
  width: 136px;
`;
