import { useRef } from 'react';
import { styled, css } from 'styled-components';
import { useParams, useSearchParams } from 'react-router-dom';

import Header from './Header';
import DetailInformation from './DetailInformation';
import NearByRestaurantCardList from './NearByRestaurantCardList';
import RestaurantVideoList from './RestaurantVideoList';
import ImageViewer from './ImageViewer';
import RestaurantReviewWrapper from '~/components/RestaurantReviewWrapper';
import RestaurantDetailLikeButton from '~/components/RestaurantDetailLikeButton';

import { FONT_SIZE } from '~/styles/common';
import useMediaQuery from '~/hooks/useMediaQuery';
import ReviewModalProvider from '~/hooks/context/ReviewModalProvider';

import MapSection from './MapSection';
import LinkContainer from './LinkContainer';

function RestaurantDetail() {
  const layoutRef = useRef();
  const { isMobile } = useMediaQuery();
  const { id: restaurantId } = useParams();
  const [searchParams] = useSearchParams();
  const celebId = searchParams.get('celebId');

  return (
    <>
      <StyledMainRestaurantDetail isMobile={isMobile} ref={layoutRef}>
        <Header restaurantId={restaurantId} celebId={celebId} />
        <ImageViewer restaurantId={restaurantId} celebId={celebId} />
        <StyledDetailAndLink isMobile={isMobile}>
          <DetailInformation restaurantId={restaurantId} celebId={celebId} />
          <LinkContainer isMobile={isMobile} restaurantId={restaurantId} celebId={celebId} />
        </StyledDetailAndLink>
        <RestaurantVideoList restaurantId={restaurantId} celebId={celebId} />
        <NearByRestaurantCardList restaurantId={restaurantId} />
        <MapSection restaurantId={restaurantId} celebId={celebId} />
        <ReviewModalProvider>
          <RestaurantReviewWrapper />
        </ReviewModalProvider>
      </StyledMainRestaurantDetail>

      {isMobile && (
        <StyledMobileLikeButtonBox>
          <RestaurantDetailLikeButton showText={false} restaurantId={restaurantId} celebId={celebId} />
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
