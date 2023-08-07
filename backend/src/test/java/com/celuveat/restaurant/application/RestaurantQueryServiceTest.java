package com.celuveat.restaurant.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.LocationFixture.isRestaurantInArea;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1_2번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.전체영역_검색_범위;
import static com.celuveat.restaurant.fixture.RestaurantFixture.isCelebVisited;
import static com.celuveat.restaurant.fixture.RestaurantLikeFixture.음식점_좋아요;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.common.IntegrationTest;
import com.celuveat.common.SeedData;
import com.celuveat.common.util.StringUtil;
import com.celuveat.restaurant.application.dto.CelebQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import com.celuveat.restaurant.domain.RestaurantRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 조회용 서비스(RestaurantQueryService) 은(는)")
class RestaurantQueryServiceTest {

    private final List<RestaurantQueryResponse> seed = new ArrayList<>();

    @Autowired
    private SeedData seedData;

    @Autowired
    private EntityManager em;

    @Autowired
    private RestaurantLikeRepository restaurantLikeRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OauthMemberRepository oauthMemberRepository;

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
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(null, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
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
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(celebId, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
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
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(null, category, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
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
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(null, null, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 100));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
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
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(celebId, category, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
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
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(celebId, null, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 100));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
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
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(null, category, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 100));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
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
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(celebId, category, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 100));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_거리_기준으로_음식점_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (isRestaurantInArea(박스_1번_지점포함, restaurantQueryResponse)
                    && isCelebVisited(celebId, restaurantQueryResponse)) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(celebId, null, null),
                new LocationSearchCond(
                        박스_1번_지점포함.lowLatitude(),
                        박스_1번_지점포함.highLatitude(),
                        박스_1번_지점포함.lowLongitude(),
                        박스_1번_지점포함.highLongitude()
                ),
                PageRequest.of(0, 100));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_음식점이름과_거리를_기준으로_음식점_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        String restaurantName = "로이스";
        for (RestaurantQueryResponse restaurantQueryResponse : seed) {
            if (isRestaurantInArea(박스_1_2번_지점포함, restaurantQueryResponse)
                    && isCelebVisited(celebId, restaurantQueryResponse)
                    && restaurantQueryResponse.name().contains(StringUtil.removeAllBlank(restaurantName))) {
                expected.add(restaurantQueryResponse);
            }
        }

        // when
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                new RestaurantSearchCond(celebId, null, restaurantName),
                new LocationSearchCond(
                        박스_1_2번_지점포함.lowLatitude(),
                        박스_1_2번_지점포함.highLatitude(),
                        박스_1_2번_지점포함.lowLongitude(),
                        박스_1_2번_지점포함.highLongitude()
                ),
                PageRequest.of(0, 100));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 멤버_아이디로_음식점_좋아요를_검색한다() {
        // given
        OauthMember 멤버 = 멤버("오도");
        oauthMemberRepository.save(멤버);
        RestaurantQueryResponse restaurantQueryResponse1 = seed.get(0);
        RestaurantQueryResponse restaurantQueryResponse2 = seed.get(2);
        RestaurantQueryResponse restaurantQueryResponse3 = seed.get(4);
        RestaurantQueryResponse restaurantQueryResponse4 = seed.get(9);
        Restaurant 말랑1호점 = restaurantRepository.getById(restaurantQueryResponse1.id());
        Restaurant 말랑3호점 = restaurantRepository.getById(restaurantQueryResponse2.id());
        Restaurant 도기2호점 = restaurantRepository.getById(restaurantQueryResponse3.id());
        Restaurant 로이스2호점 = restaurantRepository.getById(restaurantQueryResponse4.id());
        restaurantLikeRepository.saveAll(List.of(
                음식점_좋아요(말랑1호점, 멤버),
                음식점_좋아요(말랑3호점, 멤버),
                음식점_좋아요(도기2호점, 멤버),
                음식점_좋아요(로이스2호점, 멤버)
        ));
        List<RestaurantLikeQueryResponse> expected = new ArrayList<>();
        expected.addAll(List.of(
                toRestaurantLikeQueryResponse(restaurantQueryResponse1),
                toRestaurantLikeQueryResponse(restaurantQueryResponse2),
                toRestaurantLikeQueryResponse(restaurantQueryResponse3),
                toRestaurantLikeQueryResponse(restaurantQueryResponse4)
        ));

        // when
        List<RestaurantLikeQueryResponse> restaurantLikes = restaurantQueryService.findAllByMemberId(멤버.id());

        // then
        assertThat(restaurantLikes).usingRecursiveComparison().isEqualTo(expected);
    }

    private RestaurantLikeQueryResponse toRestaurantLikeQueryResponse(RestaurantQueryResponse restaurantQueryResponse) {
        return new RestaurantLikeQueryResponse(
                restaurantQueryResponse.id(),
                restaurantQueryResponse.name(),
                restaurantQueryResponse.category(),
                restaurantQueryResponse.roadAddress(),
                restaurantQueryResponse.latitude(),
                restaurantQueryResponse.longitude(),
                restaurantQueryResponse.phoneNumber(),
                restaurantQueryResponse.naverMapUrl(),
                restaurantQueryResponse.celebs(),
                restaurantQueryResponse.images()
        );
    }

    @Test
    void 음식점_상세_조회_테스트() {
        // given
        String likedRestaurant = "로이스2호점";
        Long restaurantId = seed.stream()
                .filter(restaurant -> restaurant.name().equals(likedRestaurant))
                .findFirst().orElseThrow()
                .id();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        // when
        RestaurantDetailQueryResponse result =
                restaurantQueryService.findRestaurantDetailById(restaurantId);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("likeCount", "viewCount", "imageUrls", "videos")
                .isEqualTo(restaurant);
        assertThat(result.likeCount()).isEqualTo(1);
    }
}
