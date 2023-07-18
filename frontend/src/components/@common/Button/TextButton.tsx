import { styled } from 'styled-components';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';

interface TextButtonProps extends React.HTMLAttributes<HTMLButtonElement> {
  type: 'button' | 'submit';
  text: string;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
  colorType: 'dark' | 'light';
  disabled?: boolean;
}

function TextButton({ text, colorType, disabled = false, ...props }: TextButtonProps) {
  return (
    <StyledButton {...props} colorType={colorType} disabled={disabled}>
      {text}
    </StyledButton>
  );
}

export default TextButton;

const StyledButton = styled.button<{ colorType: 'dark' | 'light' }>`
  padding: 1.2rem 2.4rem;

  border: none;
  border-radius: ${BORDER_RADIUS.sm};
  background-color: ${({ colorType }) => (colorType === 'dark' ? 'var(--primary-5)' : 'var(--primary-1)')};

  color: ${({ colorType }) => (colorType === 'dark' ? 'var(--white)' : 'var(--primary-5)')};
  font-size: ${FONT_SIZE.md};

  &:disabled {
    background-color: ${({ colorType }) => (colorType === 'dark' ? 'var(--primary-3)' : 'var(--gray-1)')};

    color: var(--white);
  }

  &:not(:disabled):hover {
    background-color: ${({ colorType }) => (colorType === 'dark' ? 'var(--primary-6);' : 'var(--primary-2)')};
  }
`;
