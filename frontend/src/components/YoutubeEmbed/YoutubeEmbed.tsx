import styled from 'styled-components';
import { BORDER_RADIUS, paintSkeleton } from '~/styles/common';

interface YoutubeEmbedProps {
  title: string;
  pathParam: string;
}

function YoutubeEmbed({ title, pathParam }: YoutubeEmbedProps) {
  return (
    <StyledVideo
      key={pathParam}
      title={`${title}의 영상`}
      src={`https://www.youtube.com/embed/${pathParam}`}
      allow="encrypted-media; gyroscope; picture-in-picture"
      allowFullScreen
    />
  );
}

export default YoutubeEmbed;

const StyledVideo = styled.iframe`
  ${paintSkeleton}
  border-radius: ${BORDER_RADIUS.md};
`;
