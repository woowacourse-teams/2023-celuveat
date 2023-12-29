import { css, styled } from 'styled-components';
import { paintSkeleton } from '~/styles/common';

interface SkeletonProps {
  width: string;
  height: string;
  borderRadius?: string;
}

function Skeleton({ width, height, borderRadius }: SkeletonProps) {
  return <StyledSkeleton width={width} height={height} borderRadius={borderRadius} />;
}

export default Skeleton;

const StyledSkeleton = styled.div<{ width: string; height: string; borderRadius: string }>`
  ${paintSkeleton}

  ${({ width, height }) => css`
    width: ${width};
    height: ${height};
  `}

  ${({ borderRadius }) =>
    borderRadius &&
    css`
      border-radius: ${borderRadius};
    `}
`;
