import { styled } from 'styled-components';
import { FONT_SIZE } from '~/styles/common';

interface ImageFormProps extends React.ComponentPropsWithoutRef<'input'> {
  onChange: React.ChangeEventHandler<HTMLInputElement>;
}

function ImageForm({ onChange, ...restProps }: ImageFormProps) {
  return (
    <StyledButton>
      +
      <input type="file" onChange={onChange} {...restProps} />
    </StyledButton>
  );
}

export default ImageForm;

const StyledButton = styled.label`
  padding: 4.8rem;

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
`;
