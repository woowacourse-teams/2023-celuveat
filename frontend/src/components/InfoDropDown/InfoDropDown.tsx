import { styled } from 'styled-components';
import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { DropDown, Modal } from 'celuveat-ui-library';

import InfoButton from '~/components/@common/InfoButton';
import LoginModal from '~/components/LoginModal';

import { getProfile } from '~/api/user';

import type { ProfileData } from '~/@types/api.types';

function InfoDropDown() {
  const { data, isSuccess: isLogin } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: getProfile,
  });

  const navigator = useNavigate();

  const goMyPage = () => {
    navigator('/user');
  };

  const goWishList = () => {
    navigator('/restaurants/like');
  };

  const goWithdrawal = () => {
    navigator('/withdrawal');
  };

  return (
    <DropDown>
      <DropDown.Trigger isCustom>
        <StyledTriggerWrapper>
          <InfoButton profile={data} isSuccess={isLogin} />
        </StyledTriggerWrapper>
      </DropDown.Trigger>
      <DropDown.Options as="ul" isCustom>
        <StyledDropDownWrapper>
          {isLogin ? (
            <>
              <DropDown.Option as="li" isCustom onClick={goMyPage}>
                <StyledDropDownOption>마이 페이지</StyledDropDownOption>
              </DropDown.Option>
              <DropDown.Option as="li" isCustom onClick={goWishList}>
                <StyledDropDownOption>위시리스트</StyledDropDownOption>
              </DropDown.Option>
              <DropDown.Option as="li" isCustom onClick={goWithdrawal}>
                <StyledDropDownOption>회원 탈퇴</StyledDropDownOption>
              </DropDown.Option>
            </>
          ) : (
            <Modal.OpenButton modalTitle="로그인 및 회원가입" isCustom modalContent={<LoginModal />}>
              <DropDown.Option isCustom>
                <StyledDropDownOption>로그인</StyledDropDownOption>
              </DropDown.Option>
            </Modal.OpenButton>
          )}
        </StyledDropDownWrapper>
      </DropDown.Options>
    </DropDown>
  );
}

export default InfoDropDown;

const StyledTriggerWrapper = styled.div`
  border: none;
  background-color: transparent;
  box-shadow: none;
  outline: none;
`;

const StyledDropDownWrapper = styled.ul`
  display: flex;
  flex-direction: column;
  align-content: center;

  position: absolute;
  top: 64px;
  right: 0;
  z-index: 1000;

  width: 216px;

  border-radius: 10px;
  background: white;

  font-size: 1.4rem;

  box-shadow: var(--shadow);
`;

const StyledDropDownOption = styled.li`
  display: flex;
  justify-content: space-between;
  align-items: center;

  height: 44px;

  padding: 0 1rem;

  border-radius: 10px;

  &:hover {
    background: var(--gray-1);
  }
`;
