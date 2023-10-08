package com.celuveat.video.query.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.common.IntegrationTest;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.query.dao.VideoQueryResponseDao.VideoSearchCond;
import com.celuveat.video.query.dto.VideoQueryResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayName("영상 조회용 Dao(VideoQueryResponseDao) 은(는)")
class VideoQueryResponseDaoTest extends IntegrationTest {

    private List<Video> videos;

    @BeforeEach
    void setUp() {
        videos = seedData.insertVideoSeedData();
        em.flush();
        em.clear();
        System.out.println("=============[INSERT VIDEO SEED DATA]===========");
    }

    @Test
    void 영상_전체_검색() {
        // given
        List<VideoQueryResponse> expected = videos.stream()
                .map(this::toVideoWithCelebQueryResponse)
                .toList();

        // when
        Page<VideoQueryResponse> result = videoQueryResponseDao.find(
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
        Long restaurantId = 1L;
        List<VideoQueryResponse> expected = videos.stream()
                .filter(video -> video.restaurant().id().equals(restaurantId))
                .map(this::toVideoWithCelebQueryResponse)
                .toList();

        // when
        Page<VideoQueryResponse> result = videoQueryResponseDao.find(
                new VideoSearchCond(null, restaurantId),
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
        Long celebId = 1L;
        List<VideoQueryResponse> expected = videos.stream()
                .filter(video -> video.celeb().id().equals(celebId))
                .map(this::toVideoWithCelebQueryResponse)
                .toList();

        // when
        Page<VideoQueryResponse> result = videoQueryResponseDao.find(
                new VideoSearchCond(celebId, null),
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
        Long restaurantId = 1L;
        Long celebId = 1L;
        List<VideoQueryResponse> expected = videos.stream()
                .filter(video -> video.restaurant().id().equals(restaurantId))
                .filter(video -> video.celeb().id().equals(celebId))
                .map(this::toVideoWithCelebQueryResponse)
                .toList();

        // when
        Page<VideoQueryResponse> result = videoQueryResponseDao.find(
                new VideoSearchCond(celebId, restaurantId),
                PageRequest.of(0, expected.size())
        );

        // then
        assertThat(result.getContent())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private VideoQueryResponse toVideoWithCelebQueryResponse(Video video) {
        Celeb celeb = video.celeb();
        return new VideoQueryResponse(
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
