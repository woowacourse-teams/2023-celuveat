package com.celuveat.restaurant.query.dao;

import static com.celuveat.restaurant.fixture.LocationFixture.isRestaurantInArea;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1_2번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.전체영역_검색_범위;
import static com.celuveat.restaurant.fixture.RestaurantFixture.isCelebVisited;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.IntegrationTest;
import com.celuveat.common.SeedData;
import com.celuveat.common.util.StringUtil;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.RestaurantSearchResponse;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 조회용 Dao(RestaurantSearchResponseDao) 은(는)")
class RestaurantSearchResponseDaoTest {

    private final List<RestaurantSearchResponse> seed = new ArrayList<>();

    @Autowired
    private SeedData seedData;

    @Autowired
    private EntityManager em;

    @Autowired
    private RestaurantSearchResponseDao RestaurantSearchResponseDao;

    @BeforeEach
    void setUp() {
        seed.addAll(seedData.insertSeedData());
        em.flush();
        em.clear();
        System.out.println("=============[INSERT SEED DATA]============");
    }

    private List<String> 이름_추출(List<RestaurantSearchResponse> list) {
        return list.stream()
                .map(RestaurantSearchResponse::getName)
                .toList();
    }

    @Test
    void 전체_음식점_조회_테스트() {
        // when
        Page<RestaurantSearchResponse> result = RestaurantSearchResponseDao.findAll(
                new RestaurantSearchCond(null, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .extracting(RestaurantSearchResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(seed));
    }

    @Test
    void 셀럽으로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        for (RestaurantSearchResponse restaurantSearchResponse : seed) {
            if (isCelebVisited(celebId, restaurantSearchResponse)) {
                expected.add(restaurantSearchResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = RestaurantSearchResponseDao.findAll(
                new RestaurantSearchCond(celebId, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .extracting(RestaurantSearchResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 카테고리로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        String category = "category:오도1호점";
        for (RestaurantSearchResponse restaurantSearchResponse : seed) {
            if (restaurantSearchResponse.getCategory().equals(category)) {
                expected.add(restaurantSearchResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = RestaurantSearchResponseDao.findAll(
                new RestaurantSearchCond(null, category, null),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .extracting(RestaurantSearchResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 음식점_이름_포함으로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        String restaurantName = " 말 랑  \n";
        for (RestaurantSearchResponse restaurantSearchResponse : seed) {
            if (restaurantSearchResponse.getName().contains(StringUtil.removeAllBlank(restaurantName))) {
                expected.add(restaurantSearchResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = RestaurantSearchResponseDao.findAll(
                new RestaurantSearchCond(null, null, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(expected.size());
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .extracting(RestaurantSearchResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_카테고리로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        String category = "category:오도1호점";
        for (RestaurantSearchResponse restaurantSearchResponse : seed) {
            if (isCelebVisited(celebId, restaurantSearchResponse)
                    && restaurantSearchResponse.getCategory().equals(category)) {
                expected.add(restaurantSearchResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = RestaurantSearchResponseDao.findAll(
                new RestaurantSearchCond(celebId, category, null),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .extracting(RestaurantSearchResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantSearchResponse restaurantSearchResponse : seed) {
            if (restaurantSearchResponse.getName().contains(StringUtil.removeAllBlank(restaurantName))
                    && isCelebVisited(celebId, restaurantSearchResponse)) {
                expected.add(restaurantSearchResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = RestaurantSearchResponseDao.findAll(
                new RestaurantSearchCond(celebId, null, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .extracting(RestaurantSearchResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 카테고리와_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        String category = "category:말랑2호점";
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantSearchResponse restaurantSearchResponse : seed) {
            if (restaurantSearchResponse.getName().contains(StringUtil.removeAllBlank(restaurantName))
                    && restaurantSearchResponse.getCategory().equals(category)) {
                expected.add(restaurantSearchResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = RestaurantSearchResponseDao.findAll(
                new RestaurantSearchCond(null, category, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 20),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .extracting(RestaurantSearchResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_카테고리와_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String category = "category:로이스1호점";
        String restaurantName = "로 이스";
        for (RestaurantSearchResponse restaurantSearchResponse : seed) {
            if (restaurantSearchResponse.getName().contains(StringUtil.removeAllBlank(restaurantName))
                    && restaurantSearchResponse.getCategory().equals(category)
                    && isCelebVisited(celebId, restaurantSearchResponse)) {
                expected.add(restaurantSearchResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = RestaurantSearchResponseDao.findAll(
                new RestaurantSearchCond(celebId, category, restaurantName),
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
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        for (RestaurantSearchResponse restaurantSearchResponse : seed) {
            if (isRestaurantInArea(박스_1번_지점포함, restaurantSearchResponse)) {
                expected.add(restaurantSearchResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = RestaurantSearchResponseDao.findAll(
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
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .extracting(RestaurantSearchResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 위치_기준으로_일정_거리내_모든_음식점_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        for (RestaurantSearchResponse restaurantSearchResponse : seed) {
            if (isRestaurantInArea(박스_1_2번_지점포함, restaurantSearchResponse)) {
                expected.add(restaurantSearchResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = RestaurantSearchResponseDao.findAll(
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
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .extracting(RestaurantSearchResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_거리_기준으로_음식점_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        for (RestaurantSearchResponse restaurantSearchResponse : seed) {
            if (isRestaurantInArea(박스_1번_지점포함, restaurantSearchResponse)
                    && isCelebVisited(celebId, restaurantSearchResponse)) {
                expected.add(restaurantSearchResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = RestaurantSearchResponseDao.findAll(
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
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .extracting(RestaurantSearchResponse::getName)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000, 3000, 5000, 30000})
    void 특정_음식점을_기준으로_일정_거리_내에_있는_모든_음식점_조회_테스트(int specificDistance) {
        // when
        Page<RestaurantSearchResponse> result = RestaurantSearchResponseDao.findNearBy(
                1L,
                specificDistance,
                PageRequest.of(0, 4),
                null
        );

        // then
        assertThat(result.getContent())
                .extracting(RestaurantSearchResponse::getDistance)
                .allMatch(distance -> distance <= specificDistance);
    }
}
