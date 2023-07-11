import { ComponentPropsWithRef, forwardRef } from 'react';
import styled from 'styled-components';

import SearchIcon from '~/assets/icons/search.svg';

const SearchbarInput = forwardRef<HTMLInputElement, ComponentPropsWithRef<'input'>>(({ onChange }, ref) => (
  <StyledSearchbarInputContainer>
    <StyledSeachbarIcon src={SearchIcon} alt="search-icon" />
    <StyledSearchbarInput type="text" ref={ref} onChange={onChange} />
  </StyledSearchbarInputContainer>
));

export default SearchbarInput;

const StyledSearchbarInputContainer = styled.div`
  display: flex;

  width: 761px;
  padding: 1rem 1rem;

  border: 1px solid var(--primary-6);
  border-radius: 48px;

  box-shadow: var(--shadow);
`;

const StyledSearchbarInput = styled.input`
  width: 80%;

  border: none;
  outline: none;
  background-color: transparent;

  font-size: 1.6rem;
`;

const StyledSeachbarIcon = styled.img`
  width: 2.5rem;
  height: 2.5rem;

  padding: 0 2.6rem 0 2.6rem;
`;
