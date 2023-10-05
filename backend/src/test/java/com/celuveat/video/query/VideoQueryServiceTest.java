package com.celuveat.video.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.common.IntegrationTest;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.query.dao.VideoWithCelebQueryResponseDao;
import com.celuveat.video.query.dto.VideoWithCelebQueryResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayName("영상 조회용 서비스(VideoQueryService) 은(는)")
class VideoQueryServiceTest extends IntegrationTest {

    @Nested
    class 영상_검색 {

        private List<Video> videos;

        @BeforeEach
        void setUp() {
            videos = seedData.insertVideoSeedData();
        }

        @Test
        void 영상_전체_검색() {
            // given
            List<VideoWithCelebQueryResponse> expected = videos.stream()
                    .map(this::toVideoWithCelebQueryResponse)
                    .toList();

            // when
            Page<VideoWithCelebQueryResponse> result = videoQueryService.findAllVideoWithCeleb(
                    new VideoWithCelebQueryResponseDao.VideoSearchCond(null, null),
                    PageRequest.of(0, expected.size())
            );

            // then
            assertThat(result.getContent())
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 음식점ID로_영상_검색() {
            // given
            Long expectedRestaurantId = 1L;
            List<VideoWithCelebQueryResponse> expected = videos.stream()
                    .filter(video -> video.restaurant().id().equals(expectedRestaurantId))
                    .map(this::toVideoWithCelebQueryResponse)
                    .toList();

            // when
            Page<VideoWithCelebQueryResponse> result = videoQueryService.findAllVideoWithCeleb(
                    new VideoWithCelebQueryResponseDao.VideoSearchCond(null, expectedRestaurantId),
                    PageRequest.of(0, expected.size())
            );

            // then
            assertThat(result.getContent())
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 셀럽ID로_영상_검색() {
            // given
            Long expectedCelebId = 1L;
            List<VideoWithCelebQueryResponse> expected = videos.stream()
                    .filter(video -> video.celeb().id().equals(expectedCelebId))
                    .map(this::toVideoWithCelebQueryResponse)
                    .toList();

            // when
            Page<VideoWithCelebQueryResponse> result = videoQueryService.findAllVideoWithCeleb(
                    new VideoWithCelebQueryResponseDao.VideoSearchCond(expectedCelebId, null),
                    PageRequest.of(0, expected.size())
            );

            // then
            assertThat(result.getContent())
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 음식점ID와_셀럽ID로_영상_검색() {
            // given
            Long expectedRestaurantId = 1L;
            Long expectedCelebId = 1L;
            List<VideoWithCelebQueryResponse> expected = videos.stream()
                    .filter(video -> video.restaurant().id().equals(expectedRestaurantId))
                    .filter(video -> video.celeb().id().equals(expectedCelebId))
                    .map(this::toVideoWithCelebQueryResponse)
                    .toList();

            // when
            Page<VideoWithCelebQueryResponse> result = videoQueryService.findAllVideoWithCeleb(
                    new VideoWithCelebQueryResponseDao.VideoSearchCond(expectedCelebId, expectedRestaurantId),
                    PageRequest.of(0, 1)
            );

            // then
            assertThat(result.getContent())
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        private VideoWithCelebQueryResponse toVideoWithCelebQueryResponse(Video video) {
            Celeb celeb = video.celeb();
            return new VideoWithCelebQueryResponse(
                    video.id(),
                    video.youtubeUrl(),
                    video.uploadDate(),
                    celeb.id(),
                    celeb.name(),
                    celeb.youtubeChannelName(),
                    celeb.profileImageUrl()
            );
        }
    }
}
