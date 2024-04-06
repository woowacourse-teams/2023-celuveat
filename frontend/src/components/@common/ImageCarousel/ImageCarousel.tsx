import { styled } from 'styled-components';
import Slider from 'react-slick';
import { RestaurantImage } from '~/@types/image.type';
import WaterMarkImage from '../WaterMarkImage';
import { BORDER_RADIUS } from '~/styles/common';

interface ImageCarouselProps {
  images: RestaurantImage[];
  showWaterMark?: boolean;
}

const sliderProps = {
  dots: true,
  infinite: true,
  speed: 300,
  slidesToShow: 1,
  slidesToScroll: 1,
};

function ImageCarousel({ images, showWaterMark = false }: ImageCarouselProps) {
  return (
    <StyledCarouselContainer>
      <Slider {...sliderProps}>
        {images.map(({ id, name, author, sns }) => (
          <WaterMarkImage key={id} imageUrl={name} waterMark={showWaterMark && author} type="list" sns={sns} />
        ))}
      </Slider>
    </StyledCarouselContainer>
  );
}

export default ImageCarousel;

const StyledCarouselContainer = styled.div`
  overflow: hidden;

  position: relative;

  width: 100;

  ::-webkit-scrollbar {
    display: none;
  }

  .slick-dots {
    bottom: 10px;
    left: 50%;

    width: max-content;

    border-radius: ${BORDER_RADIUS.lg};
    background-color: rgb(255 255 255 / 50%);

    transform: translateX(-50%);
  }
`;
