import { Link } from 'react-router-dom';
import Slider from 'react-slick';
import styled, { css } from 'styled-components';
import { SERVER_IMG_URL } from '~/constants/url';
import useMediaQuery from '~/hooks/useMediaQuery';
import Next from '~/assets/icons/arrow/next.svg';
import Prev from '~/assets/icons/arrow/prev.svg';

function BannerSlider() {
  const { isMobile } = useMediaQuery();

  const settings = {
    arrows: true,
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    nextArrow: <Next />,
    prevArrow: <Prev />,
  };
  return (
    <Slider {...settings}>
      <Link to="/updated-recent">
        <StyledBanner
          alt="최근 업데이트된 맛집"
          src={`${SERVER_IMG_URL}banner/recent-updated.jpg`}
          isMobile={isMobile}
        />
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
          height: 240px;

          margin: 1.2rem;
        `}
`;
