package com.celuveat.restaurant.query.dao;

import static com.celuveat.common.SeedData.isCelebVisited;
import static com.celuveat.common.SeedData.isRestaurantInArea;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1_2번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.전체영역_검색_범위;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.IntegrationTest;
import com.celuveat.common.util.StringUtil;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.RestaurantSearchQueryResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 조회용 Dao(RestaurantSearchQueryResponseDao) 은(는)")
class RestaurantSearchQueryResponseDaoTest extends IntegrationTest {

    private final List<RestaurantSearchQueryResponse> seed = new ArrayList<>();

    @BeforeEach
    void setUp() {
        seed.addAll(seedData.insertSeedData());
        em.flush();
        em.clear();
        System.out.println("=============[INSERT SEED DATA]============");
    }

    private List<String> 이름_추출(List<RestaurantSearchQueryResponse> list) {
        return list.stream()
                .map(RestaurantSearchQueryResponse::getName)
                .toList();
    }

    @Test
    void 전체_음식점_조회_테스트() {
        // when
        Page<RestaurantSearchQueryResponse> result = restaurantSearchQueryResponseDao.findAll(
                new RestaurantSearchCond(null, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::getDistance))
                .extracting(RestaurantSearchQueryResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(seed));
    }

    @Test
    void 셀럽으로_조회_테스트() {
        // given
        List<RestaurantSearchQueryResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        for (RestaurantSearchQueryResponse restaurantSearchQueryResponse : seed) {
            if (isCelebVisited(celebId, restaurantSearchQueryResponse)) {
                expected.add(restaurantSearchQueryResponse);
            }
        }

        // when
        Page<RestaurantSearchQueryResponse> result = restaurantSearchQueryResponseDao.findAll(
                new RestaurantSearchCond(celebId, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::getDistance))
                .extracting(RestaurantSearchQueryResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 카테고리로_조회_테스트() {
        // given
        List<RestaurantSearchQueryResponse> expected = new ArrayList<>();
        String superCategory = "superCategory:오도1호점";
        for (RestaurantSearchQueryResponse restaurantSearchQueryResponse : seed) {
            if (restaurantSearchQueryResponse.getSuperCategory().equals(superCategory)) {
                expected.add(restaurantSearchQueryResponse);
            }
        }

        // when
        Page<RestaurantSearchQueryResponse> result = restaurantSearchQueryResponseDao.findAll(
                new RestaurantSearchCond(null, superCategory, null),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::getDistance))
                .extracting(RestaurantSearchQueryResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 음식점_이름_포함으로_조회_테스트() {
        // given
        List<RestaurantSearchQueryResponse> expected = new ArrayList<>();
        String restaurantName = " 말 랑  \n";
        for (RestaurantSearchQueryResponse restaurantSearchQueryResponse : seed) {
            if (restaurantSearchQueryResponse.getName().contains(StringUtil.removeAllBlank(restaurantName))) {
                expected.add(restaurantSearchQueryResponse);
            }
        }

        // when
        Page<RestaurantSearchQueryResponse> result = restaurantSearchQueryResponseDao.findAll(
                new RestaurantSearchCond(null, null, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(expected.size());
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::getDistance))
                .extracting(RestaurantSearchQueryResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_카테고리로_조회_테스트() {
        // given
        List<RestaurantSearchQueryResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        String superCategory = "superCategory:오도1호점";
        for (RestaurantSearchQueryResponse restaurantSearchQueryResponse : seed) {
            if (isCelebVisited(celebId, restaurantSearchQueryResponse)
                    && restaurantSearchQueryResponse.getSuperCategory().equals(superCategory)) {
                expected.add(restaurantSearchQueryResponse);
            }
        }

        // when
        Page<RestaurantSearchQueryResponse> result = restaurantSearchQueryResponseDao.findAll(
                new RestaurantSearchCond(celebId, superCategory, null),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::getDistance))
                .extracting(RestaurantSearchQueryResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSearchQueryResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantSearchQueryResponse restaurantSearchQueryResponse : seed) {
            if (restaurantSearchQueryResponse.getName().contains(StringUtil.removeAllBlank(restaurantName))
                    && isCelebVisited(celebId, restaurantSearchQueryResponse)) {
                expected.add(restaurantSearchQueryResponse);
            }
        }

        // when
        Page<RestaurantSearchQueryResponse> result = restaurantSearchQueryResponseDao.findAll(
                new RestaurantSearchCond(celebId, null, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::getDistance))
                .extracting(RestaurantSearchQueryResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 카테고리와_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSearchQueryResponse> expected = new ArrayList<>();
        String superCategory = "superCategory:말랑2호점";
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantSearchQueryResponse restaurantSearchQueryResponse : seed) {
            if (restaurantSearchQueryResponse.getName().contains(StringUtil.removeAllBlank(restaurantName))
                    && restaurantSearchQueryResponse.getSuperCategory().equals(superCategory)) {
                expected.add(restaurantSearchQueryResponse);
            }
        }

        // when
        Page<RestaurantSearchQueryResponse> result = restaurantSearchQueryResponseDao.findAll(
                new RestaurantSearchCond(null, superCategory, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::getDistance))
                .extracting(RestaurantSearchQueryResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_카테고리와_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSearchQueryResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String superCategory = "superCategory:로이스1호점";
        String restaurantName = "로 이스";
        for (RestaurantSearchQueryResponse restaurantSearchQueryResponse : seed) {
            if (restaurantSearchQueryResponse.getName().contains(StringUtil.removeAllBlank(restaurantName))
                    && restaurantSearchQueryResponse.getSuperCategory().equals(superCategory)
                    && isCelebVisited(celebId, restaurantSearchQueryResponse)) {
                expected.add(restaurantSearchQueryResponse);
            }
        }

        // when
        Page<RestaurantSearchQueryResponse> result = restaurantSearchQueryResponseDao.findAll(
                new RestaurantSearchCond(celebId, superCategory, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(expected.size());
    }

    @Test
    void 위치_기준으로_일정_거리내_음식점_조회_테스트() {
        // given
        List<RestaurantSearchQueryResponse> expected = new ArrayList<>();
        for (RestaurantSearchQueryResponse restaurantSearchQueryResponse : seed) {
            if (isRestaurantInArea(박스_1번_지점포함, restaurantSearchQueryResponse)) {
                expected.add(restaurantSearchQueryResponse);
            }
        }

        // when
        Page<RestaurantSearchQueryResponse> result = restaurantSearchQueryResponseDao.findAll(
                new RestaurantSearchCond(null, null, null),
                new LocationSearchCond(
                        박스_1번_지점포함.lowLatitude(),
                        박스_1번_지점포함.highLatitude(),
                        박스_1번_지점포함.lowLongitude(),
                        박스_1번_지점포함.highLongitude()
                ),
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::getDistance))
                .extracting(RestaurantSearchQueryResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 위치_기준으로_일정_거리내_모든_음식점_조회_테스트() {
        // given
        List<RestaurantSearchQueryResponse> expected = new ArrayList<>();
        for (RestaurantSearchQueryResponse restaurantSearchQueryResponse : seed) {
            if (isRestaurantInArea(박스_1_2번_지점포함, restaurantSearchQueryResponse)) {
                expected.add(restaurantSearchQueryResponse);
            }
        }

        // when
        Page<RestaurantSearchQueryResponse> result = restaurantSearchQueryResponseDao.findAll(
                new RestaurantSearchCond(null, null, null),
                new LocationSearchCond(
                        박스_1_2번_지점포함.lowLatitude(),
                        박스_1_2번_지점포함.highLatitude(),
                        박스_1_2번_지점포함.lowLongitude(),
                        박스_1_2번_지점포함.highLongitude()
                ),
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::getDistance))
                .extracting(RestaurantSearchQueryResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_거리_기준으로_음식점_조회_테스트() {
        // given
        List<RestaurantSearchQueryResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        for (RestaurantSearchQueryResponse restaurantSearchQueryResponse : seed) {
            if (isRestaurantInArea(박스_1번_지점포함, restaurantSearchQueryResponse)
                    && isCelebVisited(celebId, restaurantSearchQueryResponse)) {
                expected.add(restaurantSearchQueryResponse);
            }
        }

        // when
        Page<RestaurantSearchQueryResponse> result = restaurantSearchQueryResponseDao.findAll(
                new RestaurantSearchCond(celebId, null, null),
                new LocationSearchCond(
                        박스_1번_지점포함.lowLatitude(),
                        박스_1번_지점포함.highLatitude(),
                        박스_1번_지점포함.lowLongitude(),
                        박스_1번_지점포함.highLongitude()
                ),
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::getDistance))
                .extracting(RestaurantSearchQueryResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000, 3000, 5000, 30000})
    void 특정_음식점을_기준으로_일정_거리_내에_있는_모든_음식점_조회_테스트(int specificDistance) {
        // when
        Page<RestaurantSearchQueryResponse> result = restaurantSearchQueryResponseDao.findNearBy(
                1L,
                specificDistance,
                PageRequest.of(0, 4),
                null
        );

        // then
        assertThat(result.getContent())
                .extracting(RestaurantSearchQueryResponse::getDistance)
                .allMatch(distance -> distance <= specificDistance);
    }

    @Test
    void 최근_등록된_음식점_조회_테스트() {
        // given TODO: expected 방식 수정 필요
        List<String> expectedRestaurantsName = em.createQuery("SELECT r FROM Restaurant r", Restaurant.class)
                .getResultList().stream()
                .sorted(comparing(Restaurant::createdDate).reversed()).limit(10)
                .map(Restaurant::name)
                .toList();

        // when
        List<RestaurantSearchQueryResponse> latestRestaurants = restaurantSearchQueryResponseDao.findLatest(null);

        // then
        assertThat(latestRestaurants).hasSize(10);
        assertThat(latestRestaurants)
                .extracting(RestaurantSearchQueryResponse::getName)
                .containsExactlyElementsOf(expectedRestaurantsName);
    }
}
