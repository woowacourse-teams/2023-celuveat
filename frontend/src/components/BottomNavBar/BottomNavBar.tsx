import { useRef } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import NavItem from '../@common/NavItem';
import LoveIcon from '~/assets/icons/love.svg';
import UserButton from './UserButton';
import FilterButton from './FilterButton';
import useScrollBlock from '~/hooks/useScrollBlock';

interface BottomNavBarProps {
  isHide: boolean;
}

function BottomNavBar({ isHide }: BottomNavBarProps) {
  const ref = useRef();
  const navigator = useNavigate();

  const clickWishList = () => navigator('/restaurants/like');

  useScrollBlock(ref);

  return (
    <StyledBottomNavBar isHide={isHide} ref={ref}>
      <StyledNavBarButton type="button" onClick={clickWishList}>
        <NavItem label="위시리스트" icon={<LoveIcon width={24} />} />
      </StyledNavBarButton>

      <FilterButton />
      <UserButton />
    </StyledBottomNavBar>
  );
}

export default BottomNavBar;

const StyledBottomNavBar = styled.nav<{ isHide: boolean }>`
  display: flex;
  justify-content: space-around;
  align-items: flex-start;

  position: fixed;
  bottom: 0;
  z-index: 20;

  width: 100vw;
  height: 64px;

  background-color: var(--white);

  border-top: 1px solid var(--gray-1);

  ${({ isHide }) => isHide && 'transform: translateY(64px)'};

  transition: 0.4s ease-in-out;
`;

const StyledNavBarButton = styled.button`
  border: none;
  outline: none;

  background: none;
`;
