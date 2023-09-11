import styled, { css } from 'styled-components';
import { MouseEvent, useState } from 'react';

import CelebIcon from '~/assets/icons/celeb.svg';
import ProfileImage from '~/components/@common/ProfileImage';
import NavItem from '~/components/@common/NavItem/NavItem';
import useBooleanState from '~/hooks/useBooleanState';

import type { Celeb } from '~/@types/celeb.types';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import { OPTION_FOR_CELEB_ALL } from '~/constants/options';
import useMediaQuery from '~/hooks/useMediaQuery';

interface DropDownProps {
  celebs: Celeb[];
  isOpen?: boolean;
  externalOnClick?: (e?: MouseEvent<HTMLLIElement>) => void;
}

function CelebDropDown({ celebs, externalOnClick, isOpen = false }: DropDownProps) {
  const { isMobile } = useMediaQuery();
  const [selected, setSelected] = useState<Celeb>(OPTION_FOR_CELEB_ALL);
  const { value: isShow, toggle: onToggleDropDown, setFalse: onCloseDropDown } = useBooleanState(isOpen);

  const onSelection = (celebName: Celeb['name']) => (event?: MouseEvent<HTMLLIElement>) => {
    setSelected(celebs.find(celeb => celebName === celeb.name));

    if (externalOnClick) externalOnClick(event);
  };

  return (
    <StyledCelebDropDown aria-hidden>
      <StyledNavItemWrapper type="button" onClick={onToggleDropDown} onBlur={onCloseDropDown}>
        {selected.id === -1 ? (
          <NavItem label="전체 셀럽" icon={<CelebIcon />} isShow={isShow} />
        ) : (
          <NavItem
            label={selected.youtubeChannelName.replace('@', '')}
            icon={<ProfileImage name={selected.name} imageUrl={selected.profileImageUrl} size="40px" />}
            isShow={isShow}
          />
        )}
      </StyledNavItemWrapper>

      {isShow && (
        <StyledDropDownWrapper isMobile={isMobile}>
          <StyledSelectContainer>
            {celebs.map(({ id, name, youtubeChannelName, profileImageUrl }) => (
              <StyledDropDownOption data-id={id} onMouseDown={onSelection(name)}>
                <div>
                  {id === -1 ? <CelebIcon /> : <ProfileImage name={name} imageUrl={profileImageUrl} size="32px" />}
                  <div>
                    <StyledCelebName>{name}</StyledCelebName>
                    <StyledChannelName>{youtubeChannelName}</StyledChannelName>
                  </div>
                </div>
              </StyledDropDownOption>
            ))}
          </StyledSelectContainer>
        </StyledDropDownWrapper>
      )}
    </StyledCelebDropDown>
  );
}

export default CelebDropDown;

const StyledNavItemWrapper = styled.button`
  border: none;
  background: transparent;

  cursor: pointer;
  outline: none;
`;

const StyledCelebDropDown = styled.div`
  position: relative;
`;

const StyledDropDownWrapper = styled.ul<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;
  align-content: center;

  position: absolute;
  top: calc(100% + 16px);

  ${({ isMobile }) =>
    isMobile
      ? css`
          left: 0;

          width: 90vw;
          height: 60vh;
          max-height: 440px;
        `
      : css`
          left: 18px;

          width: 380px;
          height: 440px;
        `}

  padding: 1.2rem 0;

  border-radius: ${BORDER_RADIUS.md};
  background: var(--white);

  box-shadow: var(--shadow);
`;

const StyledSelectContainer = styled.div`
  width: 100%;
  height: 100%;

  background: transparent;

  overflow-y: auto;
`;

const StyledDropDownOption = styled.li`
  display: flex;
  justify-content: space-between;
  align-items: center;

  height: 60px;

  padding: 0 1.8rem;

  font-size: ${FONT_SIZE.md};

  cursor: pointer;

  & + & {
    border-bottom: 1px solid var(--gray-1);
  }

  &:first-child {
    border-bottom: 1px solid var(--gray-1);
  }

  & > div {
    display: flex;
    align-items: center;
    gap: 0 1.2rem;
  }

  &:hover {
    background-color: var(--gray-1);
  }
`;

const StyledCelebName = styled.div`
  font-family: SUIT-Medium;
`;

const StyledChannelName = styled.div`
  padding-top: 0.4rem;

  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;
