import React from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import CategoryNavbar from '~/components/CategoryNavbar';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';

function CategorySection() {
  const navigate = useNavigate();

  const clickRestaurantCategory = (e: React.MouseEvent<HTMLElement>) => {
    const currentCategory = e.currentTarget.dataset.label;

    navigate(`/category/${currentCategory}`);
  };

  return (
    <section>
      <StyledTitle>카테고리</StyledTitle>
      <StyledCategoryBox>
        <CategoryNavbar
          categories={RESTAURANT_CATEGORY}
          externalOnClick={clickRestaurantCategory}
          includeAll={false}
          grid
        />
      </StyledCategoryBox>
    </section>
  );
}

export default CategorySection;

const StyledTitle = styled.h5`
  margin-left: 1.6rem;
`;

const StyledCategoryBox = styled.div`
  padding: 1.6rem 0.8rem;
`;
