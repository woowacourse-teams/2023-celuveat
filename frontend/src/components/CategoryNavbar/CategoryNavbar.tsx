import { useState } from 'react';
import { css, styled } from 'styled-components';
import NavItem from '~/components/@common/NavItem/NavItem';

import type { RestaurantCategory } from '~/@types/restaurant.types';
import { hideScrollBar } from '~/styles/common';

interface Category {
  label: RestaurantCategory;
  icon: React.ReactNode;
}

interface CategoryProps {
  categories: Category[];
  externalOnClick?: (e?: React.MouseEvent<HTMLElement>) => void;
  includeAll?: boolean;
  grid?: boolean;
}

function CategoryNavbar({ categories, externalOnClick, includeAll = true, grid = false }: CategoryProps) {
  const [selected, setSelected] = useState<RestaurantCategory>('전체');

  const clickCategory = (value: RestaurantCategory) => (event?: React.MouseEvent<HTMLElement>) => {
    setSelected(value);

    if (externalOnClick) externalOnClick(event);
  };

  return (
    <StyledCategoryNavbarWrapper grid={grid} aria-hidden>
      {categories.map(({ icon, label }) => {
        if (!includeAll && label === '전체') return null;
        return (
          <StyledNavItemButton aria-label={label} data-label={label} type="button" onClick={clickCategory(label)}>
            <NavItem label={label} icon={icon} isShow={selected === label} />
          </StyledNavItemButton>
        );
      })}
    </StyledCategoryNavbarWrapper>
  );
}

export default CategoryNavbar;

const StyledCategoryNavbarWrapper = styled.ul<{ grid: boolean }>`
  ${hideScrollBar}
  width: 100%;
  height: 100%;

  background: transparent;

  overflow-x: scroll;

  ${({ grid }) =>
    grid
      ? css`
          display: grid;
          grid-template-columns: repeat(auto-fill, minmax(64px, auto));
        `
      : css`
          display: flex;
          align-items: center;
        `}
`;

const StyledNavItemButton = styled.button`
  border: none;
  background: transparent;

  cursor: pointer;
  outline: none;
`;
