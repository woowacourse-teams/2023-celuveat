/* eslint-disable react/jsx-props-no-spreading */
import { styled } from 'styled-components';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';

interface TextButtonProps extends React.HTMLAttributes<HTMLButtonElement> {
  text: string;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
  additionalStyle: string;
}

function TextButton({ text, onClick, additionalStyle, ...props }: TextButtonProps) {
  return (
    <StyledButton {...props} onClick={onClick} additionalStyle={additionalStyle}>
      {text}
    </StyledButton>
  );
}

const StyledButton = styled.button<{ additionalStyle: string }>`
  ${props => props.additionalStyle}
  font-size: ${FONT_SIZE.md};
  padding: 12px 24px;
  border: none;
  border-radius: ${BORDER_RADIUS.sm};
  background-color: var(--yellow);
  cursor: pointer;
`;

export default TextButton;
