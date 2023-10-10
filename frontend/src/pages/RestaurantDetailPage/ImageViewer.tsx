import { Restaurant } from '~/@types/restaurant.types';
import ImageCarousel from '~/components/@common/ImageCarousel';
import ImageGrid from '~/components/@common/ImageGrid';
import useMediaQuery from '~/hooks/useMediaQuery';

interface ImageViewerProps {
  images: Restaurant['images'];
}

function ImageViewer({ images }: ImageViewerProps) {
  const { isMobile } = useMediaQuery();

  if (isMobile) return <ImageCarousel type="list" images={images} showWaterMark />;

  return <ImageGrid images={images.map(({ name: url, author, sns }) => ({ waterMark: author, url, sns }))} />;
}

export default ImageViewer;
