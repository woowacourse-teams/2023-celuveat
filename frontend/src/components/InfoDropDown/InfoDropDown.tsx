import styled from 'styled-components';

import InfoButton from '~/components/@common/InfoButton';
import InfoDropDownOption from '~/components/InfoDropDown/InfoDropDownOption';
import useBooleanState from '~/hooks/useBooleanState';

interface Option {
  id: number;
  value: string;
}

interface DropDownProps {
  options: Option[];
  isOpen?: boolean;
  externalOnClick?: (e?: React.MouseEvent<HTMLElement>) => void;
  label: string;
}

function InfoDropDown({ options, externalOnClick, isOpen = false, label }: DropDownProps) {
  const { value: isShow, toggle: onToggleDropDown, setFalse: onCloseDropDown } = useBooleanState(isOpen);

  const onSelection = () => (event?: React.MouseEvent<HTMLLIElement>) => {
    if (externalOnClick) externalOnClick(event);
  };

  return (
    <StyledInfoDropDown role="button" aria-label={label}>
      <StyledInfoButtonWrapper onClick={onToggleDropDown} onBlur={onCloseDropDown}>
        <InfoButton isShow={isShow} />
      </StyledInfoButtonWrapper>

      {isShow && (
        <StyledDropDownWrapper>
          <StyledSelectContainer>
            {options.map(({ id, value }) => (
              <InfoDropDownOption key={id} value={value} onClick={onSelection()} />
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
  display: relative;
`;

const StyledDropDownWrapper = styled.ul`
  display: flex;
  flex-direction: column;
  align-content: center;

  position: absolute;
  top: calc(100% - 8px);
  right: 18px;
  z-index: 1000;

  width: 216px;

  border-radius: 10px;
  background: white;

  font-size: 1.4rem;

  box-shadow: var(--shadow);
`;

const StyledSelectContainer = styled.div`
  width: 100%;
  height: fit-content;

  margin: 1rem 0;

  background: transparent;

  overflow-y: auto;
`;
