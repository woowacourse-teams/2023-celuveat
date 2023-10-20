import { keyframes, styled } from 'styled-components';

interface SpinnerStyleProps {
  size: `${string}px`;
}

function Spinner({ size }: SpinnerStyleProps) {
  return <StyledSpinner size={size} />;
}

export default Spinner;

const rotation = keyframes`
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
`;

const StyledSpinner = styled.div<SpinnerStyleProps>`
  display: inline-block;

  width: ${({ size }) => size};
  height: ${({ size }) => size};

  border: 3px solid #fff;
  border-radius: 50%;
  border-bottom-color: transparent;
  box-sizing: border-box;
  animation: ${rotation} 1s linear infinite;
`;
