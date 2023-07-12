import { ComponentPropsWithRef, forwardRef } from 'react';
import styled from 'styled-components';
import SearchIcon from '~/assets/icons/search.svg';
import { FONT_SIZE } from '~/styles/common';

interface SearchbarInputProps extends ComponentPropsWithRef<'input'> {
  width: number;
}

const SearchbarInput = forwardRef<HTMLInputElement, SearchbarInputProps>(({ width, onChange }, ref) => (
  <StyledSearchbarInputContainer width={width}>
    <SearchIcon />
    <StyledSearchbarInput type="text" ref={ref} onChange={onChange} />
  </StyledSearchbarInputContainer>
));

export default SearchbarInput;

const StyledSearchbarInputContainer = styled.div<{ width: number }>`
  display: flex;

  width: ${({ width }) => `${width}px`};
  padding: 1rem 2.6rem;

  border: 1px solid var(--primary-6);
  border-radius: 48px;

  box-shadow: var(--shadow);
`;

const StyledSearchbarInput = styled.input`
  width: 100%;

  border: none;
  outline: none;
  background-color: transparent;

  font-size: ${FONT_SIZE.lg};

  margin-left: 1rem;
`;
