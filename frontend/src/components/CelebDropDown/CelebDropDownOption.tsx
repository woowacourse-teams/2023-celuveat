import { styled } from 'styled-components';
import { Celeb } from '~/@types/celeb.types';
import ProfileImage from '~/components/@common/ProfileImage';
import { FONT_SIZE } from '~/styles/common';

import CelebIcon from '~/assets/icons/celeb.svg';

interface CelebDropDownOptionProps {
  celeb: Celeb;
}

function CelebDropDownOption({ celeb }: CelebDropDownOptionProps) {
  return (
    <div>
      {celeb.id === -1 ? (
        <CelebIcon />
      ) : (
        <ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size="32px" />
      )}
      <div>
        <StyledCelebName>{celeb.name}</StyledCelebName>
        <StyledChannelName>{celeb.youtubeChannelName}</StyledChannelName>
      </div>
    </div>
  );
}

export default CelebDropDownOption;

const StyledCelebName = styled.div`
  font-family: SUIT-Medium, sans-serif;
`;

const StyledChannelName = styled.div`
  padding-top: 0.4rem;

  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;
