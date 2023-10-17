import { Link } from 'react-router-dom';
import Slider from 'react-slick';
import styled, { css } from 'styled-components';
import { SERVER_IMG_URL } from '~/constants/url';
import useMediaQuery from '~/hooks/useMediaQuery';
import { BannerCarouselSettings } from '~/constants/carouselSettings';

function BannerSlider() {
  const { isMobile } = useMediaQuery();

  return (
    <Slider {...BannerCarouselSettings}>
      <Link to="/updated-recent">
        <StyledBanner
          alt="최근 업데이트된 맛집"
          src={`${SERVER_IMG_URL}banner/recent-updated.jpg`}
          isMobile={isMobile}
        />
      </Link>
      <Link to="/event">
        <StyledBanner alt="사진 등록 이벤트" src={`${SERVER_IMG_URL}banner/event-banner.jpeg`} isMobile={isMobile} />
      </Link>
    </Slider>
  );
}

export default BannerSlider;

const StyledBanner = styled.img<{ isMobile: boolean }>`
  width: 100%;

  object-fit: cover;

  overflow: hidden;

  ${({ isMobile }) =>
    isMobile
      ? css`
          height: 200px;
        `
      : css`
          height: 300px;
        `}
`;
