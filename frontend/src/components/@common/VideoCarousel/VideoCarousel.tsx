import { styled } from 'styled-components';
import { BORDER_RADIUS } from '~/styles/common';

interface VideoCarouselProps {
  title: string;
  videos: { videoId: number; youtubeUrl: string; uploadDate: string; name: string }[];
}

function VideoCarousel({ title, videos }: VideoCarouselProps) {
  return (
    <StyledVideoCarouselContainer>
      <h5>{title}</h5>
      <ul>
        {videos.map(({ name, youtubeUrl }) => (
          <li>
            <StyledVideo
              title={`${name}의 영상`}
              src={youtubeUrl}
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
    display: flex;
    gap: 0 2rem;
    overflow-x: scroll;
  }
`;

const StyledVideo = styled.iframe`
  width: 352px;
  height: 196px;

  border-radius: ${BORDER_RADIUS.md};
`;
