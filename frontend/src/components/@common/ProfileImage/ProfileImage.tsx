import { styled } from 'styled-components';

interface ProfileImageProps extends React.HTMLAttributes<HTMLImageElement> {
  name: string;
  imageUrl: string;
  border?: boolean;
  size?: string;
}

function ProfileImage({ name = '셀럽', imageUrl, size, border = false, ...props }: ProfileImageProps) {
  return <StyledProfile alt={`${name} 프로필`} src={imageUrl} border={border} size={size} {...props} />;
}

export default ProfileImage;

const StyledProfile = styled.img<{ size: string; border: boolean }>`
  width: ${({ size }) => size || 'auto'};
  height: ${({ size }) => size || 'auto'};

  border-radius: 50%;
  background: none;
`;
