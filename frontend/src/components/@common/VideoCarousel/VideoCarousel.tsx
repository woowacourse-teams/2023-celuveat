import { styled } from 'styled-components';
import Slider from 'react-slick';
import { BORDER_RADIUS, hideScrollBar, paintSkeleton } from '~/styles/common';
import type { Video } from '~/@types/api.types';
import { VideoCarouselSettings } from '~/constants/carouselSettings';
import useMediaQuery from '~/hooks/useMediaQuery';

interface VideoCarouselProps {
  title: string;
  videos: Video[];
}

function VideoCarousel({ title, videos }: VideoCarouselProps) {
  const { isMobile } = useMediaQuery();
  return (
    <>
      <h5>{title}</h5>
      {isMobile || videos.length <= 2 ? (
        <StyledVideoContainer>
          {videos.map(({ name, youtubeVideoKey }) => (
            <StyledVideo
              title={`${name}의 영상`}
              src={`https://www.youtube.com/embed/${youtubeVideoKey}`}
              allow="encrypted-media; gyroscope; picture-in-picture"
              allowFullScreen
            />
          ))}
        </StyledVideoContainer>
      ) : (
        <StyledSliderContainer>
          <Slider {...VideoCarouselSettings}>
            {videos.map(({ name, youtubeVideoKey }) => (
              <StyledVideoWrapper>
                <StyledVideo
                  title={`${name}의 영상`}
                  src={`https://www.youtube.com/embed/${youtubeVideoKey}`}
                  allow="encrypted-media; gyroscope; picture-in-picture"
                  allowFullScreen
                />
              </StyledVideoWrapper>
            ))}
          </Slider>
        </StyledSliderContainer>
      )}
    </>
  );
}

export default VideoCarousel;

const StyledSliderContainer = styled.div`
  padding: 0 2rem;
`;

const StyledVideoWrapper = styled.div`
  display: flex !important;
  justify-content: center;

  width: 100%;
`;

const StyledVideo = styled.iframe`
  ${paintSkeleton}
  width: 352px;
  max-width: 352px;
  height: 196px;

  border-radius: ${BORDER_RADIUS.md};
`;

const StyledVideoContainer = styled.div`
  display: flex;
  gap: 2rem;

  width: 100%;
  overflow: scroll;

  ${hideScrollBar}
`;
