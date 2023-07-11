import styled from 'styled-components';
import type { Option } from '~/@types/celebs.types';
import useSearchBarRef from '~/components/@common/Searchbar/hooks/useSearchbarRef';
import SearchbarSelectBox from '~/components/@common/Searchbar/SearchbarSelectBox';
import SearchbarInput from '~/components/@common/Searchbar/SearchbarInput';

interface SeachbarDropDownProps {
  options: Option[];
  setOptions: React.ChangeEventHandler<HTMLInputElement>;
}

function SearchbarDropDown({ options, setOptions }: SeachbarDropDownProps) {
  const { inputRef, inputDom, selectorRef } = useSearchBarRef();

  const equalOptionValueEvent = (option: Option) => () => {
    inputDom.value = option.youtubeChannelName;
  };

  return (
    <StyledSearchbarDropDown>
      <SearchbarInput ref={inputRef} onChange={setOptions} />
      <SearchbarSelectBox ref={selectorRef} options={options} onClickEvent={equalOptionValueEvent} />
    </StyledSearchbarDropDown>
  );
}

const StyledSearchbarDropDown = styled.div`
  div + ul {
    margin-top: 0.7rem;
  }
`;

export default SearchbarDropDown;
