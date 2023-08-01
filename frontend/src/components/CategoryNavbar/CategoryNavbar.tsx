import { useState } from 'react';
import styled from 'styled-components';
import NavItem from '~/components/@common/NavButton/NavButton';
import { isEqual } from '~/utils/compare';

import type { RestaurantCategory } from '~/@types/restaurant.types';

interface Category {
  label: RestaurantCategory;
  icon: React.ReactNode;
}

interface CategoryProps {
  categories: Category[];
  externalOnClick?: (e?: React.MouseEvent<HTMLElement>) => void;
}

function CategoryNavbar({ categories, externalOnClick }: CategoryProps) {
  const [selected, setSelected] = useState<RestaurantCategory>('전체');

  const clickCategory = (value: RestaurantCategory) => (event?: React.MouseEvent<HTMLElement>) => {
    setSelected(value);

    if (externalOnClick) externalOnClick(event);
  };

  return (
    <StyledCategoryNavbarWrapper>
      {categories.map(({ icon, label }) => (
        <StyledNavItemButton aria-label={label} data-label={label} type="button" onClick={clickCategory(label)}>
          <NavItem label={label} icon={icon} isShow={isEqual(selected, label)} />
        </StyledNavItemButton>
      ))}
    </StyledCategoryNavbarWrapper>
  );
}

export default CategoryNavbar;

const StyledCategoryNavbarWrapper = styled.ul`
  display: flex;
  align-items: center;

  background: transparent;
`;

const StyledNavItemButton = styled.button`
  border: none;
  background: transparent;

  cursor: pointer;
  outline: none;
`;
