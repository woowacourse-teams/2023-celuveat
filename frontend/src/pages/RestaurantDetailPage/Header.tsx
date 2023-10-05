import styled from 'styled-components';
import View from '~/assets/icons/view.svg';
import Love from '~/assets/icons/black-love.svg';
import { FONT_SIZE } from '~/styles/common';

interface HeaderProps {
  name: string;
  viewCount: number;
  likeCount: number;
}

function Header({ name, viewCount, likeCount }: HeaderProps) {
  return (
    <StyledDetailHeader tabIndex={0}>
      <h3>{name}</h3>
      <div role="group">
        <div aria-label={`조회수 ${viewCount}`}>
          <View width={18} aria-hidden /> <span aria-hidden>{viewCount}</span>
        </div>
        <div aria-label={`좋아요수 ${viewCount}`}>
          <Love width={18} aria-hidden /> <span aria-hidden>{likeCount}</span>
        </div>
      </div>
    </StyledDetailHeader>
  );
}

export default Header;

const StyledDetailHeader = styled.section`
  display: flex;
  flex-direction: column;
  gap: 0.8rem 0;

  padding: 1.2rem 0 2.4rem;

  & > div {
    display: flex;
    align-items: center;
    gap: 0 0.8rem;

    font-size: ${FONT_SIZE.md};

    & > div {
      display: flex;
      align-items: center;
      gap: 0 0.8rem;
    }
  }
`;
