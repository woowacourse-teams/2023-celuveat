import { useRef } from 'react';
import { styled, css } from 'styled-components';
import { useParams, useSearchParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';

import { Helmet } from 'react-helmet-async';
import Header from './Header';
import DetailInformation from './DetailInformation';
import NearByRestaurantCardList from './NearByRestaurantCardList';
import RestaurantVideoList from './RestaurantVideoList';
import ImageViewer from './ImageViewer';
import SuggestionButton from '~/components/SuggestionButton';
import RestaurantReviewWrapper from '~/components/RestaurantReviewWrapper';
import RestaurantDetailLikeButton from '~/components/RestaurantDetailLikeButton';

import Naver from '~/assets/icons/oauth/naver.svg';

import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import useMediaQuery from '~/hooks/useMediaQuery';
import ReviewModalProvider from '~/hooks/context/ReviewModalProvider';
import { getRestaurantDetail } from '~/api/restaurant';

import type { RestaurantData } from '~/@types/api.types';
import MapSection from './MapSection';

function RestaurantDetail() {
  const layoutRef = useRef();
  const { isMobile } = useMediaQuery();
  const { id: restaurantId } = useParams();
  const [searchParams] = useSearchParams();
  const celebId = searchParams.get('celebId');

  const {
    data: { celebs, ...restaurant },
  } = useQuery<RestaurantData>({
    queryKey: ['restaurantDetail', restaurantId, celebId],
    queryFn: async () => getRestaurantDetail(restaurantId, celebId),
    cacheTime: 0,
    suspense: true,
  });

  const openNewWindow =
    (url: string): React.MouseEventHandler<HTMLButtonElement> =>
    () =>
      window.open(url, '_blank');

  return (
    <>
      <Helmet>
        <meta property="og:title" content={`${restaurant.name}`} />
        <meta property="og:url" content="celuveat.com" />
        <meta
          name="image"
          property="og:image"
          content={`https://www.celuveat.com/images-data/${restaurant.images[0].name}.jpeg`}
        />
        <meta name="description" property="og:description" content={`${celebs[0].name}이 추천한 맛집`} />
      </Helmet>
      <StyledMainRestaurantDetail isMobile={isMobile} ref={layoutRef}>
        <Header name={restaurant.name} viewCount={restaurant.viewCount} likeCount={restaurant.likeCount} />

        <ImageViewer images={restaurant.images} />

        <StyledDetailAndLink isMobile={isMobile}>
          <DetailInformation
            restaurantId={restaurantId}
            celebs={celebs}
            roadAddress={restaurant.roadAddress}
            phoneNumber={restaurant.phoneNumber}
            category={restaurant.category}
          />
          <StyledLinkContainer isMobile={isMobile} tabIndex={0}>
            <StyledMainLinkContainer isMobile={isMobile}>
              <RestaurantDetailLikeButton restaurant={restaurant} />
              <button type="button" onClick={openNewWindow(restaurant.naverMapUrl)}>
                <Naver width={32} />
                <div>네이버 지도로 보기</div>
              </button>
            </StyledMainLinkContainer>
            <SuggestionButton />
          </StyledLinkContainer>
        </StyledDetailAndLink>

        <RestaurantVideoList restaurantId={restaurantId} celebId={celebId} />

        <NearByRestaurantCardList restaurantId={restaurantId} />

        <MapSection lat={restaurant.lat} lng={restaurant.lng} />

        <ReviewModalProvider>
          <RestaurantReviewWrapper />
        </ReviewModalProvider>
      </StyledMainRestaurantDetail>

      {isMobile && (
        <StyledMobileLikeButtonBox>
          <RestaurantDetailLikeButton showText={false} restaurant={restaurant} />
        </StyledMobileLikeButtonBox>
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

const StyledMobileLikeButtonBox = styled.div`
  display: flex;
  justify-content: end;

  position: sticky;
  bottom: 60px;

  padding: 0 2.4rem;

  & > button {
    display: flex;
    justify-content: center;
    align-items: center;

    width: 64px;
    height: 64px;

    border: none;
    border-radius: 50%;

    font-family: SUIT-Medium, sans-serif;
    font-size: ${FONT_SIZE.md};

    & > div {
      color: var(--white);
    }

    &:first-child {
      background: var(--red-2);
    }
  }
`;
