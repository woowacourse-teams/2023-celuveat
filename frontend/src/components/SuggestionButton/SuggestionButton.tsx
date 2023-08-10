import { styled } from 'styled-components';
import useBooleanState from '~/hooks/useBooleanState';
import { Modal, ModalContent } from '../@common/Modal';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import TextButton from '../@common/Button';

const labels = [
  '레스토랑이 폐점했어요.',
  '레스토랑 이름이 잘못되었어요.',
  '레스토랑 주소가 잘못되었어요.',
  '레스토랑 전화번호가 잘못되었어요',
  '레스토랑 위치가 잘못되었어요.',
  '레스토랑 휴무일 정보가 잘못되었어요.',
];

function SuggestionButton() {
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);

  return (
    <>
      <button type="button" onClick={openModal}>
        SuggestionButton
      </button>
      <Modal>
        <ModalContent isShow={isModalOpen} title="정보 수정 제안" closeModal={closeModal}>
          <StyledContainer>
            <h5>수정 항목</h5>
            <p>잘못되었거나 수정이 필요한 정보들을 모두 선택해주세요.</p>
            <StyledUnorderedList>
              {labels.map(label => (
                <StyledListItem>
                  <CheckBox />
                  <span>{label}</span>
                </StyledListItem>
              ))}
              <StyledTextArea placeholder="(선택) 내용을 입력해주세요." />
            </StyledUnorderedList>
            <TextButton type="submit" text="등록하기" onClick={() => {}} colorType="light" />
          </StyledContainer>
        </ModalContent>
      </Modal>
    </>
  );
}

export default SuggestionButton;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  font-size: ${FONT_SIZE.md};

  p {
    color: var(--gray-3);
  }
`;

const StyledUnorderedList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const StyledListItem = styled.li`
  display: flex;
  align-items: center;
  gap: 0.8rem;
`;

const StyledTextArea = styled.textarea`
  height: 128px;

  padding: 0.8rem;

  border: none;
  border-radius: ${BORDER_RADIUS.sm};
  background-color: var(--gray-1);
`;

const CheckBox = styled.input.attrs({ type: 'checkbox' })`
  width: 24px;
  height: 24px;

  border: 1px solid #ddd;
  border-radius: 50%;
  background-image: url('~/assets/icons/unchecked-icon.svg');
  background-size: cover;

  transition: all 0.2s ease;
  appearance: none;

  &:checked {
    border: 1px solid var(--primary-6);
    background-color: var(--primary-6);
    background-image: url('~/assets/icons/checked-icon.svg');
    background-size: cover;
  }
`;
