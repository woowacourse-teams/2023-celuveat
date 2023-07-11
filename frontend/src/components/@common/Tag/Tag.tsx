import styled from 'styled-components';
import { FONT_SIZE } from '~/styles/common';

interface TagProps {
  text: string;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
}

function Tag({ text, onClick }: TagProps) {
  return (
    <StyledTag type="button" onClick={onClick}>
      # {text}
    </StyledTag>
  );
}

export default Tag;

const StyledTag = styled.button`
  width: fit-content;

  padding: 0.6rem 0.8rem;

  border: 1px solid var(--gray-3);
  border-radius: 18px;
  background: none;

  font-size: ${FONT_SIZE.sm};
  color: var(--gray-3);

  &:hover {
    border: 1px solid var(--primary-6);
    background-color: var(--primary-3-transparent-25);

    color: var(--primary-6);
  }
`;
