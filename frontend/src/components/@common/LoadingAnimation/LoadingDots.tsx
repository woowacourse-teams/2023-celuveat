import styled, { keyframes } from 'styled-components';

function LoadingDots() {
  return (
    <StyledLoadingDots>
      <StyledLoadingDot />
      <StyledLoadingDot />
      <StyledLoadingDot />
    </StyledLoadingDots>
  );
}

export default LoadingDots;

const StyledLoadingDots = styled.div`
  display: flex;
  gap: 0 0.6rem;

  & > div:nth-child(2) {
    animation-delay: 0.13s;
  }

  & > div:nth-child(3) {
    animation-delay: 0.26s;
  }
`;

const pulseAnimation = keyframes`
0% {
  transform: scale(0);
}
90%, 100% {
    transform: scale(1);
}
`;

const StyledLoadingDot = styled.div`
  width: 12px;
  height: 12px;

  border-radius: 50%;
  background-color: var(--black);
  animation-timing-function: cubic-bezier(0, 0, 1, 1);
  animation: ${pulseAnimation} 0.4s ease-in-out infinite alternate;
`;
