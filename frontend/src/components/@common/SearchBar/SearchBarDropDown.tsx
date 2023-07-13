import styled from 'styled-components';
import type { CelebsSearchBarOptionType } from '~/@types/celebs.types';
import useSearchBarRef from '~/components/@common/SearchBar/hooks/useSearchBarRef';
import SearchBarSelectBox from '~/components/@common/SearchBar/SearchBarSelectBox';
import SearchBarInput from '~/components/@common/SearchBar/SearchBarInput';

interface SearchBarDropDownProps {
  options: CelebsSearchBarOptionType[];
  setOptions: React.ChangeEventHandler<HTMLInputElement>;
  width: number;
  placeholder: string;
}

function SearchBarDropDown({ options, setOptions, width, placeholder }: SearchBarDropDownProps) {
  const { inputRef, inputDom, selectorRef } = useSearchBarRef();

  const equalOptionValueEvent = (option: CelebsSearchBarOptionType) => () => {
    inputDom.value = option.youtubeChannelName;
  };

  return (
    <StyledSearchBarDropDown>
      <SearchBarInput ref={inputRef} placeholder={placeholder} width={width} onChange={setOptions} />
      <SearchBarSelectBox ref={selectorRef} width={width} options={options} onClickEvent={equalOptionValueEvent} />
    </StyledSearchBarDropDown>
  );
}

export default SearchBarDropDown;

const StyledSearchBarDropDown = styled.div`
  div + ul {
    margin-top: 0.7rem;
  }
`;
