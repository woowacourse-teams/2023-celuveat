import styled from 'styled-components';
import { useQuery } from '@tanstack/react-query';
import { VideoList } from '~/@types/api.types';
import { Celeb } from '~/@types/celeb.types';
import ProfileImageList from '~/components/@common/ProfileImageList';
import useMediaQuery from '~/hooks/useMediaQuery';
import Copy from '~/assets/icons/copy.svg';
import Youtube from '~/assets/icons/youtube.svg';
import { BORDER_RADIUS, FONT_SIZE, paintSkeleton } from '~/styles/common';
import { getRestaurantVideo } from '~/api/restaurant';
import SuggestionButton from '~/components/SuggestionButton';

interface DetailInformationProps {
  restaurantId: string;
  celebs: Celeb[];
  roadAddress: string;
  phoneNumber: string;
  category: string;
}

function DetailInformation({ restaurantId, celebs, roadAddress, phoneNumber, category }: DetailInformationProps) {
  const { isMobile } = useMediaQuery();

  const { data: restaurantVideo } = useQuery<VideoList>({
    queryKey: ['restaurantVideo', restaurantId],
    queryFn: async () => getRestaurantVideo(restaurantId),
    suspense: true,
  });

  const openNewWindow =
    (url: string): React.MouseEventHandler<HTMLButtonElement> =>
    () =>
      window.open(url, '_blank');

  const copyClipBoard =
    (text: string): React.MouseEventHandler<HTMLButtonElement> =>
    async () => {
      try {
        await navigator.clipboard.writeText(text);
        alert('클립보드에 저장되었어요.');
      } catch (err) {
        alert('클립보드에 저장하는데 문제가 생겼어요.');
      }
    };

  return (
    <StyledDetailInfo isMobile={isMobile} tabIndex={0} aria-label="음식정 상세 정보">
      <div>
        <div>
          <h4>{celebs[0].name}</h4>
          <div>
            <div>{celebs[0].youtubeChannelName}</div>
            <div>|</div>
            <button type="button" onClick={openNewWindow(`https://www.youtube.com/${celebs[0].youtubeChannelName}`)}>
              <Youtube width={28} />
              <div>유튜브 바로가기</div>
            </button>
          </div>
        </div>
        <ProfileImageList celebs={celebs} size="56px" />
      </div>
      <div>
        <div>
          주소 : {roadAddress}
          <button aria-label="주소 복사" type="button" onClick={copyClipBoard(roadAddress)}>
            <Copy width={16} />
            복사
          </button>
        </div>
        <div>
          전화번호 : {phoneNumber === '' ? '아직 등록되지 않았어요.' : phoneNumber}
          <button aria-label="전화번호 복사" type="button" onClick={copyClipBoard(phoneNumber)}>
            <Copy width={16} />
            복사
          </button>
        </div>
        <div>카테고리 : {category}</div>
        <SuggestionButton />
      </div>
      <StyledMainVideo>
        <h5>영상으로 보기</h5>
        <iframe
          title={`${restaurantVideo.content[0].name}의 영상`}
          src={`https://www.youtube.com/embed/${restaurantVideo.content[0].youtubeVideoKey}`}
          allow="encrypted-media; gyroscope; picture-in-picture"
          allowFullScreen
        />
      </StyledMainVideo>
    </StyledDetailInfo>
  );
}

export default DetailInformation;

const StyledDetailInfo = styled.section<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;

  width: ${({ isMobile }) => (isMobile ? '100%' : '58%')};

  padding-top: 2.4rem;

  & > div:first-child {
    display: flex;
    justify-content: space-between;

    padding: 2.4rem 0;
    border-bottom: 1px solid var(--gray-2);
  }

  & > div:nth-child(2) {
    display: flex;
    flex-direction: column;
    gap: 1.2rem 0;

    padding: 3.2rem 0;

    font-size: ${FONT_SIZE.md};
    border-bottom: 1px solid var(--gray-2);

    & > div {
      display: inline-block;

      line-height: 20px;

      & > button {
        border: none;
        background: none;

        color: #60bf48;

        vertical-align: -1px;

        text-align: start;
      }
    }
  }

  & > div:first-child > div {
    display: flex;
    flex-direction: column;
    gap: 1.2rem;

    & > div {
      display: flex;
      align-items: center;
      gap: 0 0.8rem;

      font-size: ${FONT_SIZE.md};
    }

    & > div > button {
      display: flex;
      align-items: center;
      gap: 0 0.4rem;

      padding: 0;

      border: none;
      background: none;
    }
  }
`;

const StyledMainVideo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem 0;

  width: 100%;

  padding: 2.4rem 0;
  border-bottom: 1px solid var(--gray-2);

  & > iframe {
    ${paintSkeleton}
    width: 100%;
    aspect-ratio: 1 / 0.556;

    border-radius: ${BORDER_RADIUS.md};
  }
`;
