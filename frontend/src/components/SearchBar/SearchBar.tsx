import { styled } from 'styled-components';
import SearchIcon from '../../assets/icons/search.svg';

function SearchBar() {
  return (
    <StyledContainer>
      <StyledInput placeholder="지역으로 검색하기" />
      <StyledButton type="submit">
        <SearchIcon />
      </StyledButton>
    </StyledContainer>
  );
}

export default SearchBar;

const StyledContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  width: 382px;
  height: 48px;

  padding: 1.2rem;

  border: 1px solid var(--gray-2);
  border-radius: 40px;
  background: var(--white);
  box-shadow: 0 4px 12px 0 rgb(0 0 0 / 5%), 0 1px 2px 0 rgb(0 0 0 / 8%);
`;

const StyledInput = styled.input`
  flex: 1;

  border: none;
  outline: none;

  font-size: medium;
`;

const StyledButton = styled.button`
  width: 36px;
  height: 36px;

  padding: 1.2rem;

  border: none;
  border-radius: 100%;
  background-color: var(--red-2);

  color: var(--white);
`;
