import { styled } from 'styled-components';
import Slider from 'react-slick';
import { hideScrollBar } from '~/styles/common';
import type { Video } from '~/@types/api.types';
import { VideoCarouselSettings } from '~/constants/carouselSettings';
import useMediaQuery from '~/hooks/useMediaQuery';
import YoutubeEmbed from '~/components/YoutubeEmbed';

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
            <YoutubeEmbed pathParam={youtubeVideoKey} title={name} />
          ))}
        </StyledVideoContainer>
      ) : (
        <StyledSliderContainer>
          <Slider {...VideoCarouselSettings}>
            {videos.map(({ name, youtubeVideoKey }) => (
              <StyledVideoWrapper key={youtubeVideoKey}>
                <YoutubeEmbed pathParam={youtubeVideoKey} title={name} />
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

const StyledVideoContainer = styled.div`
  display: flex;
  gap: 2rem;

  width: 100%;
  overflow: scroll;

  ${hideScrollBar}
`;
