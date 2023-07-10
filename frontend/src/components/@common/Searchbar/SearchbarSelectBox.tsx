import { forwardRef } from 'react';
import styled from 'styled-components';
import { Option } from '~/@types/utils.types';

interface SearchbarSelectBoxProps {
  options: Option[];
  onClickEvent: (option: Option) => () => void;
}

const SearchbarSelectBox = forwardRef<HTMLUListElement, SearchbarSelectBoxProps>(({ options, onClickEvent }, ref) => (
  <SearchbarTags ref={ref}>
    {options.map(option => (
      <SearchBarTag key={option.key} value={option.value} onClick={onClickEvent(option)}>
        {option.value}
      </SearchBarTag>
    ))}
  </SearchbarTags>
));

export default SearchbarSelectBox;

const SearchbarTags = styled.ul`
  border-radius: 16px;

  width: 741px;

  padding: 0 2.1rem 0 2.1rem;

  font-size: 1.6rem;

  box-shadow: var(--shadow);
`;

const SearchBarTag = styled.li`
  display: block;

  padding: 1.1rem 1.1rem;

  border: none;
  background: none;
  outline: none;

  & + & {
    border-top: 1px solid #eee;
  }

  &::before {
    content: '';

    display: inline-block;

    width: 3.6rem;
    height: 3.6rem;

    background: url(https://yt3.googleusercontent.com/ytc/AOPolaRP_f2uFfIoKv92EaFJC1eA-ibulSUORtzpwsHPoQ=s176-c-k-c0x00ffffff-no-rj)
      no-repeat 0px 0px;

    margin-right: 1.9rem;
    border-radius: 50%;

    vertical-align: middle;
  }
`;
