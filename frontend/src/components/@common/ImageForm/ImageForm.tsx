import { styled, css } from 'styled-components';
import { FONT_SIZE } from '~/styles/common';

interface ImageFormProps extends React.ComponentPropsWithoutRef<'input'> {
  disabled?: boolean;
  onChange: React.ChangeEventHandler<HTMLInputElement>;
}

function ImageForm({ onChange, disabled = false, ...restProps }: ImageFormProps) {
  return (
    <StyledButton disabled={disabled}>
      +
      <input type="file" disabled={disabled} onChange={onChange} {...restProps} />
    </StyledButton>
  );
}

export default ImageForm;

const StyledButton = styled.label<{ disabled: boolean }>`
  padding: 4.2rem;

  border: 4px solid #ddd;
  border-radius: 20px;
  background-color: transparent;

  font-size: ${FONT_SIZE.lg};

  cursor: pointer;

  &:hover {
    background-color: #ddd;
  }

  input[type='file'] {
    display: none;
  }

  ${({ disabled }) =>
    disabled &&
    css`
      background-color: #ddd;

      cursor: not-allowed;
    `}
`;
