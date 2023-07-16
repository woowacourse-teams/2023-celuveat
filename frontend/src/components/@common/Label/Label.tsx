import { styled } from 'styled-components';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';

interface LabelProps {
  text: string;
}

function Label({ text }: LabelProps) {
  return <StyledDiv>{text}</StyledDiv>;
}

export default Label;

const StyledDiv = styled.div`
  display: flex;

  width: 38px;
  height: 14px;

  background-color: var(--primary-3-transparent-25);

  color: var(--primary-6);
  font-size: ${FONT_SIZE.xs};
  justify-content: center;
  align-items: center;
  border-radius: ${BORDER_RADIUS.xs};
`;
