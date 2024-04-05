import { useQuery } from '@tanstack/react-query';
import Slider from 'react-slick';
import styled from 'styled-components';
import { RestaurantData } from '~/@types/api.types';
import { getRestaurantDetail } from '~/api/restaurant';
import ImageCarousel from '~/components/@common/ImageCarousel';
import WaterMarkImage from '~/components/@common/WaterMarkImage';
import useMediaQuery from '~/hooks/useMediaQuery';
import Next from '~/assets/icons/arrow/next.svg';
import Prev from '~/assets/icons/arrow/prev.svg';
import { BORDER_RADIUS } from '~/styles/common';
import noImageUrl from '~/assets/images/no-image.webp';

interface ImageViewerProps {
  restaurantId: string;
  celebId: string;
}

const sliderProps = {
  arrows: true,
  dots: true,
  infinite: true,
  speed: 1000,
  slidesToShow: 2,
  slidesToScroll: 2,
  nextArrow: <Next />,
  prevArrow: <Prev />,
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

  if (isMobile) return <ImageCarousel type="list" images={images} showWaterMark />;

  if (images.length === 1) {
    const { name: url, author, sns } = images[0];

    return (
      <Slider {...sliderProps}>
        <SlideItem>
          <WaterMarkImage type="list" imageUrl={url} waterMark={author} sns={sns} />
        </SlideItem>
        <SlideItem>
          <StyledNoImage src={noImageUrl} />
        </SlideItem>
      </Slider>
    );
  }

  return (
    <Slider {...sliderProps}>
      {images.map(({ name: url, author, sns }) => (
        <SlideItem key={url}>
          <WaterMarkImage type="list" imageUrl={url} waterMark={author} sns={sns} />
        </SlideItem>
      ))}
    </Slider>
  );
}

export default ImageViewer;

const SlideItem = styled.li`
  padding: 1.2rem;
`;

const StyledNoImage = styled.img`
  width: 100%;

  object-fit: cover;

  border-radius: ${BORDER_RADIUS.md};
`;
