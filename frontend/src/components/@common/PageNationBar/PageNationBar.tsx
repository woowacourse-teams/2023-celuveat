import styled, { css } from 'styled-components';
import LeftBracket from '~/assets/icons/left-bracket.svg';
import RightBracket from '~/assets/icons/right-bracket.svg';

interface PageNationBarProps {
  totalPage: number;
  currentPage: number;
  clickPageButton: React.MouseEventHandler<HTMLButtonElement>;
}

function PageNationBar({ totalPage, currentPage, clickPageButton }: PageNationBarProps) {
  const makePageNumber = (currentPageNumber: number) => {
    const isNeedPrevDots = currentPageNumber - 2 > 1;
    const isNeedNextDots = currentPageNumber + 2 < totalPage;

    if (totalPage < 7) {
      return Array.from({ length: totalPage }, (_, index) => index + 1);
    }
    if (isNeedPrevDots && isNeedNextDots) {
      return [1, '...', currentPageNumber - 1, currentPageNumber, currentPageNumber + 1, '...', totalPage];
    }
    if (isNeedPrevDots && !isNeedNextDots) {
      return [1, '...', totalPage - 3, totalPage - 2, totalPage - 1, totalPage];
    }
    if (!isNeedPrevDots && isNeedNextDots) {
      return [1, 2, 3, 4, '...', totalPage];
    }

    return Array.from({ length: totalPage }, (_, index) => index + 1);
  };

  return (
    <StyledPageNationBar>
      <LeftBracket width={12} height={12} />
      {makePageNumber(currentPage + 1).map(value => (
        <StyledPageButton
          value={value}
          isCurrentPage={value === currentPage + 1}
          onClick={clickPageButton}
          disabled={value === '...'}
        >
          {value}
        </StyledPageButton>
      ))}
      <RightBracket width={12} height={12} />
    </StyledPageNationBar>
  );
}

export default PageNationBar;

const StyledPageNationBar = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0 1.6rem;
  margin-top: 3.2rem;
`;

const StyledPageButton = styled.button<{ isCurrentPage: boolean; value: number | string }>`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 32px;
  height: 32px;

  border: none;
  border-radius: 50%;
  background: none;

  ${({ isCurrentPage }) =>
    isCurrentPage
      ? css`
          background: var(--black);

          color: var(--white);
        `
      : css`
          &:hover {
            background: #f7f7f7;
          }
        `}

  ${({ value }) =>
    value === '...' &&
    css`
      &:hover {
        background: var(--white);
      }

      cursor: default;
    `}
`;
