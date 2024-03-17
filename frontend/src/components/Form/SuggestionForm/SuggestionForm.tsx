import { ChangeEvent, FormEvent, useState } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import { postRevisedInfo } from '~/api/restaurant';
import TextButton from '~/components/@common/Button';

import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';

const labels = [
  '레스토랑이 폐점했어요.',
  '레스토랑 이름이 잘못되었어요.',
  '레스토랑 주소가 잘못되었어요.',
  '레스토랑 전화번호가 잘못되었어요',
];

function SuggestionForm() {
  const { id } = useParams();
  const [checkedItems, setCheckedItems] = useState<string[]>([]);
  const [textareaValue, setTextareaValue] = useState('');

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
  );
}

export default SuggestionForm;

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
