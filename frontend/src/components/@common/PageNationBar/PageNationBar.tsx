import { useMemo } from 'react';
import styled, { css } from 'styled-components';
import LeftBracket from '~/assets/icons/left-bracket.svg';
import RightBracket from '~/assets/icons/right-bracket.svg';
import { FONT_SIZE } from '~/styles/common';

interface PageNationBarProps {
  totalPage: number;
  currentPage: number;
  clickPageButton: React.MouseEventHandler<HTMLButtonElement>;
}

function PageNationBar({ totalPage, currentPage, clickPageButton }: PageNationBarProps) {
  const makePageNumber = useMemo(() => {
    const isNeedPrevDots = currentPage - 2 > 1;
    const isNeedNextDots = currentPage + 2 < totalPage;

    if (totalPage < 7) {
      return Array.from({ length: totalPage }, (_, index) => index + 1);
    }
    if (isNeedPrevDots && isNeedNextDots) {
      return [1, '...', currentPage - 1, currentPage, currentPage + 1, '...', totalPage];
    }
    if (isNeedPrevDots && !isNeedNextDots) {
      return [1, '...', totalPage - 3, totalPage - 2, totalPage - 1, totalPage];
    }
    if (!isNeedPrevDots && isNeedNextDots) {
      return [1, 2, 3, 4, '...', totalPage];
    }

    return Array.from({ length: totalPage }, (_, index) => index + 1);
  }, [totalPage, currentPage]);

  return (
    <StyledPageNationBar>
      <StyledBracketButton value="prev" disabled={currentPage === 1} onClick={clickPageButton}>
        <LeftBracket width={12} height={12} />
      </StyledBracketButton>
      {makePageNumber.map(value => (
        <StyledPageButton
          value={value}
          isCurrentPage={value === currentPage}
          onClick={clickPageButton}
          disabled={value === '...'}
        >
          {value}
        </StyledPageButton>
      ))}
      <StyledBracketButton value="next" disabled={currentPage === totalPage} onClick={clickPageButton}>
        <RightBracket width={12} height={12} />
      </StyledBracketButton>
    </StyledPageNationBar>
  );
}

export default PageNationBar;

const StyledPageNationBar = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0 1.6rem;
  margin-top: 3.6rem;

  & > button {
    display: flex;
    justify-content: center;
    align-items: center;

    width: 32px;
    height: 32px;

    border: none;
    border-radius: 50%;
  }

  @media screen and (width <= 420px) {
    gap: 0 0.8rem;
    margin-top: 3.6rem;

    & > button {
      width: 24px;
      height: 24px;

      border: none;
      border-radius: 50%;

      font-size: ${FONT_SIZE.sm};
    }
  }
`;

const StyledBracketButton = styled.button<{ disabled: boolean }>`
  background: none;

  ${({ disabled }) => disabled && `opacity: 0.2;`}
`;

const StyledPageButton = styled.button<{ isCurrentPage: boolean; value: number | string }>`
  background: none;

  ${({ isCurrentPage }) =>
    isCurrentPage
      ? css`
          background-color: var(--black);

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
