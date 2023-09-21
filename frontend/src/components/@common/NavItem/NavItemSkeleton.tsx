import { styled } from 'styled-components';

import { BORDER_RADIUS, paintSkeleton } from '~/styles/common';

function NavItemSkeleton() {
  return (
    <StyledNavItem>
      <div />
      <div>
        <span />
      </div>
    </StyledNavItem>
  );
}

export default NavItemSkeleton;

const StyledNavItem = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  width: 68px;

  border: none;
  background: none;

  & > div {
    ${paintSkeleton}
  }

  & > div:first-child {
    display: flex;
    justify-content: center;
    align-items: center;

    width: 36px;
    height: 36px;

    border-radius: 50%;
    margin-bottom: 0.4rem;
  }

  & > div:last-child {
    width: 40px;
    height: 12px;

    border-radius: ${BORDER_RADIUS.xs};
  }
`;
