import React, { useMemo } from 'react';
import { styled } from 'styled-components';
import Logo from '~/assets/logo.png';
import { Modal, ModalContent } from '~/components/@common/Modal';
import InfoDropDown from '~/components/InfoDropDown';
import LoginModalContent from '~/components/LoginModalContent';
import { OPTION_FOR_NOT_USER, OPTION_FOR_USER } from '~/constants/options';
import useBooleanState from '~/hooks/useBooleanState';
import useLocalStorage from '~/hooks/useLocalStorage';
import { isEmptyString } from '~/utils/compare';

function Header() {
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);
  const { value: token, clearStorage } = useLocalStorage();

  const options = useMemo(() => (isEmptyString(token) ? OPTION_FOR_NOT_USER : OPTION_FOR_USER), [token]);

  const handleInfoDropDown = (event: React.MouseEvent<HTMLElement>) => {
    const currentOption = event.currentTarget.dataset.name;

    if (currentOption === '로그인') openModal();
    if (currentOption === '로그아웃') {
      clearStorage();
    }
  };

  return (
    <>
      <StyledHeader>
        <StyledLogo alt="셀럽잇 로고" src={Logo} />
        <InfoDropDown options={options} externalOnClick={handleInfoDropDown} />
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
