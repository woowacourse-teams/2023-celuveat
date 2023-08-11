import React, { useMemo } from 'react';
import { styled } from 'styled-components';
import { Link } from 'react-router-dom';
import Logo from '~/assets/icons/logo.svg';
import { Modal, ModalContent } from '~/components/@common/Modal';
import InfoDropDown from '~/components/InfoDropDown';
import LoginModalContent from '~/components/LoginModalContent';
import { OPTION_FOR_NOT_USER, OPTION_FOR_USER } from '~/constants/options';
import useTokenStore from '~/hooks/store/useTokenState';
import useBooleanState from '~/hooks/useBooleanState';
import { isEmptyString } from '~/utils/compare';
import useMediaQuery from '~/hooks/useMediaQuery';

function Header() {
  const { isMobile } = useMediaQuery();
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);

  const token = useTokenStore(state => state.token);
  const clearToken = useTokenStore(state => state.clearToken);

  const options = useMemo(() => (isEmptyString(token) ? OPTION_FOR_NOT_USER : OPTION_FOR_USER), [token]);

  const handleInfoDropDown = (event: React.MouseEvent<HTMLElement>) => {
    const currentOption = event.currentTarget.dataset.name;

    if (currentOption === '로그인') openModal();
    if (currentOption === '로그아웃') {
      clearToken();
    }
  };

  return (
    <>
      <StyledHeader isMobile={isMobile}>
        <Link to="/">
          <Logo width={124} />
        </Link>
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

const StyledHeader = styled.header<{ isMobile: boolean }>`
  display: flex;
  justify-content: space-between;
  align-items: center;

  position: ${({ isMobile }) => (isMobile ? 'fixed' : 'sticky')};
  top: 0;
  z-index: 20;

  width: 100%;
  height: ${({ isMobile }) => (isMobile ? '60px' : '80px')};

  padding: 1.2rem 2.4rem;

  background-color: var(--white);
  border-bottom: 1px solid var(--gray-1);
`;
