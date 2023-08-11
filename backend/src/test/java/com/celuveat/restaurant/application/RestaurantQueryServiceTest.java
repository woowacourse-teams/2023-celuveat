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
import com.celuveat.restaurant.domain.RestaurantLike;
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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
    private RestaurantService restaurantService;

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
    void 로그인_상태에서_음식점을_조회하면_좋아요한_음식점의_좋아요_여부에_참값이_반환한다() {
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
        seed.set(0, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse1), 1));
        seed.set(2, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse2), 1));
        seed.set(4, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse3), 1));
        seed.set(9, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse4), 1));

        Page<RestaurantQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(null, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                멤버.id());

        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(seed);
    }

    private RestaurantQueryResponse changeIsLikedToTrue(RestaurantQueryResponse restaurantQueryResponse) {
        return new RestaurantQueryResponse(
                restaurantQueryResponse.id(),
                restaurantQueryResponse.name(),
                restaurantQueryResponse.category(),
                restaurantQueryResponse.roadAddress(),
                restaurantQueryResponse.latitude(),
                restaurantQueryResponse.longitude(),
                restaurantQueryResponse.phoneNumber(),
                restaurantQueryResponse.naverMapUrl(),
                restaurantQueryResponse.viewCount(),
                restaurantQueryResponse.distance(),
                true,
                restaurantQueryResponse.likeCount(),
                restaurantQueryResponse.celebs(),
                restaurantQueryResponse.images()
        );
    }

    private RestaurantQueryResponse increaseLikeCount(RestaurantQueryResponse restaurantQueryResponse, int value) {
        return new RestaurantQueryResponse(
                restaurantQueryResponse.id(),
                restaurantQueryResponse.name(),
                restaurantQueryResponse.category(),
                restaurantQueryResponse.roadAddress(),
                restaurantQueryResponse.latitude(),
                restaurantQueryResponse.longitude(),
                restaurantQueryResponse.phoneNumber(),
                restaurantQueryResponse.naverMapUrl(),
                restaurantQueryResponse.viewCount(),
                restaurantQueryResponse.distance(),
                restaurantQueryResponse.isLiked(),
                restaurantQueryResponse.likeCount() + value,
                restaurantQueryResponse.celebs(),
                restaurantQueryResponse.images()
        );
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
        List<RestaurantLikeQueryResponse> expected = new ArrayList<>(List.of(
                toRestaurantLikeQueryResponse(restaurantQueryResponse1),
                toRestaurantLikeQueryResponse(restaurantQueryResponse2),
                toRestaurantLikeQueryResponse(restaurantQueryResponse3),
                toRestaurantLikeQueryResponse(restaurantQueryResponse4)
        ));

        // when
        List<RestaurantLikeQueryResponse> restaurantLikes = restaurantQueryService.findAllWithMemberLiked(멤버.id());

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
        RestaurantQueryResponse restaurantQueryResponse = seed.get(0);
        OauthMember oauthMember = 멤버("로이스");
        oauthMemberRepository.save(oauthMember);
        Restaurant restaurant = restaurantRepository.getById(restaurantQueryResponse.id());
        restaurantLikeRepository.save(new RestaurantLike(restaurant, oauthMember));

        // when
        RestaurantDetailQueryResponse result =
                restaurantQueryService.findRestaurantDetailById(restaurant.id());

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("likeCount", "viewCount")
                .isEqualTo(toRestaurantDetailQueryResponse(restaurantQueryResponse));
        assertThat(result.likeCount()).isEqualTo(1);
    }

    private RestaurantDetailQueryResponse toRestaurantDetailQueryResponse(
            RestaurantQueryResponse restaurantQueryResponse
    ) {
        return new RestaurantDetailQueryResponse(
                restaurantQueryResponse.id(),
                restaurantQueryResponse.name(),
                restaurantQueryResponse.category(),
                restaurantQueryResponse.roadAddress(),
                restaurantQueryResponse.latitude(),
                restaurantQueryResponse.longitude(),
                restaurantQueryResponse.phoneNumber(),
                restaurantQueryResponse.naverMapUrl(),
                0, // likeCount
                0, // viewCount
                restaurantQueryResponse.celebs(),
                restaurantQueryResponse.images()
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 50, 100, 500, 1000, 2000, 3000, 5000, 30000})
    void 특정_음식점을_기준으로_일정_거리_내에_있는_모든_음식점_조회_테스트(int specificDistance) {
        // given
        RestaurantQueryResponse restaurant = seed.get(0);

        // when
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAllNearByDistanceWithoutSpecificRestaurant(
                specificDistance,
                restaurant.id(),
                PageRequest.of(0, 4)
        );

        // then
        assertThat(result.getContent())
                .extracting(RestaurantQueryResponse::distance)
                .allMatch(distance -> distance <= specificDistance);
        assertThat(result.getContent())
                .extracting(RestaurantQueryResponse::name)
                .doesNotContain(restaurant.name());
    }

    @Nested
    class 좋아요수_테스트 {

        private Long 오도_아이디;
        private Long 로이스_아이디;
        private Long 도기_아이디;
        private Long 말랑_아이디;
        private RestaurantQueryResponse restaurantQueryResponse1;
        private RestaurantQueryResponse restaurantQueryResponse2;
        private RestaurantQueryResponse restaurantQueryResponse3;
        private RestaurantQueryResponse restaurantQueryResponse4;
        private RestaurantQueryResponse restaurantQueryResponse5;
        private RestaurantQueryResponse restaurantQueryResponse6;

        @BeforeEach
        void setUp() {
            OauthMember 오도 = 멤버("오도");
            OauthMember 로이스 = 멤버("로이스");
            OauthMember 도기 = 멤버("도기");
            OauthMember 말랑 = 멤버("말랑");
            oauthMemberRepository.save(오도);
            oauthMemberRepository.save(로이스);
            oauthMemberRepository.save(도기);
            oauthMemberRepository.save(말랑);
            오도_아이디 = 오도.id();
            로이스_아이디 = 로이스.id();
            도기_아이디 = 도기.id();
            말랑_아이디 = 말랑.id();
            restaurantQueryResponse1 = seed.get(0);
            restaurantQueryResponse2 = seed.get(2);
            restaurantQueryResponse3 = seed.get(3);
            restaurantQueryResponse4 = seed.get(4);
            restaurantQueryResponse5 = seed.get(8);
            restaurantQueryResponse6 = seed.get(9);
            Restaurant 말랑1호점 = restaurantRepository.getById(restaurantQueryResponse1.id());
            Restaurant 말랑3호점 = restaurantRepository.getById(restaurantQueryResponse2.id());
            Restaurant 도기1호점 = restaurantRepository.getById(restaurantQueryResponse3.id());
            Restaurant 도기2호점 = restaurantRepository.getById(restaurantQueryResponse4.id());
            Restaurant 로이스1호점 = restaurantRepository.getById(restaurantQueryResponse5.id());
            Restaurant 로이스2호점 = restaurantRepository.getById(restaurantQueryResponse6.id());
            restaurantLikeRepository.saveAll(List.of(
                    음식점_좋아요(말랑1호점, 오도),
                    음식점_좋아요(말랑3호점, 오도),
                    음식점_좋아요(도기2호점, 오도),
                    음식점_좋아요(로이스2호점, 오도),

                    음식점_좋아요(말랑1호점, 로이스),
                    음식점_좋아요(도기1호점, 로이스),
                    음식점_좋아요(도기2호점, 로이스),
                    음식점_좋아요(로이스1호점, 로이스),

                    음식점_좋아요(도기2호점, 도기),
                    음식점_좋아요(로이스1호점, 도기),

                    음식점_좋아요(말랑3호점, 말랑),
                    음식점_좋아요(로이스1호점, 말랑)
            ));
        }

        @Test
        void 비회원으로_음식점을_조회하면_음식점의_좋아요여부에_모두_거짓이_반환되고_모두의_좋아요수가_함께_반환된다() {
            // given
            seed.set(0, increaseLikeCount(restaurantQueryResponse1, 2));
            seed.set(2, increaseLikeCount(restaurantQueryResponse2, 2));
            seed.set(3, increaseLikeCount(restaurantQueryResponse3, 1));
            seed.set(4, increaseLikeCount(restaurantQueryResponse4, 3));
            seed.set(8, increaseLikeCount(restaurantQueryResponse5, 3));
            seed.set(9, increaseLikeCount(restaurantQueryResponse6, 1));

            // when
            Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                    new RestaurantSearchCond(null, null, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100));

            // then
            결과를_검증한다(result, seed);
        }

        private void 결과를_검증한다(Page<RestaurantQueryResponse> result, List<RestaurantQueryResponse> expected) {
            assertThat(result).isNotEmpty();
            assertThat(result.getContent())
                    .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .ignoringCollectionOrder()
                    .isEqualTo(expected);
        }

        @Test
        void 오도로_음식점을_조회하면_오도가_좋아요한_음식점의_좋아요여부에_참이_반환되고_모두의_좋아요수가_함께_반환된다() {
            // given
            seed.set(0, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse1), 2));
            seed.set(2, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse2), 2));
            seed.set(3, increaseLikeCount(restaurantQueryResponse3, 1));
            seed.set(4, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse4), 3));
            seed.set(8, increaseLikeCount(restaurantQueryResponse5, 3));
            seed.set(9, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse6), 1));

            // when
            Page<RestaurantQueryResponse> result = 음식점을_조회한다(오도_아이디);

            // then
            결과를_검증한다(result, seed);
        }

        private Page<RestaurantQueryResponse> 음식점을_조회한다(Long memberId) {
            return restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, null, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    memberId);
        }

        @Test
        void 로이스로_음식점을_조회하면_로이스가_좋아요한_음식점의_좋아요여부에_참이_반환되고_모두의_좋아요수가_함께_반환된다() {
            // given
            seed.set(0, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse1), 2));
            seed.set(2, increaseLikeCount(restaurantQueryResponse2, 2));
            seed.set(3, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse3), 1));
            seed.set(4, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse4), 3));
            seed.set(8, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse5), 3));
            seed.set(9, increaseLikeCount(restaurantQueryResponse6, 1));

            // when
            Page<RestaurantQueryResponse> result = 음식점을_조회한다(로이스_아이디);

            // then
            결과를_검증한다(result, seed);
        }

        @Test
        void 도기로_음식점을_조회하면_도기가_좋아요한_음식점의_좋아요여부에_참이_반환되고_모두의_좋아요수가_함께_반환된다() {
            // given
            seed.set(0, increaseLikeCount(restaurantQueryResponse1, 2));
            seed.set(2, increaseLikeCount(restaurantQueryResponse2, 2));
            seed.set(3, increaseLikeCount(restaurantQueryResponse3, 1));
            seed.set(4, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse4), 3));
            seed.set(8, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse5), 3));
            seed.set(9, increaseLikeCount(restaurantQueryResponse6, 1));

            // when
            Page<RestaurantQueryResponse> result = 음식점을_조회한다(도기_아이디);

            // then
            결과를_검증한다(result, seed);
        }

        @Test
        void 말랑으로_음식점을_조회하면_말랑이_좋아요한_음식점의_좋아요여부에_참이_반환되고_모두의_좋아요수가_함께_반환된다() {
            // given
            seed.set(0, increaseLikeCount(restaurantQueryResponse1, 2));
            seed.set(2, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse2), 2));
            seed.set(3, increaseLikeCount(restaurantQueryResponse3, 1));
            seed.set(4, increaseLikeCount(restaurantQueryResponse4, 3));
            seed.set(8, increaseLikeCount(changeIsLikedToTrue(restaurantQueryResponse5), 3));
            seed.set(9, increaseLikeCount(restaurantQueryResponse6, 1));

            // when
            Page<RestaurantQueryResponse> result = 음식점을_조회한다(말랑_아이디);

            // then
            결과를_검증한다(result, seed);
        }
    }

    @Nested
    class 조회수_테스트 {

        private RestaurantQueryResponse restaurantQueryResponse1;
        private RestaurantQueryResponse restaurantQueryResponse2;
        private RestaurantQueryResponse restaurantQueryResponse3;
        private RestaurantQueryResponse restaurantQueryResponse4;

        @BeforeEach
        void setUp() {
            restaurantQueryResponse1 = seed.get(0);
            restaurantQueryResponse2 = seed.get(2);
            restaurantQueryResponse3 = seed.get(4);
            restaurantQueryResponse4 = seed.get(9);
        }

        @Test
        void 음식점을_조회하면_조회수를_함께_반환한다() {
            // given
            음식점들의_조회수를_높인다();
            seed.set(0, increaseViewCount(restaurantQueryResponse1, 4));
            seed.set(2, increaseViewCount(restaurantQueryResponse2, 2));
            seed.set(4, increaseViewCount(restaurantQueryResponse3, 1));
            seed.set(9, increaseViewCount(restaurantQueryResponse4, 5));

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

        private void 음식점들의_조회수를_높인다() {
            restaurantService.increaseViewCount(restaurantQueryResponse1.id());
            restaurantService.increaseViewCount(restaurantQueryResponse1.id());
            restaurantService.increaseViewCount(restaurantQueryResponse1.id());
            restaurantService.increaseViewCount(restaurantQueryResponse1.id());

            restaurantService.increaseViewCount(restaurantQueryResponse2.id());
            restaurantService.increaseViewCount(restaurantQueryResponse2.id());

            restaurantService.increaseViewCount(restaurantQueryResponse3.id());

            restaurantService.increaseViewCount(restaurantQueryResponse4.id());
            restaurantService.increaseViewCount(restaurantQueryResponse4.id());
            restaurantService.increaseViewCount(restaurantQueryResponse4.id());
            restaurantService.increaseViewCount(restaurantQueryResponse4.id());
            restaurantService.increaseViewCount(restaurantQueryResponse4.id());
        }

        private RestaurantQueryResponse increaseViewCount(RestaurantQueryResponse restaurantQueryResponse, int value) {
            return new RestaurantQueryResponse(
                    restaurantQueryResponse.id(),
                    restaurantQueryResponse.name(),
                    restaurantQueryResponse.category(),
                    restaurantQueryResponse.roadAddress(),
                    restaurantQueryResponse.latitude(),
                    restaurantQueryResponse.longitude(),
                    restaurantQueryResponse.phoneNumber(),
                    restaurantQueryResponse.naverMapUrl(),
                    restaurantQueryResponse.viewCount() + value,
                    restaurantQueryResponse.distance(),
                    restaurantQueryResponse.isLiked(),
                    restaurantQueryResponse.likeCount(),
                    restaurantQueryResponse.celebs(),
                    restaurantQueryResponse.images()
            );
        }
    }
}
