package com.celuveat.restaurant.query.dao;

import static com.celuveat.celeb.fixture.CelebFixture.백종원;
import static com.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celeb.fixture.CelebFixture.셀럽들;
import static com.celuveat.celeb.fixture.CelebFixture.핫둘제주;
import static com.celuveat.restaurant.fixture.LocationFixture.isRestaurantInArea;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1_2번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.전체영역_검색_범위;
import static com.celuveat.restaurant.fixture.RestaurantFixture.isCelebVisited;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.음식점사진들;
import static com.celuveat.video.fixture.VideoFixture.영상;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.common.IntegrationTest;
import com.celuveat.common.SeedData;
import com.celuveat.common.TestData;
import com.celuveat.common.TestDataInserter;
import com.celuveat.common.util.StringUtil;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.fixture.RestaurantFixture.지역별_음식점;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.AddressSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;
import com.celuveat.restaurant.query.dto.RestaurantWithDistance;
import com.celuveat.video.command.domain.Video;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@DisplayName("음식점 조회용 레포지토리(RestaurantWithDistanceDao) 은(는)")
class RestaurantWithDistanceDaoTest {

    private final List<RestaurantSimpleResponse> seed = new ArrayList<>();

    @Autowired
    private TestDataInserter testDataInserter;

    @Autowired
    private SeedData seedData;

    @Autowired
    private EntityManager em;

    @Autowired
    private RestaurantWithDistanceDao restaurantWithDistanceDao;

    @BeforeEach
    void setUp() {
        seed.addAll(seedData.insertSeedData());
        em.flush();
        em.clear();
        System.out.println("=============[INSERT SEED DATA]============");
    }

    private List<String> 이름_추출(List<RestaurantSimpleResponse> list) {
        return list.stream()
                .map(RestaurantSimpleResponse::name)
                .toList();
    }

    @Test
    void 전체_음식점_조회_테스트() {
        // when
        Page<RestaurantWithDistance> result = restaurantWithDistanceDao.search(
                new RestaurantSearchCond(null, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 20));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantWithDistance::distance))
                .extracting(RestaurantWithDistance::name)
                .containsExactlyInAnyOrderElementsOf(이름_추출(seed));
    }

    @Test
    void 셀럽으로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        for (RestaurantSimpleResponse restaurantSimpleResponse : seed) {
            if (isCelebVisited(celebId, restaurantSimpleResponse)) {
                expected.add(restaurantSimpleResponse);
            }
        }

        // when
        Page<RestaurantWithDistance> result = restaurantWithDistanceDao.search(
                new RestaurantSearchCond(celebId, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 20));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantWithDistance::distance))
                .extracting(RestaurantWithDistance::name)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 카테고리로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        String category = "category:오도1호점";
        for (RestaurantSimpleResponse restaurantSimpleResponse : seed) {
            if (restaurantSimpleResponse.category().equals(category)) {
                expected.add(restaurantSimpleResponse);
            }
        }

        // when
        Page<RestaurantWithDistance> result = restaurantWithDistanceDao.search(
                new RestaurantSearchCond(null, category, null),
                전체영역_검색_범위,
                PageRequest.of(0, 20));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantWithDistance::distance))
                .extracting(RestaurantWithDistance::name)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 음식점_이름_포함으로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        String restaurantName = " 말 랑  \n";
        for (RestaurantSimpleResponse restaurantSimpleResponse : seed) {
            if (restaurantSimpleResponse.name().contains(StringUtil.removeAllBlank(restaurantName))) {
                expected.add(restaurantSimpleResponse);
            }
        }

        // when
        Page<RestaurantWithDistance> result = restaurantWithDistanceDao.search(
                new RestaurantSearchCond(null, null, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 20));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(expected.size());
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantWithDistance::distance))
                .extracting(RestaurantWithDistance::name)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_카테고리로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        String category = "category:오도1호점";
        for (RestaurantSimpleResponse restaurantSimpleResponse : seed) {
            if (isCelebVisited(celebId, restaurantSimpleResponse)
                    && restaurantSimpleResponse.category().equals(category)) {
                expected.add(restaurantSimpleResponse);
            }
        }

        // when
        Page<RestaurantWithDistance> result = restaurantWithDistanceDao.search(
                new RestaurantSearchCond(celebId, category, null),
                전체영역_검색_범위,
                PageRequest.of(0, 20));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantWithDistance::distance))
                .extracting(RestaurantWithDistance::name)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantSimpleResponse restaurantSimpleResponse : seed) {
            if (restaurantSimpleResponse.name().contains(StringUtil.removeAllBlank(restaurantName))
                    && isCelebVisited(celebId, restaurantSimpleResponse)) {
                expected.add(restaurantSimpleResponse);
            }
        }

        // when
        Page<RestaurantWithDistance> result = restaurantWithDistanceDao.search(
                new RestaurantSearchCond(celebId, null, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 20));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantWithDistance::distance))
                .extracting(RestaurantWithDistance::name)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 카테고리와_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        String category = "category:말랑2호점";
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantSimpleResponse restaurantSimpleResponse : seed) {
            if (restaurantSimpleResponse.name().contains(StringUtil.removeAllBlank(restaurantName))
                    && restaurantSimpleResponse.category().equals(category)) {
                expected.add(restaurantSimpleResponse);
            }
        }

        // when
        Page<RestaurantWithDistance> result = restaurantWithDistanceDao.search(
                new RestaurantSearchCond(null, category, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 20));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantWithDistance::distance))
                .extracting(RestaurantWithDistance::name)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_카테고리와_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String category = "category:로이스1호점";
        String restaurantName = "로 이스";
        for (RestaurantSimpleResponse restaurantSimpleResponse : seed) {
            if (restaurantSimpleResponse.name().contains(StringUtil.removeAllBlank(restaurantName))
                    && restaurantSimpleResponse.category().equals(category)
                    && isCelebVisited(celebId, restaurantSimpleResponse)) {
                expected.add(restaurantSimpleResponse);
            }
        }

        // when
        Page<RestaurantWithDistance> result = restaurantWithDistanceDao.search(
                new RestaurantSearchCond(celebId, category, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 20));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(expected.size());
    }

    @Test
    void 위치_기준으로_일정_거리내_음식점_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        for (RestaurantSimpleResponse restaurantSimpleResponse : seed) {
            if (isRestaurantInArea(박스_1번_지점포함, restaurantSimpleResponse)) {
                expected.add(restaurantSimpleResponse);
            }
        }

        // when
        Page<RestaurantWithDistance> result = restaurantWithDistanceDao.search(
                new RestaurantSearchCond(null, null, null),
                new LocationSearchCond(
                        박스_1번_지점포함.lowLatitude(),
                        박스_1번_지점포함.highLatitude(),
                        박스_1번_지점포함.lowLongitude(),
                        박스_1번_지점포함.highLongitude()
                ),
                PageRequest.of(0, 20));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantWithDistance::distance))
                .extracting(RestaurantWithDistance::name)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 위치_기준으로_일정_거리내_모든_음식점_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        for (RestaurantSimpleResponse restaurantSimpleResponse : seed) {
            if (isRestaurantInArea(박스_1_2번_지점포함, restaurantSimpleResponse)) {
                expected.add(restaurantSimpleResponse);
            }
        }

        // when
        Page<RestaurantWithDistance> result = restaurantWithDistanceDao.search(
                new RestaurantSearchCond(null, null, null),
                new LocationSearchCond(
                        박스_1_2번_지점포함.lowLatitude(),
                        박스_1_2번_지점포함.highLatitude(),
                        박스_1_2번_지점포함.lowLongitude(),
                        박스_1_2번_지점포함.highLongitude()
                ),
                PageRequest.of(0, 20));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantWithDistance::distance))
                .extracting(RestaurantWithDistance::name)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 셀럽과_거리_기준으로_음식점_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        for (RestaurantSimpleResponse restaurantSimpleResponse : seed) {
            if (isRestaurantInArea(박스_1번_지점포함, restaurantSimpleResponse)
                    && isCelebVisited(celebId, restaurantSimpleResponse)) {
                expected.add(restaurantSimpleResponse);
            }
        }

        // when
        Page<RestaurantWithDistance> result = restaurantWithDistanceDao.search(
                new RestaurantSearchCond(celebId, null, null),
                new LocationSearchCond(
                        박스_1번_지점포함.lowLatitude(),
                        박스_1번_지점포함.highLatitude(),
                        박스_1번_지점포함.lowLongitude(),
                        박스_1번_지점포함.highLongitude()
                ),
                PageRequest.of(0, 20));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantWithDistance::distance))
                .extracting(RestaurantWithDistance::name)
                .containsExactlyInAnyOrderElementsOf(이름_추출(expected));
    }

    @Test
    void 주소로_음식점_검색() {
        // given
        Map<String, Celeb> 셀럽들 = 셀럽들(성시경(), 핫둘제주(), 백종원());
        List<Restaurant> 음식점들 = new ArrayList<>();
        List<Restaurant> 제주_음식점들 = 지역별_음식점.제주_음식점들();
        List<Restaurant> 압구정_음식점들 = 지역별_음식점.압구정_음식점들();
        음식점들.addAll(제주_음식점들);
        음식점들.addAll(압구정_음식점들);
        Map<Restaurant, List<RestaurantImage>> 음식점_사진들 = 음식점사진들(음식점들);
        Map<Restaurant, List<Video>> 영상들 = new HashMap<>();
        for (Restaurant restaurant : 제주_음식점들) {
            영상들.put(restaurant, List.of(영상("제주 url1", restaurant, 셀럽들.get("핫둘제주"))));
        }
        for (Restaurant restaurant : 압구정_음식점들) {
            List<Video> videos = new ArrayList<>();
            videos.add(영상(restaurant, 셀럽들.get("성시경")));
            videos.add(영상(restaurant, 셀럽들.get("백종원")));
            영상들.put(restaurant, videos);
        }
        TestData testData = TestData.builder()
                .celebs(셀럽들.values().stream().toList())
                .restaurants(음식점들)
                .restaurantImages(음식점_사진들)
                .videos(영상들)
                .build();
        testDataInserter.insertData(testData);

        // when
        Page<RestaurantWithDistance> restaurantWithDistances = restaurantWithDistanceDao.searchByAddress(
                new AddressSearchCond(List.of(
                        "서울 강남구 도산대로",
                        "서울 강남구 가로수길",
                        "서울 강남구 압구정로",
                        "서울 강남구 논현로"
                )),
                PageRequest.of(0, 20)
        );

        // then
        assertThat(restaurantWithDistances.getContent())
                .extracting(RestaurantWithDistance::name)
                .containsExactlyInAnyOrderElementsOf(압구정_음식점들
                        .stream()
                        .map(Restaurant::name)
                        .toList());
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000, 3000, 5000, 30000})
    void 특정_음식점을_기준으로_일정_거리_내에_있는_모든_음식점_조회_테스트(int specificDistance) {
        // when
        Page<RestaurantWithDistance> result = restaurantWithDistanceDao.searchNearBy(
                1L,
                specificDistance,
                PageRequest.of(0, 4)
        );

        // then
        assertThat(result.getContent())
                .extracting(RestaurantWithDistance::distance)
                .allMatch(distance -> distance <= specificDistance);
    }
}
