import { styled } from 'styled-components';
import { shallow } from 'zustand/shallow';
import CategoryNavbar from '../CategoryNavbar';

import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';

import CelebDropDown from '~/components/CelebDropDown/CelebDropDown';

function MapPageNavBar() {
  const [setCurrentPage, setRestaurantCategory] = useRestaurantsQueryStringState(
    state => [state.setCurrentPage, state.setRestaurantCategory],
    shallow,
  );

  const clickRestaurantCategory = (e: React.MouseEvent<HTMLElement>) => {
    const currentCategory = e.currentTarget.dataset.label;

    setRestaurantCategory(currentCategory);
    setCurrentPage(0);
  };

  return (
    <StyledNavBar>
      <CelebDropDown />
      <StyledLine />
      <CategoryNavbar categories={RESTAURANT_CATEGORY} externalOnClick={clickRestaurantCategory} isInteractive />
    </StyledNavBar>
  );
}

export default MapPageNavBar;

const StyledNavBar = styled.nav`
  display: flex;
  align-items: center;

  width: 100%;
  height: 80px;

  background-color: var(--white);

  padding-left: 1.2rem;
  border-bottom: 1px solid var(--gray-1);
`;

const StyledLine = styled.div`
  margin-left: 1.2rem;

  width: 1px;
  height: 46px;

  background-color: var(--gray-3);
`;
