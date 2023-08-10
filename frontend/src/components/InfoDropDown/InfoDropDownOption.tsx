import styled, { css } from 'styled-components';
import useBooleanState from '~/hooks/useBooleanState';

interface InfoDropDownOptionProps {
  value: string;
  onClick: React.MouseEventHandler;
}

function InfoDropDownOption({ value, onClick }: InfoDropDownOptionProps) {
  const { value: isHover, setTrue: onHover, setFalse: onNotHover } = useBooleanState(false);

  return (
    <StyledDropDownOption
      isHover={isHover}
      data-name={value}
      onMouseDown={onClick}
      onMouseEnter={onHover}
      onMouseLeave={onNotHover}
    >
      {value}
    </StyledDropDownOption>
  );
}

export default InfoDropDownOption;

const StyledDropDownOption = styled.li<{ isHover: boolean }>`
  display: flex;
  justify-content: space-between;
  align-items: center;

  height: 44px;

  padding: 0 1rem;

  ${({ isHover }) =>
    isHover &&
    css`
      background: var(--gray-1);
    `};
`;
