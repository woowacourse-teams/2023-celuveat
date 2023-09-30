import { styled } from 'styled-components';
import { useQuery } from '@tanstack/react-query';
import { shallow } from 'zustand/shallow';
import { OPTION_FOR_CELEB_ALL } from '~/constants/options';
import CategoryNavbar from '../CategoryNavbar';
import CelebDropDown from '../CelebDropDown/CelebDropDown';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';

import type { RestaurantCategory } from '~/@types/restaurant.types';
import { getCelebs } from '~/api/celeb';

function MainPageNavBar() {
  const [setCelebId, setCurrentPage, setRestaurantCategory] = useRestaurantsQueryStringState(
    state => [state.setCelebId, state.setCurrentPage, state.setRestaurantCategory],
    shallow,
  );

  const { data: celebOptions } = useQuery({
    queryKey: ['celebOptions'],
    queryFn: () => getCelebs(),
    suspense: true,
  });

  const clickRestaurantCategory = (e: React.MouseEvent<HTMLElement>) => {
    const currentCategory = e.currentTarget.dataset.label as RestaurantCategory;

    setRestaurantCategory(currentCategory);
    setCurrentPage(0);
  };

  const clickCeleb = (e: React.MouseEvent<HTMLElement>) => {
    const currentCelebId = Number(e.currentTarget.dataset.id);

    setCelebId(currentCelebId);
    setCurrentPage(0);
  };

  return (
    <StyledNavBar>
      <CelebDropDown celebs={[OPTION_FOR_CELEB_ALL, ...celebOptions]} externalOnClick={clickCeleb} />
      <StyledLine />
      <CategoryNavbar categories={Object.values(RESTAURANT_CATEGORY)} externalOnClick={clickRestaurantCategory} />
    </StyledNavBar>
  );
}

export default MainPageNavBar;

const StyledNavBar = styled.nav`
  display: flex;
  align-items: center;

  position: sticky;
  top: 80px;
  z-index: 11;

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
