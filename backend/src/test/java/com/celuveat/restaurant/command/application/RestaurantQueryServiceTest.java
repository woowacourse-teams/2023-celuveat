package com.celuveat.restaurant.command.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.LocationFixture.isRestaurantInArea;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1_2번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.전체영역_검색_범위;
import static com.celuveat.restaurant.fixture.RestaurantFixture.isCelebVisited;
import static com.celuveat.restaurant.fixture.RestaurantLikeFixture.음식점_좋아요;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.common.IntegrationTest;
import com.celuveat.common.SeedData;
import com.celuveat.common.util.StringUtil;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.command.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.query.RestaurantQueryService;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.query.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private final List<RestaurantSimpleResponse> seed = new ArrayList<>();

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
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(null, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(seed);
    }

    @Test
    void 셀럽으로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        for (RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (isCelebVisited(celebId, restaurantWithCelebsAndImagesSimpleResponse)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(celebId, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 카테고리로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        String category = "category:오도1호점";
        for (RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (restaurantWithCelebsAndImagesSimpleResponse.category().equals(category)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(null, category, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 음식점_이름_포함으로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        String restaurantName = " 말 랑  \n";
        for (RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (restaurantWithCelebsAndImagesSimpleResponse.name()
                    .contains(StringUtil.removeAllBlank(restaurantName))) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(null, null, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_카테고리로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        String category = "category:오도1호점";
        for (RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            List<Long> list = restaurantWithCelebsAndImagesSimpleResponse.celebs().stream().map(CelebQueryResponse::id)
                    .toList();
            if (list.contains(celebId) && restaurantWithCelebsAndImagesSimpleResponse.category().equals(category)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(celebId, category, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            List<Long> list = restaurantWithCelebsAndImagesSimpleResponse.celebs().stream().map(CelebQueryResponse::id)
                    .toList();
            if (restaurantWithCelebsAndImagesSimpleResponse.name().contains(StringUtil.removeAllBlank(restaurantName))
                    && list.contains(celebId)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(celebId, null, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 카테고리와_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        String category = "category:말랑2호점";
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (restaurantWithCelebsAndImagesSimpleResponse.name().contains(StringUtil.removeAllBlank(restaurantName))
                    && restaurantWithCelebsAndImagesSimpleResponse.category().equals(category)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(null, category, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_카테고리와_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String category = "category:로이스1호점";
        String restaurantName = "로 이스";
        for (RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (restaurantWithCelebsAndImagesSimpleResponse.name().contains(StringUtil.removeAllBlank(restaurantName))
                    && restaurantWithCelebsAndImagesSimpleResponse.category().equals(category)
                    && isCelebVisited(celebId, restaurantWithCelebsAndImagesSimpleResponse)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(celebId, category, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_거리_기준으로_음식점_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        for (RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (isRestaurantInArea(박스_1번_지점포함, restaurantWithCelebsAndImagesSimpleResponse)
                    && isCelebVisited(celebId, restaurantWithCelebsAndImagesSimpleResponse)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(celebId, null, null),
                new LocationSearchCond(
                        박스_1번_지점포함.lowLatitude(),
                        박스_1번_지점포함.highLatitude(),
                        박스_1번_지점포함.lowLongitude(),
                        박스_1번_지점포함.highLongitude()
                ),
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_음식점이름과_거리를_기준으로_음식점_조회_테스트() {
        // given
        List<RestaurantSimpleResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        String restaurantName = "로이스";
        for (RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (isRestaurantInArea(박스_1_2번_지점포함, restaurantWithCelebsAndImagesSimpleResponse)
                    && isCelebVisited(celebId, restaurantWithCelebsAndImagesSimpleResponse)
                    && restaurantWithCelebsAndImagesSimpleResponse.name()
                    .contains(StringUtil.removeAllBlank(restaurantName))) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(celebId, null, restaurantName),
                new LocationSearchCond(
                        박스_1_2번_지점포함.lowLatitude(),
                        박스_1_2번_지점포함.highLatitude(),
                        박스_1_2번_지점포함.lowLongitude(),
                        박스_1_2번_지점포함.highLongitude()
                ),
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 로그인_상태에서_음식점을_조회하면_좋아요한_음식점의_좋아요_여부에_참값이_반환한다() {
        OauthMember 오도 = 멤버("오도");
        oauthMemberRepository.save(오도);
        RestaurantSimpleResponse restaurantSimpleResponse1 = seed.get(0);
        RestaurantSimpleResponse restaurantSimpleResponse2 = seed.get(2);
        RestaurantSimpleResponse restaurantSimpleResponse3 = seed.get(4);
        RestaurantSimpleResponse restaurantSimpleResponse4 = seed.get(9);
        Restaurant 말랑1호점 = restaurantRepository.getById(restaurantSimpleResponse1.id());
        Restaurant 말랑3호점 = restaurantRepository.getById(restaurantSimpleResponse2.id());
        Restaurant 도기2호점 = restaurantRepository.getById(restaurantSimpleResponse3.id());
        Restaurant 로이스2호점 = restaurantRepository.getById(restaurantSimpleResponse4.id());
        restaurantLikeRepository.saveAll(List.of(
                음식점_좋아요(말랑1호점, 오도),
                음식점_좋아요(말랑3호점, 오도),
                음식점_좋아요(도기2호점, 오도),
                음식점_좋아요(로이스2호점, 오도)
        ));
        seed.set(0, increaseLikeCount(changeIsLikedToTrue(restaurantSimpleResponse1), 1));
        seed.set(2, increaseLikeCount(changeIsLikedToTrue(restaurantSimpleResponse2), 1));
        seed.set(4, increaseLikeCount(changeIsLikedToTrue(restaurantSimpleResponse3), 1));
        seed.set(9, increaseLikeCount(changeIsLikedToTrue(restaurantSimpleResponse4), 1));

        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(null, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                오도.id());

        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(seed);
    }

    private RestaurantSimpleResponse changeIsLikedToTrue(
            RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse) {
        return new RestaurantSimpleResponse(
                restaurantWithCelebsAndImagesSimpleResponse.id(),
                restaurantWithCelebsAndImagesSimpleResponse.name(),
                restaurantWithCelebsAndImagesSimpleResponse.category(),
                restaurantWithCelebsAndImagesSimpleResponse.roadAddress(),
                restaurantWithCelebsAndImagesSimpleResponse.latitude(),
                restaurantWithCelebsAndImagesSimpleResponse.longitude(),
                restaurantWithCelebsAndImagesSimpleResponse.phoneNumber(),
                restaurantWithCelebsAndImagesSimpleResponse.naverMapUrl(),
                restaurantWithCelebsAndImagesSimpleResponse.viewCount(),
                restaurantWithCelebsAndImagesSimpleResponse.distance(),
                true,
                restaurantWithCelebsAndImagesSimpleResponse.likeCount(),
                restaurantWithCelebsAndImagesSimpleResponse.celebs(),
                restaurantWithCelebsAndImagesSimpleResponse.images()
        );
    }

    private RestaurantSimpleResponse increaseLikeCount(
            RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse, int value) {
        return new RestaurantSimpleResponse(
                restaurantWithCelebsAndImagesSimpleResponse.id(),
                restaurantWithCelebsAndImagesSimpleResponse.name(),
                restaurantWithCelebsAndImagesSimpleResponse.category(),
                restaurantWithCelebsAndImagesSimpleResponse.roadAddress(),
                restaurantWithCelebsAndImagesSimpleResponse.latitude(),
                restaurantWithCelebsAndImagesSimpleResponse.longitude(),
                restaurantWithCelebsAndImagesSimpleResponse.phoneNumber(),
                restaurantWithCelebsAndImagesSimpleResponse.naverMapUrl(),
                restaurantWithCelebsAndImagesSimpleResponse.viewCount(),
                restaurantWithCelebsAndImagesSimpleResponse.distance(),
                restaurantWithCelebsAndImagesSimpleResponse.isLiked(),
                restaurantWithCelebsAndImagesSimpleResponse.likeCount() + value,
                restaurantWithCelebsAndImagesSimpleResponse.celebs(),
                restaurantWithCelebsAndImagesSimpleResponse.images()
        );
    }

    @Test
    void 멤버_아이디로_음식점_좋아요를_검색한다() {
        // given
        OauthMember 멤버 = 멤버("오도");
        oauthMemberRepository.save(멤버);
        RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse1 = seed.get(0);
        RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse2 = seed.get(2);
        RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse3 = seed.get(4);
        RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse4 = seed.get(9);
        Restaurant 말랑1호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse1.id());
        Restaurant 말랑3호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse2.id());
        Restaurant 도기2호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse3.id());
        Restaurant 로이스2호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse4.id());
        restaurantLikeRepository.saveAll(List.of(
                음식점_좋아요(말랑1호점, 멤버),
                음식점_좋아요(말랑3호점, 멤버),
                음식점_좋아요(도기2호점, 멤버),
                음식점_좋아요(로이스2호점, 멤버)
        ));
        List<RestaurantLikeQueryResponse> expected = new ArrayList<>(List.of(
                toRestaurantLikeQueryResponse(restaurantWithCelebsAndImagesSimpleResponse4),
                toRestaurantLikeQueryResponse(restaurantWithCelebsAndImagesSimpleResponse3),
                toRestaurantLikeQueryResponse(restaurantWithCelebsAndImagesSimpleResponse2),
                toRestaurantLikeQueryResponse(restaurantWithCelebsAndImagesSimpleResponse1)
        ));

        // when
        List<RestaurantLikeQueryResponse> restaurantLikes =
                restaurantQueryService.findAllLikedRestaurantByMemberId(멤버.id());

        // then
        assertThat(restaurantLikes).usingRecursiveComparison().isEqualTo(expected);
    }

    private RestaurantLikeQueryResponse toRestaurantLikeQueryResponse(
            RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse) {
        return new RestaurantLikeQueryResponse(
                restaurantWithCelebsAndImagesSimpleResponse.id(),
                restaurantWithCelebsAndImagesSimpleResponse.name(),
                restaurantWithCelebsAndImagesSimpleResponse.category(),
                restaurantWithCelebsAndImagesSimpleResponse.roadAddress(),
                restaurantWithCelebsAndImagesSimpleResponse.latitude(),
                restaurantWithCelebsAndImagesSimpleResponse.longitude(),
                restaurantWithCelebsAndImagesSimpleResponse.phoneNumber(),
                restaurantWithCelebsAndImagesSimpleResponse.naverMapUrl(),
                restaurantWithCelebsAndImagesSimpleResponse.celebs(),
                restaurantWithCelebsAndImagesSimpleResponse.images()
        );
    }

    @Test
    void 음식점_상세_조회_테스트() {
        // given
        RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse = seed.get(0);
        OauthMember oauthMember = 멤버("로이스");
        oauthMemberRepository.save(oauthMember);
        Restaurant restaurant = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse.id());
        restaurantLikeRepository.save(new RestaurantLike(restaurant, oauthMember));

        // when
        RestaurantDetailResponse result =
                restaurantQueryService.findRestaurantDetailById(restaurant.id(), Optional.empty());

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("likeCount", "viewCount")
                .isEqualTo(toRestaurantDetailQueryResponse(restaurantWithCelebsAndImagesSimpleResponse, false));
        assertThat(result.likeCount()).isEqualTo(1);
    }

    @Test
    void 음식점_상세_조회시_좋아요_여부_포함_테스트() {
        // given
        RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse = seed.get(0);
        OauthMember oauthMember = 멤버("로이스");
        oauthMemberRepository.save(oauthMember);
        Restaurant restaurant = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse.id());
        restaurantLikeRepository.save(new RestaurantLike(restaurant, oauthMember));

        // when
        RestaurantDetailResponse result =
                restaurantQueryService.findRestaurantDetailById(restaurant.id(), Optional.of(oauthMember.id()));

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("likeCount", "viewCount")
                .isEqualTo(toRestaurantDetailQueryResponse(restaurantWithCelebsAndImagesSimpleResponse, true));
        assertThat(result.likeCount()).isEqualTo(1);
    }

    private RestaurantDetailResponse toRestaurantDetailQueryResponse(
            RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse,
            boolean isLiked
    ) {
        return new RestaurantDetailResponse(
                restaurantWithCelebsAndImagesSimpleResponse.id(),
                restaurantWithCelebsAndImagesSimpleResponse.name(),
                restaurantWithCelebsAndImagesSimpleResponse.category(),
                restaurantWithCelebsAndImagesSimpleResponse.roadAddress(),
                restaurantWithCelebsAndImagesSimpleResponse.latitude(),
                restaurantWithCelebsAndImagesSimpleResponse.longitude(),
                restaurantWithCelebsAndImagesSimpleResponse.phoneNumber(),
                restaurantWithCelebsAndImagesSimpleResponse.naverMapUrl(),
                0, // likeCount
                0, // viewCount
                isLiked,
                restaurantWithCelebsAndImagesSimpleResponse.celebs(),
                restaurantWithCelebsAndImagesSimpleResponse.images()
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 50, 100, 500, 1000, 2000, 3000, 5000, 30000})
    void 특정_음식점을_기준으로_일정_거리_내에_있는_모든_음식점_조회_테스트(int specificDistance) {
        // given
        RestaurantSimpleResponse restaurant = seed.get(0);

        // when
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllNearByDistanceWithoutSpecificRestaurant(
                specificDistance,
                restaurant.id(),
                Optional.empty(),
                PageRequest.of(0, 4)
        );

        // then
        assertThat(result.getContent())
                .extracting(RestaurantSimpleResponse::distance)
                .allMatch(distance -> distance <= specificDistance);
        assertThat(result.getContent())
                .extracting(RestaurantSimpleResponse::name)
                .doesNotContain(restaurant.name());
    }

    @Test
    void 주변_음식점을_조회할_때_좋아요_여부가_포함된다() {
        // given
        OauthMember 도기 = oauthMemberRepository.save(멤버("도기"));
        oauthMemberRepository.save(도기);
        for (RestaurantSimpleResponse restaurantSimpleResponse : seed) {
            Restaurant restaurant = restaurantRepository.getById(restaurantSimpleResponse.id());
            restaurantLikeRepository.save(음식점_좋아요(restaurant, 도기));
        }

        // when
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllNearByDistanceWithoutSpecificRestaurant(
                50000,
                seed.get(0).id(),
                Optional.of(도기.id()),
                PageRequest.of(0, 4)
        );

        // then
        List<RestaurantSimpleResponse> content = result.getContent();
        assertThat(content).extracting(RestaurantSimpleResponse::isLiked)
                .allMatch(isLiked -> isLiked);
    }

    @Nested
    class 좋아요수_테스트 {

        private Long 오도_아이디;
        private Long 로이스_아이디;
        private Long 도기_아이디;
        private Long 말랑_아이디;
        private RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse1;
        private RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse2;
        private RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse3;
        private RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse4;
        private RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse5;
        private RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse6;

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
            restaurantWithCelebsAndImagesSimpleResponse1 = seed.get(0);
            restaurantWithCelebsAndImagesSimpleResponse2 = seed.get(2);
            restaurantWithCelebsAndImagesSimpleResponse3 = seed.get(3);
            restaurantWithCelebsAndImagesSimpleResponse4 = seed.get(4);
            restaurantWithCelebsAndImagesSimpleResponse5 = seed.get(8);
            restaurantWithCelebsAndImagesSimpleResponse6 = seed.get(9);
            Restaurant 말랑1호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse1.id());
            Restaurant 말랑3호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse2.id());
            Restaurant 도기1호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse3.id());
            Restaurant 도기2호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse4.id());
            Restaurant 로이스1호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse5.id());
            Restaurant 로이스2호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse6.id());
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
            seed.set(0, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse1, 2));
            seed.set(2, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse2, 2));
            seed.set(3, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse3, 1));
            seed.set(4, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse4, 3));
            seed.set(8, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse5, 3));
            seed.set(9, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse6, 1));

            // when
            Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, null, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    null
            );

            // then
            결과를_검증한다(result, seed);
        }

        private void 결과를_검증한다(Page<RestaurantSimpleResponse> result,
                              List<RestaurantSimpleResponse> expected) {
            assertThat(result).isNotEmpty();
            assertThat(result.getContent())
                    .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .ignoringCollectionOrder()
                    .isEqualTo(expected);
        }

        @Test
        void 오도로_음식점을_조회하면_오도가_좋아요한_음식점의_좋아요여부에_참이_반환되고_모두의_좋아요수가_함께_반환된다() {
            // given
            seed.set(0, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebsAndImagesSimpleResponse1), 2));
            seed.set(2, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebsAndImagesSimpleResponse2), 2));
            seed.set(3, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse3, 1));
            seed.set(4, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebsAndImagesSimpleResponse4), 3));
            seed.set(8, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse5, 3));
            seed.set(9, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebsAndImagesSimpleResponse6), 1));

            // when
            Page<RestaurantSimpleResponse> result = 음식점을_조회한다(오도_아이디);

            // then
            결과를_검증한다(result, seed);
        }

        private Page<RestaurantSimpleResponse> 음식점을_조회한다(Long memberId) {
            return restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, null, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    memberId);
        }

        @Test
        void 로이스로_음식점을_조회하면_로이스가_좋아요한_음식점의_좋아요여부에_참이_반환되고_모두의_좋아요수가_함께_반환된다() {
            // given
            seed.set(0, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebsAndImagesSimpleResponse1), 2));
            seed.set(2, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse2, 2));
            seed.set(3, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebsAndImagesSimpleResponse3), 1));
            seed.set(4, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebsAndImagesSimpleResponse4), 3));
            seed.set(8, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebsAndImagesSimpleResponse5), 3));
            seed.set(9, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse6, 1));

            // when
            Page<RestaurantSimpleResponse> result = 음식점을_조회한다(로이스_아이디);

            // then
            결과를_검증한다(result, seed);
        }

        @Test
        void 도기로_음식점을_조회하면_도기가_좋아요한_음식점의_좋아요여부에_참이_반환되고_모두의_좋아요수가_함께_반환된다() {
            // given
            seed.set(0, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse1, 2));
            seed.set(2, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse2, 2));
            seed.set(3, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse3, 1));
            seed.set(4, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebsAndImagesSimpleResponse4), 3));
            seed.set(8, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebsAndImagesSimpleResponse5), 3));
            seed.set(9, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse6, 1));

            // when
            Page<RestaurantSimpleResponse> result = 음식점을_조회한다(도기_아이디);

            // then
            결과를_검증한다(result, seed);
        }

        @Test
        void 말랑으로_음식점을_조회하면_말랑이_좋아요한_음식점의_좋아요여부에_참이_반환되고_모두의_좋아요수가_함께_반환된다() {
            // given
            seed.set(0, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse1, 2));
            seed.set(2, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebsAndImagesSimpleResponse2), 2));
            seed.set(3, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse3, 1));
            seed.set(4, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse4, 3));
            seed.set(8, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebsAndImagesSimpleResponse5), 3));
            seed.set(9, increaseLikeCount(restaurantWithCelebsAndImagesSimpleResponse6, 1));

            // when
            Page<RestaurantSimpleResponse> result = 음식점을_조회한다(말랑_아이디);

            // then
            결과를_검증한다(result, seed);
        }
    }

    @Nested
    class 조회수_테스트 {

        private RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse1;
        private RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse2;
        private RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse3;
        private RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse4;

        @BeforeEach
        void setUp() {
            restaurantWithCelebsAndImagesSimpleResponse1 = seed.get(0);
            restaurantWithCelebsAndImagesSimpleResponse2 = seed.get(2);
            restaurantWithCelebsAndImagesSimpleResponse3 = seed.get(4);
            restaurantWithCelebsAndImagesSimpleResponse4 = seed.get(9);
        }

        @Test
        void 음식점을_조회하면_조회수를_함께_반환한다() {
            // given
            음식점들의_조회수를_높인다();
            seed.set(0, increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse1, 4));
            seed.set(2, increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse2, 2));
            seed.set(4, increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse3, 1));
            seed.set(9, increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse4, 5));

            // when
            Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, null, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertThat(result).isNotEmpty();
            assertThat(result.getContent())
                    .isSortedAccordingTo(comparing(RestaurantSimpleResponse::distance))
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .ignoringCollectionOrder()
                    .isEqualTo(seed);
        }

        private void 음식점들의_조회수를_높인다() {
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse1.id());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse1.id());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse1.id());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse1.id());

            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse2.id());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse2.id());

            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse3.id());

            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse4.id());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse4.id());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse4.id());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse4.id());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse4.id());
        }

        private RestaurantSimpleResponse increaseViewCount(
                RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse, int value) {
            return new RestaurantSimpleResponse(
                    restaurantWithCelebsAndImagesSimpleResponse.id(),
                    restaurantWithCelebsAndImagesSimpleResponse.name(),
                    restaurantWithCelebsAndImagesSimpleResponse.category(),
                    restaurantWithCelebsAndImagesSimpleResponse.roadAddress(),
                    restaurantWithCelebsAndImagesSimpleResponse.latitude(),
                    restaurantWithCelebsAndImagesSimpleResponse.longitude(),
                    restaurantWithCelebsAndImagesSimpleResponse.phoneNumber(),
                    restaurantWithCelebsAndImagesSimpleResponse.naverMapUrl(),
                    restaurantWithCelebsAndImagesSimpleResponse.viewCount() + value,
                    restaurantWithCelebsAndImagesSimpleResponse.distance(),
                    restaurantWithCelebsAndImagesSimpleResponse.isLiked(),
                    restaurantWithCelebsAndImagesSimpleResponse.likeCount(),
                    restaurantWithCelebsAndImagesSimpleResponse.celebs(),
                    restaurantWithCelebsAndImagesSimpleResponse.images()
            );
        }
    }
}
