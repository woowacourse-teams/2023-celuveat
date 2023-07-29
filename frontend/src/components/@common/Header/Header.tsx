import React, { useState } from 'react';
import { styled } from 'styled-components';
import Logo from '~/assets/logo.png';
import { Modal, ModalContent } from '~/components/@common/Modal';
import InfoDropDown from '~/components/InfoDropDown';
import LoginModalContent from '~/components/LoginModalContent';

const options = [
  { id: 1, value: '로그인' },
  { id: 2, value: '회원가입' },
];

function Header() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleModalShow = (isShow: boolean) => {
    setIsModalOpen(isShow);
  };

  const handleInfoDropDown = (event: React.MouseEvent<HTMLElement>) => {
    const currentOption = event.currentTarget.dataset.name;

    if (currentOption === '로그인') handleModalShow(true);
  };

  return (
    <>
      <StyledHeader>
        <StyledLogo alt="셀럽잇 로고" src={Logo} />
        <InfoDropDown options={options} externalOnClick={handleInfoDropDown} isOpen={isModalOpen} />
      </StyledHeader>
      <Modal>
        <ModalContent isShow={isModalOpen} title="로그인 및 회원 가입" handleModalShow={handleModalShow}>
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
