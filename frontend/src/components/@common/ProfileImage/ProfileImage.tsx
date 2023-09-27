import { css, styled } from 'styled-components';
import { paintSkeleton } from '~/styles/common';

interface ProfileImageProps extends React.HTMLAttributes<HTMLImageElement> {
  name: string;
  imageUrl: string;
  size?: string;
  boxShadow?: boolean;
}

function ProfileImage({ name = '셀럽', imageUrl, size, boxShadow = false, ...props }: ProfileImageProps) {
  return <StyledProfile alt={`${name} 프로필`} src={imageUrl} size={size} boxShadow={boxShadow} {...props} />;
}

export default ProfileImage;

const StyledProfile = styled.img<{ size: string; boxShadow: boolean }>`
  ${paintSkeleton}
  width: ${({ size }) => size || 'auto'};
  height: ${({ size }) => size || 'auto'};

  border-radius: 50%;
  background: none;

  ${({ boxShadow }) =>
    boxShadow &&
    css`
      box-shadow: var(--shadow);
    `}
`;
