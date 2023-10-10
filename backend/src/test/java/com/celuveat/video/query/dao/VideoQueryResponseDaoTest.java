package com.celuveat.video.query.dao;

import static com.celuveat.celeb.fixture.CelebFixture.맛객리우;
import static com.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celeb.fixture.CelebFixture.회사랑;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static com.celuveat.restaurant.fixture.RestaurantFixture.모던샤브하우스;
import static com.celuveat.restaurant.fixture.RestaurantFixture.하늘초밥;
import static com.celuveat.video.fixture.VideoFixture.맛객리우의_모던샤브하우스_영상;
import static com.celuveat.video.fixture.VideoFixture.성시경의_대성집_영상;
import static com.celuveat.video.fixture.VideoFixture.회사랑의_하늘초밥_영상;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.common.DaoTest;
import com.celuveat.common.TestData;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.query.dao.VideoQueryResponseDao.VideoSearchCond;
import com.celuveat.video.query.dto.VideoQueryResponse;
import com.celuveat.video.utils.VideoResponseUtil;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@DisplayName("영상 조회용 Dao(VideoQueryResponseDao) 은(는)")
class VideoQueryResponseDaoTest extends DaoTest {

    @Autowired
    private VideoQueryResponseDao videoQueryResponseDao;

    private final Restaurant 대성집 = 대성집();
    private final Restaurant 하늘초밥 = 하늘초밥();
    private final Restaurant 모던샤브하우스 = 모던샤브하우스();
    private final Celeb 성시경 = 성시경();
    private final Celeb 회사랑 = 회사랑();
    private final Celeb 맛객리우 = 맛객리우();
    private final Video 성시경의_대성집_영상 = 성시경의_대성집_영상(성시경, 대성집);
    private final Video 회사랑의_하늘초밥_영상 = 회사랑의_하늘초밥_영상(회사랑, 하늘초밥);
    private final Video 맛객리우의_모던샤브하우스_영상 = 맛객리우의_모던샤브하우스_영상(맛객리우, 모던샤브하우스);

    @Override
    protected void prepareTestData() {
        testData.addCelebs(성시경, 회사랑, 맛객리우);
        testData.addRestaurants(대성집, 하늘초밥, 모던샤브하우스);
        testData.addVideos(
                성시경의_대성집_영상,
                회사랑의_하늘초밥_영상,
                맛객리우의_모던샤브하우스_영상
        );
    }

    @Test
    void 영상_전체_검색() {
        // when
        List<VideoQueryResponse> result = videoQueryResponseDao.find(
                new VideoSearchCond(null, null),
                PageRequest.of(0, 18)
        ).getContent();

        // then
        assertThat(result)
                .hasSize(3)
                .extracting(VideoQueryResponse::youtubeVideoKey)
                .containsExactly(
                        VideoResponseUtil.extractVideoKey(성시경의_대성집_영상.youtubeUrl()),
                        VideoResponseUtil.extractVideoKey(회사랑의_하늘초밥_영상.youtubeUrl()),
                        VideoResponseUtil.extractVideoKey(맛객리우의_모던샤브하우스_영상.youtubeUrl())
                );
    }

    @Test
    void 음식점ID로_영상_검색() {
        // when
        List<VideoQueryResponse> result = videoQueryResponseDao.find(
                new VideoSearchCond(null, 대성집.id()),
                PageRequest.of(0, 18)
        ).getContent();

        // then
        assertThat(result)
                .hasSize(1)
                .extracting(VideoQueryResponse::youtubeVideoKey)
                .containsExactly(
                        VideoResponseUtil.extractVideoKey(성시경의_대성집_영상.youtubeUrl())
                );
    }

    @Test
    void 셀럽ID로_영상_검색() {
        // when
        List<VideoQueryResponse> result = videoQueryResponseDao.find(
                new VideoSearchCond(회사랑.id(), null),
                PageRequest.of(0, 18)
        ).getContent();

        // then
        assertThat(result)
                .hasSize(1)
                .extracting(VideoQueryResponse::youtubeVideoKey)
                .containsExactly(
                        VideoResponseUtil.extractVideoKey(회사랑의_하늘초밥_영상.youtubeUrl())
                );
    }

    @Test
    void 음식점ID와_셀럽ID로_영상_검색() {
        // when
        List<VideoQueryResponse> result = videoQueryResponseDao.find(
                new VideoSearchCond(맛객리우.id(), 모던샤브하우스.id()),
                PageRequest.of(0, 18)
        ).getContent();

        // then
        assertThat(result)
                .hasSize(1)
                .extracting(VideoQueryResponse::youtubeVideoKey)
                .containsExactly(
                        VideoResponseUtil.extractVideoKey(맛객리우의_모던샤브하우스_영상.youtubeUrl())
                );
    }
}
