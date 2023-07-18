import { ComponentPropsWithRef, forwardRef } from 'react';
import styled from 'styled-components';
import type { CelebsSearchBarOptionType } from '~/@types/celebs.types';
import ProfileImage from '~/components/@common/ProfileImage';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';

interface CelebsSearchBarOption extends ComponentPropsWithRef<'ul'> {
  width: number;
  options: CelebsSearchBarOptionType[];
  onClickEvent: (option: CelebsSearchBarOptionType) => () => void;
}

const SearchBarSelectBox = forwardRef<HTMLUListElement, CelebsSearchBarOption>(
  ({ options, width, onClickEvent }, ref) => (
    <StyledSearchBarTags width={width} ref={ref}>
      {options.map(option => (
        <StyledSearchBarTag key={option.id} value={option.youtubeChannelName} onClick={onClickEvent(option)}>
          <ProfileImage name={option.name} imageUrl={option.profileImageUrl} size={36} />
          <StyledYoutubeName>{option.youtubeChannelName}</StyledYoutubeName>
        </StyledSearchBarTag>
      ))}
    </StyledSearchBarTags>
  ),
);

export default SearchBarSelectBox;

const StyledSearchBarTags = styled.ul<{ width: number }>`
  width: ${props => `${props.width}px`};

  padding: 0 2.1rem;
  border-radius: ${BORDER_RADIUS.lg};

  font-size: ${FONT_SIZE.sm};
  box-shadow: var(--shadow);
`;

const StyledSearchBarTag = styled.li`
  display: flex;
  align-items: center;

  padding: 1.1rem;
  border: none;
  background: none;
  outline: none;

  & + & {
    border-top: 1px solid var(--gray-1);
  }
`;

const StyledYoutubeName = styled.span`
  margin-left: 1.9rem;
`;
