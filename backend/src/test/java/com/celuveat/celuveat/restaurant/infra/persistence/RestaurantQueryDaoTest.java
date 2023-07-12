package com.celuveat.celuveat.restaurant.infra.persistence;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.celuveat.video.fixture.VideoFixture.영상;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.infra.persistence.CelebDao;
import com.celuveat.celuveat.common.annotation.DaoTest;
import com.celuveat.celuveat.common.page.PageCond;
import com.celuveat.celuveat.common.page.SliceResponse;
import com.celuveat.celuveat.restaurant.application.dto.RestaurantSearchResponse;
import com.celuveat.celuveat.restaurant.domain.Restaurant;
import com.celuveat.celuveat.video.infra.persistence.VideoDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DaoTest
@DisplayName("RestaurantQueryDao 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RestaurantQueryDaoTest {

    private static final int 히밥이_방문한_음식점_수 = 60;

    @Autowired
    private RestaurantQueryDao restaurantQueryDao;

    @Autowired
    private CelebDao celebDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private VideoDao videoDao;

    private final List<Restaurant> 히밥_음식점들 = new ArrayList<>();
    private final List<Restaurant> 성시경_음식점들 = new ArrayList<>();

    private Long 히밥_ID;
    private Long 성시경_ID;

    @BeforeEach
    void setUp() {
        히밥_ID = celebDao.save(히밥());
        성시경_ID = celebDao.save(성시경());
        for (int i = 0; i < 히밥이_방문한_음식점_수; i++) {
            Restaurant 음식점 = 음식점과_영상을_저장하고_음식점을_반환한다(i, 히밥_ID);
            히밥_음식점들.add(음식점);
        }
        for (int i = 히밥이_방문한_음식점_수; i < 히밥이_방문한_음식점_수 + 5; i++) {
            Restaurant 음식점 = 음식점과_영상을_저장하고_음식점을_반환한다(i, 성시경_ID);
            성시경_음식점들.add(음식점);
        }
    }

    private Restaurant 음식점과_영상을_저장하고_음식점을_반환한다(int index, Long 셀럽_ID) {
        Restaurant 음식점 = 음식점("음식점 " + index);
        Long id = restaurantDao.save(음식점);
        videoDao.save(영상(셀럽_ID, id));
        return 음식점;
    }

    @Test
    void 특정_셀럽이_방문한_음식점들을_조회할_수_있다() {
        // given
        int page = 2;
        int size = 20;
        PageCond pageCond = new PageCond(page, size);
        List<Restaurant> expected = 히밥_음식점들.subList(20, 40);
        Collections.reverse(expected);

        // when
        SliceResponse<RestaurantSearchResponse> result =
                restaurantQueryDao.findAllByCelebIdUploadDateDesc(히밥_ID, pageCond);

        // then
        assertThat(result.hasNextPage()).isTrue();
        assertThat(result.contents()).usingRecursiveComparison()
                .ignoringFields("id", "isAds")
                .isEqualTo(expected);
    }

    @Test
    void 마지막_페이지인_경우() {
        // given
        int page = 3;
        int size = 20;
        PageCond pageCond = new PageCond(page, size);
        List<Restaurant> expected = 히밥_음식점들.subList(0, 20);
        Collections.reverse(expected);

        // when
        SliceResponse<RestaurantSearchResponse> result =
                restaurantQueryDao.findAllByCelebIdUploadDateDesc(히밥_ID, pageCond);

        // then
        assertThat(result.hasNextPage()).isFalse();
        assertThat(result.contents()).usingRecursiveComparison()
                .ignoringFields("id", "isAds")
                .isEqualTo(expected);
    }

    @Test
    void 사이즈_초과_시_가져올_수_있는_수만_가져온다() {
        // given
        int page = 1;
        int size = 100;
        PageCond pageCond = new PageCond(page, size);
        List<Restaurant> expected = 히밥_음식점들;
        Collections.reverse(expected);

        // when
        SliceResponse<RestaurantSearchResponse> result =
                restaurantQueryDao.findAllByCelebIdUploadDateDesc(히밥_ID, pageCond);

        // then
        assertThat(result.hasNextPage()).isFalse();
        assertThat(result.contents()).usingRecursiveComparison()
                .ignoringFields("id", "isAds")
                .isEqualTo(expected);
        assertThat(result.contents()).hasSize(히밥이_방문한_음식점_수);
    }
}
