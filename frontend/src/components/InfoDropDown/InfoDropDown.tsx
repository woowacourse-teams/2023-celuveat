import styled from 'styled-components';
import { MouseEvent, useCallback, useState } from 'react';
import InfoButton from '~/components/@common/InfoButton';

interface Option {
  id: number;
  value: string;
}

interface DropDownProps {
  options: Option[];
  isOpen?: boolean;
  externalOnClick?: (e?: MouseEvent<HTMLLIElement>) => void;
}

function InfoDropDown({ options, externalOnClick, isOpen = false }: DropDownProps) {
  const [isShow, setIsShow] = useState(isOpen);

  const onSelection = () => (event?: MouseEvent<HTMLLIElement>) => {
    if (externalOnClick) externalOnClick(event);
  };

  const onToggleDropDown = useCallback(() => {
    setIsShow(!isShow);
  }, [isShow]);

  return (
    <StyledInfoDropDown>
      <StyledInfoButtonWrapper onClick={onToggleDropDown} onBlur={onToggleDropDown}>
        <InfoButton isShow={isShow} />
      </StyledInfoButtonWrapper>

      {isShow && (
        <StyledDropDownWrapper>
          <StyledSelectContainer>
            {options.map(({ id, value }) => (
              <StyledDropDownOption key={id} data-id={id} onMouseDown={onSelection()}>
                {value}
              </StyledDropDownOption>
            ))}
          </StyledSelectContainer>
        </StyledDropDownWrapper>
      )}
    </StyledInfoDropDown>
  );
}

export default InfoDropDown;

const StyledInfoButtonWrapper = styled.button`
  border: none;
  background: transparent;

  cursor: pointer;
  outline: none;
`;

const StyledInfoDropDown = styled.div`
  position: relative;
`;

const StyledDropDownWrapper = styled.ul`
  display: flex;
  flex-direction: column;
  align-content: center;

  position: absolute;
  top: calc(100% + 16px);
  left: 18px;

  width: 216px;
  height: 176px;

  padding: 1.8rem 0;

  border-radius: 10px;
  background: white;

  font-size: 1.4rem;

  box-shadow: var(--shadow);
`;

const StyledSelectContainer = styled.div`
  width: 100%;
  height: 150px;

  background: transparent;

  overflow-y: auto;
`;

const StyledDropDownOption = styled.li`
  display: flex;
  justify-content: space-between;
  align-items: center;

  height: 44px;

  margin: 0 1.8rem;

  cursor: pointer;

  & + & {
    border-bottom: 1px solid var(--gray-1);
  }

  &:first-child {
    border-bottom: 1px solid var(--gray-1);
  }

  & > div {
    display: flex;
    align-items: center;
    gap: 0.4rem;
  }
`;
