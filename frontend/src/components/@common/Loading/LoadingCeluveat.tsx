import styled, { keyframes } from 'styled-components';
import Cel from '~/assets/icons/celuveat_cel.svg';
import Luv from '~/assets/icons/celuveat_luv.svg';
import Eat from '~/assets/icons/celuveat_eat.svg';

interface LoadingAnimationProps {
  size: number;
}

function LoadingAnimation({ size }: LoadingAnimationProps) {
  return (
    <StyledLoadingAnimation>
      <StyledBouncing size={size}>
        <Cel width={`${size}px`} />
      </StyledBouncing>
      <StyledBouncing size={size}>
        <Luv width={`${size}px`} />
      </StyledBouncing>
      <StyledBouncing size={size}>
        <Eat width={`${size}px`} />
      </StyledBouncing>
    </StyledLoadingAnimation>
  );
}

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
    top: 30px;
  }
  30% {
    transform: rotate(20deg);
  }
  50% {
    top: 0px;
  }
  75% {
    top: 30px;
  }
  80% {
    transform: rotate(-20deg);
  }
  100% {
    top: 0px;
  }
`;

const StyledBouncing = styled.div<{ size: number }>`
  position: relative;
  top: 0;
  left: ${({ size }) => `${size}px`};

  width: ${({ size }) => `${size}px`};
  height: ${({ size }) => `${size}px`};

  border-radius: 50%;
  background: none;
  animation: ${bounceAnimation} 1.6s ease-in-out infinite;
`;

export default LoadingAnimation;
