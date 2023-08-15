// import { useState } from 'react';
import styled from 'styled-components';

import { useState } from 'react';
import { hideScrollBar } from '~/styles/common';
import type { Celeb } from '~/@types/celeb.types';
import ProfileImage from '../@common/ProfileImage';
import { isEqual } from '~/utils/compare';
import useMediaQuery from '~/hooks/useMediaQuery';
import NavItem from '../@common/NavItem';
import CelebIcon from '~/assets/icons/celeb.svg';

interface CelebNavBarProps {
  celebs: Celeb[];
  externalOnClick?: (e?: React.MouseEvent<HTMLElement>) => void;
}

type CelebNavbarLabel = Celeb['id'] | -1;

function CelebNavbar({ celebs, externalOnClick }: CelebNavBarProps) {
  const { isMobile } = useMediaQuery();
  const [selected, setSelected] = useState<CelebNavbarLabel>(-1);

  const clickCeleb = (value: CelebNavbarLabel) => (event?: React.MouseEvent<HTMLElement>) => {
    setSelected(value);

    if (externalOnClick) externalOnClick(event);
  };

  return (
    <StyledNavBar isMobile={isMobile}>
      <StyledNavItemButton aria-label="전체" data-label={-1} type="button" onClick={clickCeleb(-1)}>
        <NavItem label="전체" icon={<CelebIcon width={28} />} isShow={isEqual(selected, -1)} />
      </StyledNavItemButton>

      <StyledLine />

      <StyledCelebNavbarWrapper>
        {celebs.map(({ id, name, youtubeChannelName, profileImageUrl }) => (
          <StyledNavItemButton key={id} aria-label={name} data-label={id} type="button" onClick={clickCeleb(id)}>
            <NavItem
              label={youtubeChannelName.replace('@', '')}
              icon={<ProfileImage name={name} imageUrl={profileImageUrl} size="40px" />}
              isShow={isEqual(selected, id)}
            />
          </StyledNavItemButton>
        ))}
      </StyledCelebNavbarWrapper>
    </StyledNavBar>
  );
}

export default CelebNavbar;

const StyledNavBar = styled.div<{ isMobile: boolean }>`
  display: flex;
  align-items: center;

  top: ${({ isMobile }) => (isMobile ? '60px' : '80px')};
  z-index: 11;

  width: 100%;
  height: 80px;

  background-color: var(--white);
  border-bottom: 1px solid var(--gray-1);
`;

const StyledLine = styled.div`
  width: 1px;
  height: 56px;

  background-color: var(--gray-3);
`;

const StyledCelebNavbarWrapper = styled.ul`
  ${hideScrollBar}
  display: flex;
  align-items: center;

  width: 100%;
  height: 100%;

  background: transparent;

  overflow-x: scroll;
`;

const StyledNavItemButton = styled.button`
  border: none;
  background: transparent;

  cursor: pointer;
  outline: none;
`;
