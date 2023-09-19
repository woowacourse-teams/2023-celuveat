import { styled } from 'styled-components';
import { BORDER_RADIUS, hideScrollBar, paintSkeleton } from '~/styles/common';
import type { Video } from '~/@types/api.types';

interface VideoCarouselProps {
  title: string;
  videos: Video[];
}

function VideoCarousel({ title, videos }: VideoCarouselProps) {
  return (
    <StyledVideoCarouselContainer>
      <h5>{title}</h5>
      <ul>
        {videos.map(({ name, youtubeVideoKey }) => (
          <li>
            <StyledVideo
              loading="lazy"
              title={`${name}의 영상`}
              src={`https://www.youtube.com/embed/${youtubeVideoKey}`}
              allow="encrypted-media; gyroscope; picture-in-picture"
              allowFullScreen
            />
          </li>
        ))}
      </ul>
    </StyledVideoCarouselContainer>
  );
}

export default VideoCarousel;

const StyledVideoCarouselContainer = styled.section`
  display: flex;
  flex-direction: column;
  gap: 2rem 0;

  & > ul {
    ${hideScrollBar}
    display: flex;
    gap: 0 2rem;
    overflow-x: scroll;
  }
`;

const StyledVideo = styled.iframe`
  ${paintSkeleton}
  width: 352px;
  height: 196px;

  border-radius: ${BORDER_RADIUS.md};
`;
