import { styled } from 'styled-components';
import { NavItemSkeleton } from '../@common/NavItem';

interface MainPageNavBarSkeletonProps {
  navItemLength: number;
}

function MainPageNavBarSkeleton({ navItemLength }: MainPageNavBarSkeletonProps) {
  return (
    <StyledNavBar>
      <NavItemSkeleton />
      <StyledLine />
      <div>
        {Array.from({ length: navItemLength }, () => (
          <NavItemSkeleton />
        ))}
      </div>
    </StyledNavBar>
  );
}

export default MainPageNavBarSkeleton;

const StyledNavBar = styled.nav`
  display: flex;
  align-items: center;

  position: sticky;
  top: 80px;
  z-index: 11;

  width: 100%;
  height: 80px;

  background-color: var(--white);

  padding-left: 1.2rem;
  border-bottom: 1px solid var(--gray-1);

  & > div:last-child {
    display: flex;
    align-items: center;

    & > * {
      margin: 0 0.6rem;
    }
  }
`;

const StyledLine = styled.div`
  margin-left: 1.2rem;

  width: 1px;
  height: 46px;

  background-color: var(--gray-3);
`;
