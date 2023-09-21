import { styled, keyframes } from 'styled-components';
import Dot from '~/assets/icons/dot.svg';

function LoadingDots() {
  return (
    <StyledLoadingDots>
      <StyledLoadingDot>
        <Dot />
      </StyledLoadingDot>
      <StyledLoadingDot>
        <Dot />
      </StyledLoadingDot>
      <StyledLoadingDot>
        <Dot />
      </StyledLoadingDot>
    </StyledLoadingDots>
  );
}

export default LoadingDots;

const StyledLoadingDots = styled.div`
  display: flex;
  gap: 0 1rem;

  & > div:nth-child(2) {
    animation-delay: 0.14s;
  }

  & > div:nth-child(3) {
    animation-delay: 0.28s;
  }
`;

const pulseAnimation = keyframes`
  0% {
    transform: scale(1);
  }
  90%, 100% {
    transform: scale(0);
  }
`;

const StyledLoadingDot = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 12px;
  height: 12px;

  animation: ${pulseAnimation} 0.4s ease-in-out infinite alternate;
  animation-timing-function: cubic-bezier(0, 0, 1, 1);
`;
