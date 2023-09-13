import React, { ChangeEvent } from 'react';
import styled from 'styled-components';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';

type SortingWay = 'distance' | 'like';

function FilterSelectBox() {
  const [sort, setSort] = useRestaurantsQueryStringState(state => [state.sort, state.setSort]);

  const handleSortState = (e: ChangeEvent<HTMLSelectElement>) => {
    setSort(e.target.value as SortingWay);
  };
  return (
    <StyledSelectBox onChange={handleSortState} value={sort}>
      <option value="like">인기순</option>
      <option value="distance">거리순</option>
    </StyledSelectBox>
  );
}

export default FilterSelectBox;

const StyledSelectBox = styled.select`
  width: 100px;
  height: 30px;

  border: 1px solid var(--gray-3);
  border-radius: 4px;
`;
