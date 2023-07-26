import styled from 'styled-components';
import { MouseEvent, useCallback, useState } from 'react';
import NavButton from '~/components/@common/NavButton';

import FastFoodIcon from '~/assets/icons/fastFood.svg';
import SearchIcon from '~/assets/icons/search.svg';
import isEqual from '~/utils/compare';

interface Option {
  value: number;
  label: string;
}

interface DropDownProps {
  options: Option[];
  isOpen: boolean;
  externalOnClick?: (e?: MouseEvent<HTMLLIElement>) => void;
}

function CelebDropDown({ options, externalOnClick, isOpen = false }: DropDownProps) {
  const [selected, setSelected] = useState(0);
  const [isShow, setIsShow] = useState(isOpen);

  const onSelection = (value: number) => (event?: MouseEvent<HTMLLIElement>) => {
    setSelected(value);

    if (externalOnClick) externalOnClick(event);
  };

  const onToggleDropDown = useCallback(() => {
    setIsShow(!isShow);
  }, [isShow]);

  return (
    <>
      <NavButton label="셀럽" icon={<FastFoodIcon />} onClick={onToggleDropDown} isShow={isShow} />

      {isShow && (
        <StyledDropDownWrapper>
          <StyledSelectContainer>
            {options.map(({ label }, idx) => (
              <StyledDropDownOption value={idx + 1} data-label={label} onClick={onSelection(idx + 1)}>
                {isEqual(selected, idx + 1) && <SearchIcon />}
                {label}
              </StyledDropDownOption>
            ))}
          </StyledSelectContainer>
        </StyledDropDownWrapper>
      )}
    </>
  );
}

export default CelebDropDown;

const StyledDropDownWrapper = styled.ul`
  display: flex;
  flex-direction: column;
  align-content: center;

  width: 216px;
  height: 176px;

  padding: 1.8rem 0;

  border-radius: 10px;
  background: transparent;

  box-shadow: 0 1px 2px rgb(0 0 0 / 15%);
`;

const StyledSelectContainer = styled.div`
  width: 100%;
  height: 150px;

  background: transparent;

  overflow-y: auto;
`;

const StyledDropDownOption = styled.li`
  display: flex;
  align-items: center;

  height: 44px;

  padding: 0 1.8rem;

  cursor: pointer;
`;
