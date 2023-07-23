package com.celuveat.restaurant.application;

import static com.celuveat.restaurant.fixture.PointFixture.기준점;
import static com.celuveat.restaurant.fixture.PointFixture.기준점에서_2KM_지점;
import static com.celuveat.restaurant.fixture.PointFixture.기준점에서_3KM_지점;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.SeedData;
import com.celuveat.common.util.StringUtil;
import com.celuveat.restaurant.application.dto.CelebQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import com.celuveat.restaurant.fixture.PointFixture.Point;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@Sql("/truncate.sql")
@DisplayName("RestaurantQueryService 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RestaurantQueryServiceTest {

    private final List<RestaurantQueryResponse> seed = new ArrayList<>();

    @Autowired
    private SeedData seedData;

    @Autowired
    private EntityManager em;

    @Autowired
    private RestaurantQueryService restaurantQueryService;

    @BeforeEach
    void setUp() {
        seed.addAll(seedData.insertSeedData());
        em.flush();
        em.clear();
        System.out.println("=============[INSERT SEED DATA]============");
    }

    @Test
    void 전체_음식점_조회_테스트() {
        // when
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(null, null, null, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(seed);
    }

    @Test
    void 셀럽으로_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (isCelebVisited(celebId, restaurantQueryResponse)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(celebId, null, null, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void 카테고리로_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        String category = "category:오도1호점";
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (restaurantQueryResponse.category().equals(category)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(null, category, null, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void 음식점_이름_포함으로_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        String restaurantName = " 말 랑  \n";
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (restaurantQueryResponse.name().contains(StringUtil.removeAllBlank(restaurantName))) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(null, null, restaurantName, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_카테고리로_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        String category = "category:오도1호점";
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            List<Long> list = restaurantQueryResponse.celebs().stream().map(CelebQueryResponse::id)
                    .toList();
            if (list.contains(celebId) && restaurantQueryResponse.category().equals(category)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(celebId, category, null, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            List<Long> list = restaurantQueryResponse.celebs().stream().map(CelebQueryResponse::id)
                    .toList();
            if (restaurantQueryResponse.name().contains(StringUtil.removeAllBlank(restaurantName))
                    && list.contains(celebId)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(celebId, null, restaurantName, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void 카테고리와_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        String category = "category:말랑2호점";
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (restaurantQueryResponse.name().contains(StringUtil.removeAllBlank(restaurantName))
                    && restaurantQueryResponse.category().equals(category)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(null, category, restaurantName, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_카테고리와_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String category = "category:로이스1호점";
        String restaurantName = "로 이스";
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (restaurantQueryResponse.name().contains(StringUtil.removeAllBlank(restaurantName))
                    && restaurantQueryResponse.category().equals(category)
                    && isCelebVisited(celebId, restaurantQueryResponse)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(celebId, category, restaurantName, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_거리_기준으로_음식점_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        int distance = 3;
        Long celebId = 1L;
        Set<Point> inDistancePoints = Set.of(기준점에서_2KM_지점, 기준점에서_3KM_지점);
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (isRestaurantInDistance(inDistancePoints, restaurantQueryResponse)
                    && isCelebVisited(celebId, restaurantQueryResponse)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(
                        celebId,
                        null,
                        null,
                        String.valueOf(기준점.latitude()),
                        String.valueOf(기준점.longitude()),
                        distance
                )
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_음식점이름과_거리를_기준으로_음식점_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        int distance = 3;
        Long celebId = 1L;
        String restaurantName = "로이스";
        Set<Point> inAreaRestaurants = Set.of(기준점에서_2KM_지점, 기준점에서_3KM_지점);
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (isRestaurantInDistance(inAreaRestaurants, restaurantQueryResponse)
                    && isCelebVisited(celebId, restaurantQueryResponse)
                    && restaurantQueryResponse.name().contains(StringUtil.removeAllBlank(restaurantName))) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(
                        celebId,
                        null,
                        restaurantName,
                        String.valueOf(기준점.latitude()),
                        String.valueOf(기준점.longitude()),
                        distance
                )
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private boolean isRestaurantInDistance(
            Set<Point> inAreaRestaurants,
            RestaurantQueryResponse restaurantQueryResponse
    ) {
        return inAreaRestaurants.stream().anyMatch(point ->
                point.latitude().equals(restaurantQueryResponse.latitude())
                        && point.longitude().equals(restaurantQueryResponse.longitude())
        );
    }

    private boolean isCelebVisited(Long celebId, RestaurantQueryResponse restaurantQueryResponse) {
        List<Long> celebIds = restaurantQueryResponse.celebs().stream().map(CelebQueryResponse::id).toList();
        return celebIds.contains(celebId);
    }
}
