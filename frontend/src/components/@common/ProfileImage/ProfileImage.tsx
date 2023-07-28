import { styled } from 'styled-components';

interface ProfileImageProps extends React.HTMLAttributes<HTMLImageElement> {
  name: string;
  imageUrl: string;
  border?: boolean;
  size?: number;
}

function ProfileImage({ name = '셀럽', imageUrl, size, border = false, ...props }: ProfileImageProps) {
  return <StyledProfile alt={`${name} 프로필`} src={imageUrl} border={border} size={size} {...props} />;
}

export default ProfileImage;

const StyledProfile = styled.img<{ size: number; border: boolean }>`
  width: ${({ size }) => (size ? `${size}px` : '100%')};
  height: ${({ size }) => (size ? `${size}px` : 'auto')};

  border-radius: 50%;
  background: none;
`;
