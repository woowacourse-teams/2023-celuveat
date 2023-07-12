import styled from 'styled-components';
import type { Option } from '~/@types/celebs.types';
import useSearchBarRef from '~/components/@common/Searchbar/hooks/useSearchbarRef';
import SearchbarSelectBox from '~/components/@common/Searchbar/SearchbarSelectBox';
import SearchbarInput from '~/components/@common/Searchbar/SearchbarInput';

interface SeachbarDropDownProps {
  options: Option[];
  setOptions: React.ChangeEventHandler<HTMLInputElement>;
  width: number;
  placeholder: string;
}

function SearchbarDropDown({ options, setOptions, width, placeholder }: SeachbarDropDownProps) {
  const { inputRef, inputDom, selectorRef } = useSearchBarRef();

  const equalOptionValueEvent = (option: Option) => () => {
    inputDom.value = option.youtubeChannelName;
  };

  return (
    <StyledSearchbarDropDown>
      <SearchbarInput ref={inputRef} placeholder={placeholder} width={width} onChange={setOptions} />
      <SearchbarSelectBox ref={selectorRef} width={width} options={options} onClickEvent={equalOptionValueEvent} />
    </StyledSearchbarDropDown>
  );
}

export default SearchbarDropDown;

const StyledSearchbarDropDown = styled.div`
  div + ul {
    margin-top: 0.7rem;
  }
`;
