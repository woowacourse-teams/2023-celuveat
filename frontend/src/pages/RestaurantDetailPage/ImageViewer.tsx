import { useQuery } from '@tanstack/react-query';
import Slider from 'react-slick';
import styled from 'styled-components';
import { RestaurantData } from '~/@types/api.types';
import { getRestaurantDetail } from '~/api/restaurant';
import WaterMarkImage from '~/components/@common/WaterMarkImage';
import Next from '~/assets/icons/arrow/next.svg';
import Prev from '~/assets/icons/arrow/prev.svg';
import { BORDER_RADIUS } from '~/styles/common';
import noImageUrl from '~/assets/images/no-image.webp';
import useMediaQuery from '~/hooks/useMediaQuery';

interface ImageViewerProps {
  restaurantId: string;
  celebId: string;
}

const sliderProps = {
  arrows: true,
  dots: true,
  infinite: true,
  speed: 500,
  slidesToShow: 2,
  slidesToScroll: 2,
  nextArrow: <Next />,
  prevArrow: <Prev />,
  responsive: [
    {
      breakpoint: 780,
      settings: {
        arrows: false,
        slidesToShow: 1,
        slidesToScroll: 1,
        speed: 300,
      },
    },
  ],
};

function ImageViewer({ restaurantId, celebId }: ImageViewerProps) {
  const { isMobile } = useMediaQuery();

  const {
    data: { images },
  } = useQuery<RestaurantData>({
    queryKey: ['restaurantDetail', restaurantId, celebId],
    queryFn: async () => getRestaurantDetail(restaurantId, celebId),
    suspense: true,
  });

  if (images.length === 1) {
    const { name: url, author, sns } = images[0];

    return (
      <Slider {...sliderProps}>
        <SlideItem isMobile={isMobile}>
          <WaterMarkImage type="list" imageUrl={url} waterMark={author} sns={sns} />
        </SlideItem>
        <SlideItem isMobile={isMobile}>
          <StyledNoImage src={noImageUrl} />
        </SlideItem>
      </Slider>
    );
  }

  return (
    <Slider {...sliderProps}>
      {images.map(({ name: url, author, sns }) => (
        <SlideItem key={url} isMobile={isMobile}>
          <WaterMarkImage type="list" imageUrl={url} waterMark={author} sns={sns} />
        </SlideItem>
      ))}
    </Slider>
  );
}

export default ImageViewer;

const SlideItem = styled.li<{ isMobile: boolean }>`
  padding: ${({ isMobile }) => (isMobile ? '0' : '1.2rem')};
`;

const StyledNoImage = styled.img`
  width: 100%;

  object-fit: cover;

  border-radius: ${BORDER_RADIUS.md};
`;
