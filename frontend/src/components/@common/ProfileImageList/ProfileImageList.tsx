import { styled } from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { MouseEvent } from 'react';
import ProfileImage from '../ProfileImage/ProfileImage';
import useBooleanState from '~/hooks/useBooleanState';

import type { Celeb } from '~/@types/celeb.types';

interface ProfileImageListProps {
  celebs: Celeb[];
  size: string;
}

function ProfileImageList({ celebs, size }: ProfileImageListProps) {
  const { value: hover, setTrue, setFalse } = useBooleanState(false);
  const navigate = useNavigate();

  return (
    <StyledProfileImageList onMouseEnter={setTrue} onMouseLeave={setFalse} size={size}>
      {celebs.map((celeb, index) => (
        <StyledProfileImageWrapper
          key={celeb.id}
          index={index}
          hover={hover}
          length={celebs.length}
          onClick={(e: MouseEvent) => {
            e.stopPropagation();
            navigate(`/celeb/${celeb.id}`);
          }}
        >
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

const StyledProfileImageWrapper = styled.div<{ index: number; hover: boolean; length: number }>`
  position: absolute;
  z-index: ${({ length, index }) => 10 + length - index};

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
