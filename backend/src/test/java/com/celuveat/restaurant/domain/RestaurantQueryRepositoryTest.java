package com.celuveat.restaurant.domain;

import static com.celuveat.restaurant.fixture.PointFixture.기준점;
import static com.celuveat.restaurant.fixture.PointFixture.기준점에서_2KM_지점;
import static com.celuveat.restaurant.fixture.PointFixture.기준점에서_3KM_지점;
import static com.celuveat.restaurant.fixture.PointFixture.기준점에서_5KM_지점;
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
@DisplayName("RestaurantQueryRepository 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RestaurantQueryRepositoryTest {

    private final List<RestaurantQueryResponse> seed = new ArrayList<>();

    @Autowired
    private SeedData seedData;

    @Autowired
    private EntityManager em;

    @Autowired
    private RestaurantQueryRepository restaurantQueryRepository;

    @BeforeEach
    void setUp() {
        seed.addAll(seedData.insertSeedData());
        em.flush();
        em.clear();
        System.out.println("=============[INSERT SEED DATA]============");
    }

    private List<String> 이름_추출(List<RestaurantQueryResponse> list) {
        return list.stream()
                .map(RestaurantQueryResponse::name)
                .toList();
    }

    @Test
    void 전체_음식점_조회_테스트() {
        // when
        List<Restaurant> result = restaurantQueryRepository.getRestaurants(
                new RestaurantSearchCond(null, null, null, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).extracting(Restaurant::name)
                .containsExactlyElementsOf(이름_추출(seed));
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
        List<Restaurant> result = restaurantQueryRepository.getRestaurants(
                new RestaurantSearchCond(celebId, null, null, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).extracting(Restaurant::name)
                .containsExactlyElementsOf(이름_추출(expected));
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
        List<Restaurant> result = restaurantQueryRepository.getRestaurants(
                new RestaurantSearchCond(null, category, null, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).extracting(Restaurant::name)
                .containsExactlyElementsOf(이름_추출(expected));
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
        List<Restaurant> result = restaurantQueryRepository.getRestaurants(
                new RestaurantSearchCond(null, null, restaurantName, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(expected.size());
        assertThat(result).extracting(Restaurant::name)
                .containsExactlyElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_카테고리로_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        String category = "category:오도1호점";
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (isCelebVisited(celebId, restaurantQueryResponse)
                    && restaurantQueryResponse.category().equals(category)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<Restaurant> result = restaurantQueryRepository.getRestaurants(
                new RestaurantSearchCond(celebId, category, null, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).extracting(Restaurant::name)
                .containsExactlyElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (restaurantQueryResponse.name().contains(StringUtil.removeAllBlank(restaurantName))
                    && isCelebVisited(celebId, restaurantQueryResponse)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<Restaurant> result = restaurantQueryRepository.getRestaurants(
                new RestaurantSearchCond(celebId, null, restaurantName, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).extracting(Restaurant::name)
                .containsExactlyElementsOf(이름_추출(expected));
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
        List<Restaurant> result = restaurantQueryRepository.getRestaurants(
                new RestaurantSearchCond(null, category, restaurantName, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).extracting(Restaurant::name)
                .containsExactlyElementsOf(이름_추출(expected));
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
        List<Restaurant> result = restaurantQueryRepository.getRestaurants(
                new RestaurantSearchCond(celebId, category, restaurantName, null, null, null));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(expected.size());
    }

    @Test
    void 위치_기준으로_일정_거리내_음식점_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        int distance = 2;
        Set<Point> inAreaRestaurants = Set.of(기준점에서_2KM_지점);
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (isRestaurantInDistance(inAreaRestaurants, restaurantQueryResponse)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<Restaurant> result = restaurantQueryRepository.getRestaurants(
                new RestaurantSearchCond(
                        null,
                        null,
                        null,
                        String.valueOf(기준점.latitude()),
                        String.valueOf(기준점.longitude()),
                        distance)
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).extracting(Restaurant::name)
                .containsExactlyElementsOf(이름_추출(expected));
    }

    @Test
    void 위치_기준으로_일정_거리내_모든_음식점_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        int distance = 5;
        Set<Point> inAreaRestaurants = Set.of(기준점에서_2KM_지점, 기준점에서_3KM_지점, 기준점에서_5KM_지점);
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (isRestaurantInDistance(inAreaRestaurants, restaurantQueryResponse)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<Restaurant> result = restaurantQueryRepository.getRestaurants(
                new RestaurantSearchCond(
                        null,
                        null,
                        null,
                        String.valueOf(기준점.latitude()),
                        String.valueOf(기준점.longitude()),
                        distance)
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).extracting(Restaurant::name)
                .containsExactlyElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_거리_기준으로_음식점_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        int distance = 2;
        Long celebId = 1L;
        Set<Point> inAreaRestaurants = Set.of(기준점에서_2KM_지점);
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (isRestaurantInDistance(inAreaRestaurants, restaurantQueryResponse)
                    && isCelebVisited(celebId, restaurantQueryResponse)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        List<Restaurant> result = restaurantQueryRepository.getRestaurants(
                new RestaurantSearchCond(
                        celebId,
                        null,
                        null,
                        String.valueOf(기준점.latitude()),
                        String.valueOf(기준점.longitude()),
                        distance)
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).extracting(Restaurant::name)
                .containsExactlyElementsOf(이름_추출(expected));
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
