import { styled, css } from 'styled-components';
import TextButton from '../@common/Button';
import ProfileImage from '../@common/ProfileImage';
import { FONT_SIZE } from '~/styles/common';

interface CelebBannerProps {
  name: string;
  youtubeChannelName: string;
  subscriberCount: number;
  restaurantCount: number;
  youtubeChannelUrl: string;
  profileImageUrl: string;
  backgroundImageUrl: string | null;
}

function CelebBanner({
  name,
  youtubeChannelName,
  subscriberCount,
  restaurantCount,
  youtubeChannelUrl,
  profileImageUrl,
  backgroundImageUrl,
}: CelebBannerProps) {
  const onClick = () => window.open(youtubeChannelUrl, '_blank');

  return (
    <StyledContainer background={backgroundImageUrl}>
      <StyledCelebInfo>
        <ProfileImage name={name} imageUrl={profileImageUrl} size={172} />
        <StyledName>{name}</StyledName>
        <StyledDetail>
          {youtubeChannelName} 구독자 {subscriberCount / 10_000}만명 ∙ 음식점 {restaurantCount}개
        </StyledDetail>
      </StyledCelebInfo>
      <TextButton type="button" text="유튜브 바로가기" colorType="light" onClick={onClick} />
    </StyledContainer>
  );
}

export default CelebBanner;

const StyledContainer = styled.section<{ background: string }>`
  display: flex;
  justify-content: space-around;
  align-items: center;

  width: 100%;

  padding: 6.4rem 0;

  ${({ background }) =>
    background
      ? css`
          background-image: url(${background});
        `
      : css`
          background-color: var(--primary-4);
        `};

  & > button {
    width: 260px;
  }
`;

const StyledCelebInfo = styled.div`
  display: grid;
  grid-template-columns: auto 1fr;

  gap: 2.4rem;

  & > *:first-child {
    grid-area: 1 / 1 / span 2 / span 1;
  }
`;

const StyledName = styled.h2`
  grid-area: 1 / 2 / span 1 / span 1;
  align-self: end;

  color: var(--white);
`;

const StyledDetail = styled.div`
  grid-area: 2 / 2 / span 1 / span 1;

  color: var(--white);
  font-size: ${FONT_SIZE.sm};
`;
