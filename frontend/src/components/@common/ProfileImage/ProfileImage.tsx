import { styled } from 'styled-components';

interface ProfileImageProps extends React.HTMLAttributes<HTMLImageElement> {
  name: string;
  imageUrl: string;
  size?: string;
}

function ProfileImage({ name = '셀럽', imageUrl, size, ...props }: ProfileImageProps) {
  return <StyledProfile alt={`${name} 프로필`} src={imageUrl} size={size} {...props} />;
}

export default ProfileImage;

const StyledProfile = styled.img<{ size: string }>`
  width: ${({ size }) => size || 'auto'};
  height: ${({ size }) => size || 'auto'};

  border-radius: 50%;
  background: none;
`;
