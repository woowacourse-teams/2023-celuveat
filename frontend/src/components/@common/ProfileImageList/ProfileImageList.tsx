import { styled } from 'styled-components';
import { useState } from 'react';
import { Celeb } from '~/@types/celeb.types';
import ProfileImage from '../ProfileImage/ProfileImage';

interface ProfileImageListProps {
  celebs: Celeb[];
  size: number;
}

function ProfileImageList({ celebs, size }: ProfileImageListProps) {
  const [hover, setHover] = useState(false);

  const onMouseEnter = () => setHover(true);
  const onMouseLeave = () => setHover(false);

  return (
    <StyledProfileImageList onMouseEnter={onMouseEnter} onMouseLeave={onMouseLeave} size={size}>
      {celebs.map((celeb, index) => (
        <StyledProfileImageWrapper key={celeb.id} index={index} hover={hover}>
          <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size={size} />
        </StyledProfileImageWrapper>
      ))}
    </StyledProfileImageList>
  );
}

export default ProfileImageList;

const StyledProfileImageList = styled.div<{ size: number }>`
  position: relative;

  width: ${({ size }) => `${size}px`};
  height: ${({ size }) => `${size}px`};
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
