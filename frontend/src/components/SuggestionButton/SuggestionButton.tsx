import { styled } from 'styled-components';
import { ChangeEvent, FormEvent, useState } from 'react';
import { useParams } from 'react-router-dom';
import useBooleanState from '~/hooks/useBooleanState';
import { Modal, ModalContent } from '../@common/Modal';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import TextButton from '../@common/Button';
import Pencil from '~/assets/icons/pencil.svg';
import useRestaurant from '~/hooks/server/useRestaurant';

const labels = [
  '레스토랑이 폐점했어요.',
  '레스토랑 이름이 잘못되었어요.',
  '레스토랑 주소가 잘못되었어요.',
  '레스토랑 전화번호가 잘못되었어요',
];

function SuggestionButton() {
  const { id } = useParams();
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);
  const [checkedItems, setCheckedItems] = useState<string[]>([]);
  const [textareaValue, setTextareaValue] = useState('');
  const { postRevisedInfo } = useRestaurant();

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();

    const requestData = textareaValue ? [...checkedItems, textareaValue] : checkedItems;
    postRevisedInfo({ restaurantId: Number(id), data: { contents: requestData } });
    window.location.reload();
  };

  const clickCheckBox = (e: ChangeEvent<HTMLInputElement>) => {
    const { checked, value } = e.target;

    setCheckedItems(prev => (checked ? [...prev, value] : prev.filter(item => item !== value)));
  };

  const onChangeTextarea = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setTextareaValue(e.target.value);
  };

  return (
    <>
      <StyledButton type="button" onClick={openModal}>
        <Pencil width={16} />
        <div>정보 수정 제안하기</div>
      </StyledButton>
      <Modal open={openModal} close={closeModal} isOpen={isModalOpen}>
        <ModalContent title="정보 수정 제안">
          <StyledForm onSubmit={handleSubmit}>
            <h5>수정 항목</h5>
            <p>잘못되었거나 수정이 필요한 정보들을 모두 선택해주세요.</p>
            <StyledUnorderedList>
              {labels.map(label => (
                <StyledListItem>
                  <CheckBox value={label} onChange={clickCheckBox} />
                  <span>{label}</span>
                </StyledListItem>
              ))}
              <StyledTextarea placeholder="(선택) 내용을 입력해주세요." onChange={onChangeTextarea} />
            </StyledUnorderedList>
            <TextButton
              type="submit"
              text="등록하기"
              onClick={handleSubmit}
              colorType="light"
              disabled={!checkedItems.length && !textareaValue}
            />
          </StyledForm>
        </ModalContent>
      </Modal>
    </>
  );
}

export default SuggestionButton;

const StyledForm = styled.form`
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

const StyledTextarea = styled.textarea`
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
