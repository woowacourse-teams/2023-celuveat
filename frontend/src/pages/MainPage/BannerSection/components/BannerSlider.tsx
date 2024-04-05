import { useNavigate } from 'react-router-dom';
import Slider from 'react-slick';
import styled, { css } from 'styled-components';
import { SERVER_IMG_URL } from '~/constants/url';
import useMediaQuery from '~/hooks/useMediaQuery';
import { BannerCarouselSettings } from '~/constants/carouselSettings';
import { paintSkeleton } from '~/styles/common';
import useOnClickBlock from '~/hooks/useOnClickBlock';

function BannerSlider() {
  const { isMobile } = useMediaQuery();
  const navigate = useNavigate();
  const register1 = useOnClickBlock({
    callback: () => {
      navigate('/updated-recent');
    },
  });
  const register2 = useOnClickBlock({
    callback: () => {
      navigate('/event');
    },
  });

  return (
    <Slider {...BannerCarouselSettings} arrows={!isMobile}>
      <StyledBanner
        alt="최근 업데이트된 맛집"
        src={`${SERVER_IMG_URL}banner/recent-updated.jpg`}
        isMobile={isMobile}
        {...register1}
      />

      <StyledBanner
        alt="사진 등록 이벤트"
        src={`${SERVER_IMG_URL}banner/event-banner.jpeg`}
        isMobile={isMobile}
        {...register2}
      />
    </Slider>
  );
}

export default BannerSlider;

const StyledBanner = styled.img<{ isMobile: boolean }>`
  width: 100%;

  object-fit: cover;

  overflow: hidden;

  cursor: pointer;

  ${({ isMobile }) =>
    isMobile
      ? css`
          height: 200px;
        `
      : css`
          height: 300px;
        `}

  ${paintSkeleton}
`;
