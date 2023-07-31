import { styled } from 'styled-components';
import ProfileImage from '../ProfileImage/ProfileImage';
import useBooleanState from '~/hooks/useBooleanState';

import type { Celeb } from '~/@types/celeb.types';

interface ProfileImageListProps {
  celebs: Celeb[];
  size: string;
}

function ProfileImageList({ celebs, size }: ProfileImageListProps) {
  const { value: hover, setTrue, setFalse } = useBooleanState(false);

  return (
    <StyledProfileImageList onMouseEnter={setTrue} onMouseLeave={setFalse} size={size}>
      {celebs.map((celeb, index) => (
        <StyledProfileImageWrapper key={celeb.id} index={index} hover={hover}>
          <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size={size} />
        </StyledProfileImageWrapper>
      ))}
    </StyledProfileImageList>
  );
}

export default ProfileImageList;

const StyledProfileImageList = styled.div<{ size: string }>`
  position: relative;

  width: ${({ size }) => `${size}`};
  height: ${({ size }) => `${size}`};
`;

const StyledProfileImageWrapper = styled.div<{ index: number; hover: boolean }>`
  position: absolute;
  z-index: ${({ index }) => 100 - index};

  transition: 0.4s ease-in-out;

  ${({ hover, index }) =>
    hover
      ? `
          transform: translateX(${index * -110}%);
        `
      : `
          transform: translateX(${index * -20}%);
        `};
`;
