import { styled } from 'styled-components';

interface ProfileImageProps extends React.HTMLAttributes<HTMLImageElement> {
  name: string;
  imageUrl: string;
  border?: boolean;
  size: number;
}

function ProfileImage({ name = '셀럽', imageUrl, size, border = false, ...props }: ProfileImageProps) {
  return <StyledProfile alt={`${name} 프로필`} src={imageUrl} border={border} size={size} {...props} />;
}

export default ProfileImage;

const StyledProfile = styled.img<{ size: number; border: boolean }>`
  width: ${({ size }) => `${size}px`};
  height: ${({ size }) => `${size}px`};

  border: ${({ border }) => (border ? `1px solid var(--primary-1)` : `none`)};
  border-radius: 50%;
  background: var(--red-5);
`;
