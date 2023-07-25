import { css, styled } from 'styled-components';
import { paintSkeleton } from '~/styles/common';

interface SkeletonProps {
  width: string;
  height: string;
}

function Skeleton({ width, height }: SkeletonProps) {
  return <StyledSkeleton width={width} height={height} />;
}

export default Skeleton;

const StyledSkeleton = styled.div<{ width: string; height: string }>`
  ${paintSkeleton}

  ${({ width, height }) => css`
    width: ${width};
    height: ${height};
  `}
`;
