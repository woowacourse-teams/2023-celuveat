import { styled } from 'styled-components';
import { FONT_SIZE } from '~/styles/common';

function Label() {
  return <StyleDiv>유료광고</StyleDiv>;
}

export default Label;

const StyleDiv = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 38px;
  height: 14px;

  border-radius: 5px;

  color: var(--primary-6);
  font-size: ${FONT_SIZE.xs};

  background-color: var(--primary-3-transparent-25);
`;
