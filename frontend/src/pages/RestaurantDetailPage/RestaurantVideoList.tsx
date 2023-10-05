import { useQuery } from '@tanstack/react-query';
import styled from 'styled-components';
import VideoCarousel from '~/components/@common/VideoCarousel';
import type { VideoList } from '~/@types/api.types';
import { getRestaurantVideo } from '~/api/restaurant';
import { getCelebVideo } from '~/api/celeb';

interface RestaurantVideoListProps {
  restaurantId: string;
  celebId: string;
}

function RestaurantVideoList({ restaurantId, celebId }: RestaurantVideoListProps) {
  const { data: restaurantVideo } = useQuery<VideoList>({
    queryKey: ['restaurantVideo', restaurantId],
    queryFn: async () => getRestaurantVideo(restaurantId),
    suspense: true,
  });

  const { data: celebVideo } = useQuery<VideoList>({
    queryKey: ['celebVideo', celebId],
    queryFn: async () => getCelebVideo(celebId),
    suspense: true,
  });

  return (
    <StyledVideoSection>
      {restaurantVideo.totalElementsCount > 1 && (
        <VideoCarousel
          title={`이외에 ${restaurantVideo.currentElementsCount - 1}명의 셀럽이 다녀갔어요!`}
          videos={restaurantVideo.content.slice(1)}
        />
      )}
      <VideoCarousel
        title="이 셀럽의 다른 음식점 영상"
        videos={celebVideo.content.filter(({ videoId }) => videoId !== restaurantVideo?.content[0].videoId)}
      />
    </StyledVideoSection>
  );
}

export default RestaurantVideoList;

const StyledVideoSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 3.2rem 0;
`;
