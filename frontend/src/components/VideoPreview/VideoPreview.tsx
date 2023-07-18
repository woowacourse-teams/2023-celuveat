import { styled } from 'styled-components';
import ProfileImage from '../@common/ProfileImage';
import { BORDER_RADIUS, FONT_SIZE, truncateText } from '~/styles/common';
import formatDateToKorean from '~/utils/formatDateToKorean';
import useBooleanState from '~/hooks/useBooleanState';

interface VideoPreviewProps {
  title: string;
  celebName: string;
  videoUrl: string;
  thumbnailUrl: string;
  profileImageUrl: string;
  uploadDate: string;
  viewCount: number;
}

function VideoPreview({
  title,
  celebName,
  videoUrl,
  thumbnailUrl,
  viewCount,
  uploadDate,
  profileImageUrl,
}: VideoPreviewProps) {
  const { value: isClicked, setTrue: setClickTrue } = useBooleanState(false);

  return (
    <StyledContainer>
      {isClicked ? (
        <iframe
          title={title}
          width="352"
          height="196"
          src={videoUrl}
          allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
          allowFullScreen
        />
      ) : (
        <StyledVideoCover type="button" onClick={setClickTrue}>
          <img alt={`영상 시작 ${title}`} src={thumbnailUrl} />
        </StyledVideoCover>
      )}
      <StyledVideoInfo>
        <ProfileImage name={celebName} imageUrl={profileImageUrl} size={38} />
        <StyledTitle>{title}</StyledTitle>
        <StyledViewAndDate>
          <div>{celebName}</div>
          <div>
            조회수 {viewCount > 10_000 ? `${viewCount / 10_000}만회` : `${viewCount}회`} ∙ 게시일{' '}
            {formatDateToKorean(uploadDate)}
          </div>
        </StyledViewAndDate>
      </StyledVideoInfo>
    </StyledContainer>
  );
}

export default VideoPreview;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.2rem 0;

  width: 352px;

  & > iframe {
    border-radius: ${BORDER_RADIUS.md};
  }
`;

const StyledVideoCover = styled.button`
  overflow: hidden;

  width: 352px;
  height: 196px;

  padding: 0;

  border: none;
  border-radius: ${BORDER_RADIUS.md};

  & > img {
    width: 100%;
    object-fit: cover;
  }
`;

const StyledVideoInfo = styled.div`
  display: grid;
  grid-template-columns: auto 1fr;

  gap: 1.2rem;

  width: 100%;

  & > *:first-child {
    grid-area: 1 / 1 / span 2 / span 1;
  }
`;

const StyledTitle = styled.div`
  ${truncateText(2)}

  grid-area: 1 / 2 / span 1 / span 1;

  font-size: ${FONT_SIZE.md};
`;

const StyledViewAndDate = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.2rem 0;
  grid-area: 2 / 2 / span 1 / span 1;

  font-size: ${FONT_SIZE.sm};

  & > * {
    ${truncateText(1)}
    color: var(--gray-4);
  }
`;
