import { useQuery } from '@tanstack/react-query';
import { DropDown } from 'celuveat-ui-library';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import { getProfile } from '~/api/user';
import InfoButton from '~/components/@common/InfoButton';
import LoginModal from '~/components/LoginModal';
import useBooleanState from '~/hooks/useBooleanState';

import { ProfileData } from '~/@types/api.types';

function InfoDropDown() {
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);
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
    <>
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
                <DropDown.Option as="li" isCustom externalClick={goMyPage}>
                  <StyledDropDownOption>마이 페이지</StyledDropDownOption>
                </DropDown.Option>
                <DropDown.Option as="li" isCustom externalClick={goWishList}>
                  <StyledDropDownOption>위시리스트</StyledDropDownOption>
                </DropDown.Option>
                <DropDown.Option as="li" isCustom externalClick={goWithdrawal}>
                  <StyledDropDownOption>회원 탈퇴</StyledDropDownOption>
                </DropDown.Option>
              </>
            ) : (
              <DropDown.Option isCustom externalClick={openModal}>
                <StyledDropDownOption>로그인</StyledDropDownOption>
              </DropDown.Option>
            )}
          </StyledDropDownWrapper>
        </DropDown.Options>
      </DropDown>

      <LoginModal close={closeModal} isOpen={isModalOpen} />
    </>
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

  &:hover {
    background: var(--gray-1);
  }
`;
