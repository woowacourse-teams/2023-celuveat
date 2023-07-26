import { MouseEvent, useState } from 'react';
import styled from 'styled-components';
import NavButton from '~/components/@common/NavButton';
import isEqual from '~/utils/compare';

interface Category {
  label: string;
  icon: React.ReactNode;
}

interface CategoryProps {
  categories: Category[];
  externalOnClick?: (e?: MouseEvent<HTMLElement>) => void;
}

function CategoryNavbar({ categories, externalOnClick }: CategoryProps) {
  const [selected, setSelected] = useState(0);

  const onSelection = (value: number) => (event?: MouseEvent<HTMLElement>) => {
    setSelected(value);

    if (externalOnClick) externalOnClick(event);
  };

  return (
    <StyledCategoryNavbarWrapper>
      {categories.map(({ icon, label }, idx) => (
        <NavButton label={label} icon={icon} isShow={isEqual(selected, idx + 1)} onClick={onSelection(idx + 1)} />
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
