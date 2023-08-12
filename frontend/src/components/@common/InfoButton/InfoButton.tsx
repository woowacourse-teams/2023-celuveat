import { useQuery } from '@tanstack/react-query';
import styled, { css } from 'styled-components';

import { getProfile } from '~/api/oauth';
import { FONT_SIZE } from '~/styles/common';

import Menu from '~/assets/icons/etc/menu.svg';
import User from '~/assets/icons/etc/user.svg';

import type { ProfileData } from '~/@types/api.types';

interface InfoButtonProps {
  isShow?: boolean;
}

function InfoButton({ isShow = false }: InfoButtonProps) {
  const { data, isSuccess } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: () => getProfile(),
  });

  return (
    <StyledInfoButton isShow={isShow}>
      <Menu />
      {isSuccess ? (
        <StyledProfileWrapper>
          {isSuccess ? (
            <>
              <StyledLoginProfile src={data?.profileImageUrl} alt="" />
              <StyledProfileText>{data?.nickname[0]}</StyledProfileText>
            </>
          ) : (
            <User />
          )}
        </StyledProfileWrapper>
      ) : (
        <User />
      )}
    </StyledInfoButton>
  );
}

export default InfoButton;

const StyledProfileWrapper = styled.div`
  position: relative;
`;

const StyledLoginProfile = styled.img`
  width: 30px;
  height: 30px;

  border-radius: 50%;
`;

const StyledProfileText = styled.span`
  position: absolute;
  top: 50%;
  left: 50%;

  color: var(--white);
  font-size: ${FONT_SIZE.sm};

  transform: translate(-50%, -50%);
`;

const StyledInfoButton = styled.button<InfoButtonProps>`
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
