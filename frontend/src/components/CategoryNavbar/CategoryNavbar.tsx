import { useState } from 'react';
import styled from 'styled-components';
import NavItem from '~/components/@common/NavItem';
import { isEqual } from '~/utils/compare';
import All from '~/assets/icons/restaurantCategory/all.svg';
import type { RestaurantCategory } from '~/@types/restaurant.types';
import { hideScrollBar } from '~/styles/common';
import useMediaQuery from '~/hooks/useMediaQuery';

interface Category {
  label: RestaurantCategory;
  icon: React.ReactNode;
}

interface CategoryProps {
  categories: Category[];
  externalOnClick?: (e?: React.MouseEvent<HTMLElement>) => void;
}

function CategoryNavbar({ categories, externalOnClick }: CategoryProps) {
  const { isMobile } = useMediaQuery();
  const [selected, setSelected] = useState<RestaurantCategory>('전체');

  const clickCategory = (value: RestaurantCategory) => (event?: React.MouseEvent<HTMLElement>) => {
    setSelected(value);

    if (externalOnClick) externalOnClick(event);
  };

  return (
    <StyledNavBar isMobile={isMobile}>
      <StyledNavItemButton aria-label="전체" data-label="전체" type="button" onClick={clickCategory('전체')}>
        <NavItem label="전체" icon={<All width={28} />} isShow={isEqual(selected, '전체')} />
      </StyledNavItemButton>

      <StyledLine />
      <StyledCategoryNavbarWrapper>
        {categories.map(({ icon, label }) => (
          <StyledNavItemButton aria-label={label} data-label={label} type="button" onClick={clickCategory(label)}>
            <NavItem label={label} icon={icon} isShow={isEqual(selected, label)} />
          </StyledNavItemButton>
        ))}
      </StyledCategoryNavbarWrapper>
    </StyledNavBar>
  );
}

export default CategoryNavbar;

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

const StyledCategoryNavbarWrapper = styled.ul`
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
