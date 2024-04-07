import { styled, css } from 'styled-components';

import Menu from '~/assets/icons/etc/menu.svg';
import User from '~/assets/icons/etc/user.svg';

import type { ProfileData } from '~/@types/api.types';

import ProfileImage from '~/components/@common/ProfileImage';

interface InfoButtonProps {
  isShow?: boolean;
  isSuccess: boolean;
  profile: ProfileData;
}

function InfoButton({ isShow = false, isSuccess, profile }: InfoButtonProps) {
  return (
    <StyledInfoButton isShow={isShow} aria-label="프로필">
      <Menu />
      {isSuccess ? <ProfileImage name={profile.nickname} imageUrl={profile.profileImageUrl} size="30px" /> : <User />}
    </StyledInfoButton>
  );
}

export default InfoButton;

const StyledInfoButton = styled.button<{ isShow: boolean }>`
  display: flex;
  justify-content: space-between;
  align-items: center;

  width: 77px;

  padding: 0.5rem 0.5rem 0.5rem 1.2rem;

  border: 1px solid #ddd;
  border-radius: 21px;
  background: transparent;

  cursor: pointer;

  ${({ isShow }) =>
    isShow &&
    css`
      box-shadow: var(--shadow);
    `}

  &:hover {
    box-shadow: var(--shadow);

    transition: box-shadow 0.2s ease-in-out;
  }
`;
