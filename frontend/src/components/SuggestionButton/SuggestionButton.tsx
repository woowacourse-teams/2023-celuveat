import { styled } from 'styled-components';
import Pencil from '~/assets/icons/pencil.svg';
import useCeluveatModal from '~/hooks/useCeluveatModal';

function SuggestionButton() {
  const { openSuggestionModal } = useCeluveatModal();

  return (
    <StyledButton type="button" onClick={openSuggestionModal}>
      <Pencil width={16} />
      <div>정보 수정 제안하기</div>
    </StyledButton>
  );
}

export default SuggestionButton;

const StyledButton = styled.button`
  display: flex;
  align-items: center;
  gap: 0 1.2rem;

  margin: 2rem auto 0;

  border: none;
  background: none;

  & > div {
    color: var(--gray-3);
    font-family: SUIT-Medium, sans-serif;
    font-size: 1.4rem;
    text-decoration-line: underline;
  }
`;
