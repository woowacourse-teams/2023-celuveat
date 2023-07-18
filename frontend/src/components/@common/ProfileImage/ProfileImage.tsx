import { styled } from 'styled-components';

interface ProfileImageProps extends React.HTMLAttributes<HTMLImageElement> {
  name: string;
  imageUrl: string;
  size: number;
}

function ProfileImage({ name = '셀럽', imageUrl, size, ...props }: ProfileImageProps) {
  return <StyledProfile alt={`${name} 프로필`} src={imageUrl} size={size} {...props} />;
}

export default ProfileImage;

const StyledProfile = styled.img<{ size: number }>`
  width: ${props => props.size}px;
  height: ${props => props.size}px;
  border-radius: 50%;
  background: var(--red-5);
`;
