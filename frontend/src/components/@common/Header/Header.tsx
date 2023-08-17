import React, { useMemo } from 'react';
import { styled } from 'styled-components';
import { Link, useNavigate } from 'react-router-dom';
import { Wrapper } from '@googlemaps/react-wrapper';
import Logo from '~/assets/icons/logo.svg';
import { Modal, ModalContent } from '~/components/@common/Modal';
import InfoDropDown from '~/components/InfoDropDown';
import LoginModalContent from '~/components/LoginModalContent';
import { OPTION_FOR_NOT_USER, OPTION_FOR_USER } from '~/constants/options';
import useTokenStore from '~/hooks/store/useTokenState';
import useBooleanState from '~/hooks/useBooleanState';
import { isEmptyString } from '~/utils/compare';
import useMediaQuery from '~/hooks/useMediaQuery';
import SearchBar from '~/components/SearchBar';
import { getLogout } from '~/api/oauth';

function Header() {
  const { isMobile } = useMediaQuery();
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);
  const navigator = useNavigate();
  const [token, clearToken, oauth] = useTokenStore(state => [state.token, state.clearToken, state.oauth]);

  const options = useMemo(() => (isEmptyString(token) ? OPTION_FOR_NOT_USER : OPTION_FOR_USER), [token]);

  const handleInfoDropDown = (event: React.MouseEvent<HTMLElement>) => {
    const currentOption = event.currentTarget.dataset.name;

    if (currentOption === '로그인') openModal();

    if (currentOption === '위시리스트') navigator('/restaurants/like');

    if (currentOption === '회원 탈퇴') navigator('/withdrawal');

    if (currentOption === '로그아웃') {
      if (oauth !== '') {
        getLogout(oauth);
      }

      clearToken();
      window.location.href = '/';
    }
  };

  return (
    <>
      <StyledHeader isMobile={isMobile}>
        <Link aria-label="셀럽잇 홈페이지" role="button" to="/">
          <Logo width={136} />
        </Link>
        {!isMobile && (
          <Wrapper apiKey={process.env.GOOGLE_MAP_API_KEY} language="ko" libraries={['places']}>
            <SearchBar />
          </Wrapper>
        )}
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
