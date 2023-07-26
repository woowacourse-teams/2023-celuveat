import { MouseEvent, useState } from 'react';
import styled from 'styled-components';
import { RestaurantCategory } from '~/@types/restaurant.types';
import NavButton from '~/components/@common/NavButton';
import isEqual from '~/utils/compare';

interface Category {
  label: RestaurantCategory;
  icon: React.ReactNode;
}

interface CategoryProps {
  categories: Category[];
  externalOnClick?: (e?: MouseEvent<HTMLElement>) => void;
}

function CategoryNavbar({ categories, externalOnClick }: CategoryProps) {
  const [selected, setSelected] = useState<RestaurantCategory>('전체');

  const clickCategory = (value: RestaurantCategory) => (event?: MouseEvent<HTMLElement>) => {
    setSelected(value);

    if (externalOnClick) externalOnClick(event);
  };

  return (
    <StyledCategoryNavbarWrapper>
      {categories.map(({ icon, label }) => (
        <NavButton
          label={label}
          icon={icon}
          data-label={label}
          isShow={isEqual(selected, label)}
          onClick={clickCategory(label)}
        />
      ))}
    </StyledCategoryNavbarWrapper>
  );
}

export default CategoryNavbar;

const StyledCategoryNavbarWrapper = styled.ul`
  display: flex;
  gap: 1.2rem;

  width: 100%;
  height: 56px;

  padding: 1.8rem 0;

  border-radius: 10px;
  background: transparent;
`;
