import { styled } from 'styled-components';
import { paintSkeleton } from '~/styles/common';

interface ProfileImageSkeletonProps {
  size: number;
}

function ProfileImageSkeleton({ size }: ProfileImageSkeletonProps) {
  return <StyledProfileImageSkeleton size={size} />;
}

export default ProfileImageSkeleton;

const StyledProfileImageSkeleton = styled.div<{ size: number }>`
  ${paintSkeleton}
  width: ${({ size }) => (size ? `${size}px` : '100%')};
  height: ${({ size }) => (size ? `${size}px` : 'auto')};

  border-radius: 50%;
  background: none;
`;
