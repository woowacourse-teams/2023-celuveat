package com.celuveat.video.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.IntegrationTest;
import com.celuveat.common.SeedData;
import com.celuveat.video.application.dto.VideoWithCelebQueryResponse;
import com.celuveat.video.domain.Video;
import com.celuveat.video.domain.VideoQueryRepository.VideoSearchCond;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("영상 조회용 Repo(VideoQueryRepository) 은(는)")
class VideoQueryServiceTest {


    @Autowired
    private SeedData seedData;

    @Autowired
    private VideoQueryService videoQueryService;

    @Nested
    class 영상_검색 {

        @Test
        void 영상_전체_검색() {
            // given
            List<Video> videos = seedData.insertVideoSeedData();
            List<VideoWithCelebQueryResponse> expected = videos.stream()
                    .map(VideoWithCelebQueryResponse::of)
                    .toList();

            // when
            Page<VideoWithCelebQueryResponse> result = videoQueryService.findAllVideoWithCeleb(
                    new VideoSearchCond(null, null),
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
            List<Video> videos = seedData.insertVideoSeedData();
            Long expectedRestaurantId = 1L;
            List<VideoWithCelebQueryResponse> expected = videos.stream()
                    .filter(video -> video.restaurant().id().equals(expectedRestaurantId))
                    .map(VideoWithCelebQueryResponse::of)
                    .toList();

            // when
            Page<VideoWithCelebQueryResponse> result = videoQueryService.findAllVideoWithCeleb(
                    new VideoSearchCond(null, expectedRestaurantId),
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
            List<Video> videos = seedData.insertVideoSeedData();
            Long expectedCelebId = 1L;
            List<VideoWithCelebQueryResponse> expected = videos.stream()
                    .filter(video -> video.celeb().id().equals(expectedCelebId))
                    .map(VideoWithCelebQueryResponse::of)
                    .toList();

            // when
            Page<VideoWithCelebQueryResponse> result = videoQueryService.findAllVideoWithCeleb(
                    new VideoSearchCond(expectedCelebId, null),
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
            List<Video> videos = seedData.insertVideoSeedData();
            Long expectedRestaurantId = 1L;
            Long expectedCelebId = 1L;
            List<VideoWithCelebQueryResponse> expected = videos.stream()
                    .filter(video -> video.restaurant().id().equals(expectedRestaurantId))
                    .filter(video -> video.celeb().id().equals(expectedCelebId))
                    .map(VideoWithCelebQueryResponse::of)
                    .toList();

            // when
            Page<VideoWithCelebQueryResponse> result = videoQueryService.findAllVideoWithCeleb(
                    new VideoSearchCond(expectedCelebId, expectedRestaurantId),
                    PageRequest.of(0, expected.size())
            );

            // then
            assertThat(result.getContent())
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }
}
