import { forwardRef } from 'react';
import styled from 'styled-components';
import type { Option } from '~/@types/utils.types';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';

interface SearchbarSelectBoxProps {
  options: Option[];
  onClickEvent: (option: Option) => () => void;
}

const SearchbarSelectBox = forwardRef<HTMLUListElement, SearchbarSelectBoxProps>(({ options, onClickEvent }, ref) => (
  <StyledSearchbarTags ref={ref}>
    {options.map(option => (
      <StyledSearchBarTag key={option.key} value={option.value} onClick={onClickEvent(option)}>
        {option.value}
      </StyledSearchBarTag>
    ))}
  </StyledSearchbarTags>
));

export default SearchbarSelectBox;

const StyledSearchbarTags = styled.ul`
  border-radius: ${BORDER_RADIUS.lg};

  width: 741px;

  padding: 0 2.1rem 0 2.1rem;

  font-size: ${FONT_SIZE.sm};

  box-shadow: var(--shadow);
`;

const StyledSearchBarTag = styled.li`
  display: block;

  padding: 1.1rem 1.1rem;

  border: none;
  background: none;
  outline: none;

  & + & {
    border-top: 1px solid #eee;
  }
`;
