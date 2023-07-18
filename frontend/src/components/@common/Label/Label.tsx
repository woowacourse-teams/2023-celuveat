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
  justify-content: center;
  align-items: center;

  width: 38px;
  height: 14px;

  border-radius: ${BORDER_RADIUS.xs};
  background-color: var(--primary-3-transparent-25);

  color: var(--primary-6);
  font-size: ${FONT_SIZE.xs};
`;
