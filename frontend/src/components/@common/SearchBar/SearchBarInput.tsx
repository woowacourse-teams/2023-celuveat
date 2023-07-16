import { ComponentPropsWithRef, forwardRef } from 'react';
import styled from 'styled-components';
import SearchIcon from '~/assets/icons/search.svg';
import { FONT_SIZE } from '~/styles/common';

interface SearchBarInputProps extends ComponentPropsWithRef<'input'> {
  width: number;
}

const SearchBarInput = forwardRef<HTMLInputElement, SearchBarInputProps>(({ width, onChange }, ref) => (
  <StyledSearchBarInputContainer width={width}>
    <SearchIcon />
    <StyledSearchBarInput type="text" ref={ref} onChange={onChange} />
  </StyledSearchBarInputContainer>
));

export default SearchBarInput;

const StyledSearchBarInputContainer = styled.div<{ width: number }>`
  display: flex;

  width: ${({ width }) => `${width}px`};

  padding: 1rem 2.6rem;
  border: 1px solid var(--primary-6);
  border-radius: 48px;
  box-shadow: var(--shadow);
`;

const StyledSearchBarInput = styled.input`
  width: 100%;

  margin-left: 1rem;
  border: none;

  background-color: transparent;

  font-size: ${FONT_SIZE.lg};
  outline: none;
`;
