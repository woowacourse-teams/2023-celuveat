import { Suspense, useRef } from 'react';
import { styled, css } from 'styled-components';
import { Wrapper } from '@googlemaps/react-wrapper';
import { Link, useParams, useSearchParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import Logo from '~/assets/icons/logo-icon.svg';

import Footer from '~/components/@common/Footer';
import { Header } from '~/components/@common/Header';
import ImageGrid from '~/components/@common/ImageGrid';
import VideoCarousel from '~/components/@common/VideoCarousel';
import RestaurantCard from '~/components/RestaurantCard';
import MapContent from '~/components/@common/Map/MapContent';
import ProfileImageList from '~/components/@common/ProfileImageList';
import SuggestionButton from '~/components/SuggestionButton';
import RestaurantDetailLikeButton from '~/components/RestaurantDetailLikeButton';
import ImageCarousel from '~/components/@common/ImageCarousel';

import View from '~/assets/icons/view.svg';
import Copy from '~/assets/icons/copy.svg';
import Love from '~/assets/icons/black-love.svg';
import Naver from '~/assets/icons/oauth/naver.svg';
import Youtube from '~/assets/icons/youtube.svg';

import { BORDER_RADIUS, FONT_SIZE, hideScrollBar, paintSkeleton } from '~/styles/common';
import useMediaQuery from '~/hooks/useMediaQuery';
import ReviewModalProvider from '~/hooks/context/ReviewModalProvider';

import type { RestaurantData, RestaurantListData, VideoList } from '~/@types/api.types';
import RestaurantReviewWrapper from '~/components/RestaurantReviewWrapper';
import useScrollDirection from '~/hooks/useScrollDirection';
import { getNearByRestaurant, getRestaurantDetail, getRestaurantVideo } from '~/api/restaurant';
import { getCelebVideo } from '~/api/celeb';

function RestaurantDetail() {
  const layoutRef = useRef();
  const sheetRef = useRef<HTMLDivElement>();
  const { isMobile } = useMediaQuery();
  const { id: restaurantId } = useParams();
  const [searchParams] = useSearchParams();
  const celebId = searchParams.get('celebId');
  const scrollDirection = useScrollDirection();

  const {
    data: {
      id,
      distance,
      name,
      viewCount,
      isLiked,
      likeCount,
      images,
      celebs,
      roadAddress,
      category,
      phoneNumber,
      naverMapUrl,
      lat,
      lng,
    } = {},
    isSuccess: isSuccessRestaurantDetail,
  } = useQuery<RestaurantData>({
    queryKey: ['restaurantDetail', restaurantId, celebId],
    queryFn: async () => getRestaurantDetail(restaurantId, celebId),
    cacheTime: 0,
  });

  const { data: nearByRestaurant, isSuccess: isSuccessNearByRestaurant } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', 'nearby', restaurantId],
    queryFn: async () => getNearByRestaurant(restaurantId),
  });

  const { data: celebVideo, isSuccess: isSuccessCelebVideo } = useQuery<VideoList>({
    queryKey: ['celebVideo', celebId],
    queryFn: async () => getCelebVideo(celebId),
  });

  const { data: restaurantVideo, isSuccess: isSuccessRestaurantVideo } = useQuery<VideoList>({
    queryKey: ['restaurantVideo', restaurantId],
    queryFn: async () => getRestaurantVideo(restaurantId),
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
    <>
      {isMobile ? (
        <StyledMobileHeader>
          <Link aria-label="셀럽잇 홈페이지" role="button" to="/">
            <Logo width={32} />
          </Link>
          <h5>celuveat</h5>
          <div />
        </StyledMobileHeader>
      ) : (
        <Header />
      )}
      <>
        <StyledMainRestaurantDetail isMobile={isMobile} ref={layoutRef}>
          {isSuccessRestaurantDetail && (
            <>
              <StyledDetailHeader tabIndex={0}>
                <h3>{name}</h3>
                <div role="group">
                  <div aria-label={`조회수 ${viewCount}`}>
                    <View width={18} aria-hidden /> <span aria-hidden>{viewCount}</span>
                  </div>
                  <div aria-label={`좋아요수 ${viewCount}`}>
                    <Love width={18} aria-hidden /> <span aria-hidden>{likeCount}</span>
                  </div>
                </div>
              </StyledDetailHeader>
              {isMobile ? (
                <ImageCarousel type="list" images={images} />
              ) : (
                <ImageGrid images={images.map(({ name: url, author, sns }) => ({ waterMark: author, url, sns }))} />
              )}
              <StyledDetailAndLink isMobile={isMobile}>
                <StyledDetailInfo isMobile={isMobile} tabIndex={0} aria-label="음식정 상세 정보">
                  <div>
                    <div>
                      <h4>{celebs[0].name}</h4>
                      <div>
                        <div>{celebs[0].youtubeChannelName}</div>
                        <div>|</div>
                        <button
                          type="button"
                          onClick={openNewWindow(`https://www.youtube.com/${celebs[0].youtubeChannelName}`)}
                        >
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
                  </div>
                  {isSuccessRestaurantVideo && (
                    <StyledMainVideo>
                      <h5>영상으로 보기</h5>
                      <iframe
                        title={`${restaurantVideo.content[0].name}의 영상`}
                        src={`https://www.youtube.com/embed/${restaurantVideo.content[0].youtubeVideoKey}`}
                        allow="encrypted-media; gyroscope; picture-in-picture"
                        allowFullScreen
                      />
                    </StyledMainVideo>
                  )}
                </StyledDetailInfo>
                <StyledLinkContainer isMobile={isMobile} tabIndex={0}>
                  <StyledMainLinkContainer isMobile={isMobile}>
                    <RestaurantDetailLikeButton
                      restaurant={{
                        id,
                        distance,
                        name,
                        images,
                        roadAddress,
                        isLiked,
                        category,
                        phoneNumber,
                        naverMapUrl,
                        lat,
                        lng,
                        likeCount,
                        viewCount,
                      }}
                    />
                    <button type="button" onClick={openNewWindow(naverMapUrl)}>
                      <Naver width={32} />
                      <div>네이버 지도로 보기</div>
                    </button>
                  </StyledMainLinkContainer>
                  <SuggestionButton />
                </StyledLinkContainer>
              </StyledDetailAndLink>
            </>
          )}
          <StyledVideoSection>
            {isSuccessRestaurantVideo && restaurantVideo.totalElementsCount > 1 && (
              <VideoCarousel
                title={`이외에 ${restaurantVideo.currentElementsCount - 1}명의 셀럽이 다녀갔어요!`}
                videos={restaurantVideo.content.slice(1)}
              />
            )}
            {isSuccessCelebVideo && isSuccessCelebVideo && (
              <VideoCarousel
                title="이 셀럽의 다른 음식점 영상"
                videos={celebVideo.content.filter(({ videoId }) => videoId !== restaurantVideo?.content[0].videoId)}
              />
            )}
          </StyledVideoSection>
          {isSuccessNearByRestaurant && nearByRestaurant.totalElementsCount > 0 && (
            <StyledNearByRestaurant>
              <h5>주변 다른 식당</h5>
              <ul>
                {nearByRestaurant.content.map(restaurant => (
                  <StyledRestaurantCardContainer>
                    <RestaurantCard type="map" restaurant={restaurant} celebs={restaurant.celebs} size="36px" />
                  </StyledRestaurantCardContainer>
                ))}
              </ul>
            </StyledNearByRestaurant>
          )}
          <Suspense fallback={<div>Loading...</div>}>
            <ReviewModalProvider>
              <RestaurantReviewWrapper />
            </ReviewModalProvider>
          </Suspense>
          {isSuccessRestaurantDetail && (
            <StyledMapSection>
              <h5>위치 확인하기</h5>
              <div>
                <Wrapper apiKey={process.env.GOOGLE_MAP_API_KEY} language="ko" libraries={['places']}>
                  <MapContent
                    center={{ lat, lng }}
                    zoom={17}
                    style={{ width: '100%', height: isMobile ? '300px' : '600px' }}
                    markers={[{ lat, lng }]}
                    gestureHandling="cooperative"
                  />
                </Wrapper>
              </div>

              {isMobile && (
                <>
                  <StyledButton type="button" onClick={openNewWindow(naverMapUrl)}>
                    <Naver width={32} />
                    <div>네이버 지도로 보기</div>
                  </StyledButton>
                  <SuggestionButton />
                </>
              )}
            </StyledMapSection>
          )}
        </StyledMainRestaurantDetail>
        <Footer />
      </>
      {isMobile && isSuccessRestaurantDetail && (
        <StyledMobileBottomSheet ref={sheetRef} movingDirection={scrollDirection.y}>
          <StyledMainLinkContainer isMobile={isMobile}>
            <RestaurantDetailLikeButton
              restaurant={{
                id,
                distance,
                name,
                images,
                roadAddress,
                isLiked,
                category,
                phoneNumber,
                naverMapUrl,
                lat,
                viewCount,
                likeCount,
                lng,
              }}
            />
          </StyledMainLinkContainer>
        </StyledMobileBottomSheet>
      )}
    </>
  );
}

export default RestaurantDetail;

const StyledMainRestaurantDetail = styled.main<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;
  ${({ isMobile }) =>
    isMobile
      ? css`
          position: sticky;

          margin: 0 1.2rem 2rem;
        `
      : css`
          max-width: 1240px;

          margin: 0 auto 8rem;
        `}
`;

const StyledDetailHeader = styled.section`
  display: flex;
  flex-direction: column;
  gap: 0.8rem 0;

  padding: 2.4rem 0;

  & > div {
    display: flex;
    align-items: center;
    gap: 0 0.8rem;

    font-size: ${FONT_SIZE.md};

    & > div {
      display: flex;
      align-items: center;
      gap: 0 0.8rem;
    }
  }
`;

const StyledDetailAndLink = styled.section<{ isMobile: boolean }>`
  display: flex;
  justify-content: space-between;
  margin-bottom: 3.2rem;

  ${({ isMobile }) =>
    isMobile &&
    css`
      flex-direction: column;
    `}
`;

const StyledVideoSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 3.2rem 0;
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

const StyledLinkContainer = styled.aside<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;

  height: 100%;

  ${({ isMobile }) =>
    isMobile
      ? css`
          display: none;
        `
      : css`
          position: sticky;
          top: 80px;

          width: 33%;
        `}
`;

const StyledMainLinkContainer = styled.div<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;

  width: 100%;

  padding: 2.4rem;

  ${({ isMobile }) =>
    isMobile
      ? css`
          border-radius: ${BORDER_RADIUS.md} ${BORDER_RADIUS.md} 0 0;
          background: var(--white);
        `
      : css`
          margin-top: 4.8rem;

          border: 1px solid var(--gray-2);
          border-radius: ${BORDER_RADIUS.md};
        `}

  box-shadow: var(--shadow);

  & > button {
    display: flex;
    align-items: center;
    gap: 0 4rem;

    padding: 1.6rem 3.2rem;

    border: none;
    border-radius: ${BORDER_RADIUS.md};

    font-family: SUIT-Medium, sans-serif;
    font-size: ${FONT_SIZE.md};

    & > div {
      color: var(--white);
    }

    &:first-child {
      background: var(--red-2);
    }

    &:nth-child(2) {
      background: #03c75a;
    }
  }
`;

const StyledNearByRestaurant = styled.section`
  display: flex;
  flex-direction: column;

  margin: 3.2rem 0;

  & > ul {
    ${hideScrollBar}
    display: flex;
    gap: 0 2rem;
    overflow-x: scroll;

    padding: 2rem 0;

    & > * {
      min-width: 300px;

      box-shadow: var(--shadow);
    }
  }
`;

const StyledMapSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 2rem 0;

  & > div {
    border-radius: ${BORDER_RADIUS.md};
    overflow: hidden;
  }
`;

const StyledMobileBottomSheet = styled.section<{
  movingDirection: string;
}>`
  position: fixed;
  bottom: 0;
  left: 0;
  z-index: 100;

  width: 100%;

  transition: transform 0.3s ease-in-out;
  transform: ${({ movingDirection }) => (movingDirection === 'up' ? 'translateY(100%)' : 'translateY(0)')};
`;

const StyledButton = styled.button`
  display: flex;
  align-items: center;
  gap: 0 4rem;

  padding: 1.6rem 3.2rem;

  border: none;
  border-radius: ${BORDER_RADIUS.md};
  background: #03c75a;

  font-family: SUIT-Medium, sans-serif;
  font-size: ${FONT_SIZE.md};

  & > div {
    color: var(--white);
  }
`;

const StyledRestaurantCardContainer = styled.div`
  border-radius: 12px;

  box-shadow: var(--map-shadow);
`;

const StyledMobileHeader = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;

  position: sticky;
  top: 0;
  left: 0;
  z-index: 10;

  width: 100%;
  height: 44px;

  padding: 0.2rem 0.8rem;

  background-color: var(--white);
  box-shadow: var(--map-shadow);

  & > div:last-child {
    width: 32px;
  }
`;
