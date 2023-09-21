import { styled, keyframes } from 'styled-components';
import Cel from '~/assets/icons/celuveat_cel.svg';
import Luv from '~/assets/icons/celuveat_luv.svg';
import Eat from '~/assets/icons/celuveat_eat.svg';

interface LoadingIndicatorProps {
  size: number;
}

function LoadingIndicator({ size }: LoadingIndicatorProps) {
  return (
    <StyledLoadingAnimation>
      <StyledBouncing size={size}>
        <Cel />
      </StyledBouncing>
      <StyledBouncing size={size}>
        <Luv />
      </StyledBouncing>
      <StyledBouncing size={size}>
        <Eat />
      </StyledBouncing>
    </StyledLoadingAnimation>
  );
}

export default LoadingIndicator;

const StyledLoadingAnimation = styled.div`
  display: flex;
  gap: 0.6rem;

  & > div:nth-child(2) {
    animation-delay: 0.2s;
  }

  & > div:nth-child(3) {
    animation-delay: 0.4s;
  }
`;

const bounceAnimation = keyframes`
  0% {
    top: 0px;
  }
  25% {
    top: 10px;
  }
  30% {
    transform: rotate(10deg);
  }
  50% {
    top: 0px;
  }
  75% {
    top: 10px;
  }
  80% {
    transform: rotate(-10deg);
  }
  100% {
    top: 0px;
  }
`;

const StyledBouncing = styled.div<{ size: number }>`
  position: relative;
  top: 0;

  width: ${({ size }) => `${size}px`};
  height: ${({ size }) => `${size}px`};

  border-radius: 50%;
  background: none;
  animation: ${bounceAnimation} 1.6s ease-in-out infinite;
`;
