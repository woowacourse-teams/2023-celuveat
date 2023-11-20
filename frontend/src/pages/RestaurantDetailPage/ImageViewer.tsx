import { useQuery } from '@tanstack/react-query';
import { RestaurantData } from '~/@types/api.types';
import { getRestaurantDetail } from '~/api/restaurant';
import ImageCarousel from '~/components/@common/ImageCarousel';
import ImageGrid from '~/components/@common/ImageGrid';
import useMediaQuery from '~/hooks/useMediaQuery';

interface ImageViewerProps {
  restaurantId: string;
  celebId: string;
}

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

  return <ImageGrid images={images.map(({ name: url, author, sns }) => ({ waterMark: author, url, sns }))} />;
}

export default ImageViewer;
