package com.celuveat.restaurant.query;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.celeb.exception.CelebExceptionType.NOT_FOUND_CELEB;
import static com.celuveat.celeb.fixture.CelebFixture.셀럽;
import static com.celuveat.common.SeedData.isCelebVisited;
import static com.celuveat.common.SeedData.isContainsRestaurantName;
import static com.celuveat.common.SeedData.isRestaurantInArea;
import static com.celuveat.common.SeedData.isSameCategory;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1_2번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.전체영역_검색_범위;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.video.fixture.VideoFixture.영상;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.celeb.exception.CelebException;
import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantImageQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchQueryResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayName("음식점 조회용 서비스(RestaurantQueryService) 은(는)")
class RestaurantQueryServiceTest extends IntegrationTest {

    private List<RestaurantSearchQueryResponse> seed;

    @BeforeEach
    void setUp() {
        seed = new ArrayList<>(seedData.insertSeedData());
        em.flush();
        em.clear();
        System.out.println("=============[INSERT SEED DATA]============");
    }

    @Nested
    class 음식점_상세_조회 {

        private RestaurantSearchQueryResponse RestaurantSearchQueryResponse;
        private Long celebId;
        private OauthMember oauthMember;
        private Restaurant restaurant;

        @BeforeEach
        void setUp() {
            RestaurantSearchQueryResponse = seed.get(0);
            celebId = RestaurantSearchQueryResponse.celebs().get(0).id();
            oauthMember = oauthMemberRepository.save(멤버("로이스"));
            restaurant = restaurantRepository.getById(RestaurantSearchQueryResponse.id());
            restaurantLikeRepository.save(RestaurantLike.create(restaurant, oauthMember));
        }

        @Test
        void 회원_아이디_없이_음식점_상세_조회() {
            // given
            RestaurantDetailQueryResponse expected = toRestaurantDetailResponse(RestaurantSearchQueryResponse, false);

            // when
            RestaurantDetailQueryResponse result =
                    restaurantQueryService.findRestaurantDetailById(restaurant.id(), celebId, null);

            // then
            assertAll(
                    () -> assertThat(result.likeCount()).isEqualTo(1),
                    () -> assertThat(result)
                            .usingRecursiveComparison()
                            .ignoringFields("likeCount")
                            .ignoringCollectionOrder()
                            .isEqualTo(expected)
            );
        }

        @Test
        void 회원_아이디를_포함해서_음식점_상세_조회시_좋아요_여부를_포함한다() {
            // given
            RestaurantDetailQueryResponse expected = toRestaurantDetailResponse(RestaurantSearchQueryResponse, true);

            // when
            RestaurantDetailQueryResponse result =
                    restaurantQueryService.findRestaurantDetailById(restaurant.id(), celebId, oauthMember.id());

            // then
            assertAll(
                    () -> assertThat(result.likeCount()).isEqualTo(1),
                    () -> assertThat(result)
                            .usingRecursiveComparison()
                            .ignoringFields("likeCount")
                            .ignoringCollectionOrder()
                            .isEqualTo(expected)
            );
        }

        private RestaurantDetailQueryResponse toRestaurantDetailResponse(
                RestaurantSearchQueryResponse restaurantSearchQueryResponse,
                boolean isLiked
        ) {
            return new RestaurantDetailQueryResponse(
                    restaurantSearchQueryResponse.id(),
                    restaurantSearchQueryResponse.name(),
                    restaurantSearchQueryResponse.category(),
                    restaurantSearchQueryResponse.superCategory(),
                    restaurantSearchQueryResponse.roadAddress(),
                    restaurantSearchQueryResponse.latitude(),
                    restaurantSearchQueryResponse.longitude(),
                    restaurantSearchQueryResponse.phoneNumber(),
                    restaurantSearchQueryResponse.naverMapUrl(),
                    0, // likeCount
                    0, // viewCount
                    isLiked,
                    restaurantSearchQueryResponse.rating(),
                    restaurantSearchQueryResponse.celebs(),
                    restaurantSearchQueryResponse.images()
            );
        }

        @Test
        void 셀럽ID가_포함되면_정렬한다() {
            // given
            Long celebId = RestaurantSearchQueryResponse.celebs().get(RestaurantSearchQueryResponse.celebs().size() - 1).id();
            Celeb targetCeleb = celebRepository.findById(celebId)
                    .orElseThrow(() -> new CelebException(NOT_FOUND_CELEB));

            // when
            RestaurantDetailQueryResponse result =
                    restaurantQueryService.findRestaurantDetailById(restaurant.id(), celebId, null);

            // then
            assertAll(
                    () -> assertThat(result.celebs().get(0))
                            .usingRecursiveComparison()
                            .isEqualTo(CelebQueryResponse.from(result.id(), targetCeleb)),
                    () -> assertThat(result.images().get(0).author())
                            .isEqualTo(targetCeleb.name())
            );
        }
    }

    @Nested
    class 음식점_전체_조회 {

        @Test
        void 전체_음식점_조회() {
            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, null, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.getContent())
                            .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::distance))
                            .usingRecursiveComparison()
                            .ignoringFields("distance")
                            .ignoringCollectionOrder()
                            .isEqualTo(seed)
            );
        }

        @Test
        void 셀럽으로_조회() {
            // given
            Long celebId = 1L;
            List<RestaurantSearchQueryResponse> expected = seed.stream()
                    .filter(it -> isCelebVisited(celebId, it))
                    .toList();

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(celebId, null, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.getContent())
                            .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::distance))
                            .usingRecursiveComparison()
                            .ignoringFields("distance")
                            .ignoringCollectionOrder()
                            .isEqualTo(expected)
            );
        }

        @Test
        void 카테고리로_조회() {
            // given
            String category = "superCategory:오도1호점";
            List<RestaurantSearchQueryResponse> expected = seed.stream()
                    .filter(it -> it.superCategory().equals(category))
                    .toList();

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, category, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.getContent())
                            .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::distance))
                            .usingRecursiveComparison()
                            .ignoringFields("distance")
                            .ignoringCollectionOrder()
                            .isEqualTo(expected)
            );
        }

        @Test
        void 음식점_이름으로_조회() {
            // given
            String restaurantName = " 말 랑  \n";
            List<RestaurantSearchQueryResponse> expected = seed.stream()
                    .filter(it -> isContainsRestaurantName(restaurantName, it))
                    .toList();

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, null, restaurantName),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.getContent())
                            .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::distance))
                            .usingRecursiveComparison()
                            .ignoringFields("distance")
                            .ignoringCollectionOrder()
                            .isEqualTo(expected)
            );
        }

        @Test
        void 셀럽과_카테고리로_조회_테스트() {
            // given
            Long celebId = 1L;
            String category = "superCategory:오도1호점";
            List<RestaurantSearchQueryResponse> expected = seed.stream()
                    .filter(it -> isCelebVisited(celebId, it) && it.superCategory().equals(category))
                    .toList();

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(celebId, category, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.getContent())
                            .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::distance))
                            .usingRecursiveComparison()
                            .ignoringFields("distance")
                            .ignoringCollectionOrder()
                            .isEqualTo(expected)
            );
        }

        @Test
        void 셀럽과_음식점_이름으로_조회_테스트() {
            // given
            Long celebId = 2L;
            String restaurantName = "\n      말 \n랑  \n";
            List<RestaurantSearchQueryResponse> expected = seed.stream()
                    .filter(it -> isCelebVisited(celebId, it) && isContainsRestaurantName(restaurantName, it))
                    .toList();

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(celebId, null, restaurantName),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.getContent())
                            .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::distance))
                            .usingRecursiveComparison()
                            .ignoringFields("distance")
                            .ignoringCollectionOrder()
                            .isEqualTo(expected)
            );
        }

        @Test
        void 카테고리와_음식점_이름으로_조회_테스트() {
            // given
            String category = "superCategory:말랑2호점";
            String restaurantName = "\n      말 \n랑  \n";
            List<RestaurantSearchQueryResponse> expected = seed.stream()
                    .filter(it -> isSameCategory(category, it) && isContainsRestaurantName(restaurantName, it))
                    .toList();

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, category, restaurantName),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.getContent())
                            .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::distance))
                            .usingRecursiveComparison()
                            .ignoringFields("distance")
                            .ignoringCollectionOrder()
                            .isEqualTo(expected)
            );
        }

        @Test
        void 셀럽과_카테고리와_음식점_이름으로_조회_테스트() {
            // given
            Long celebId = 2L;
            String category = "superCategory:로이스1호점";
            String restaurantName = "로 이스";
            List<RestaurantSearchQueryResponse> expected = seed.stream()
                    .filter(it -> isCelebVisited(celebId, it)
                            && isSameCategory(category, it)
                            && isContainsRestaurantName(restaurantName, it))
                    .toList();

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(celebId, category, restaurantName),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.getContent())
                            .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::distance))
                            .usingRecursiveComparison()
                            .ignoringFields("distance")
                            .ignoringCollectionOrder()
                            .isEqualTo(expected)
            );
        }

        @Test
        void 셀럽과_거리_기준으로_음식점_조회_테스트() {
            // given
            Long celebId = 1L;
            LocationSearchCond locationSearchCond = 박스_1번_지점포함;
            List<RestaurantSearchQueryResponse> expected = seed.stream()
                    .filter(it -> isCelebVisited(celebId, it) && isRestaurantInArea(locationSearchCond, it))
                    .toList();

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(celebId, null, null),
                    new LocationSearchCond(
                            locationSearchCond.lowLatitude(),
                            locationSearchCond.highLatitude(),
                            locationSearchCond.lowLongitude(),
                            locationSearchCond.highLongitude()
                    ),
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.getContent())
                            .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::distance))
                            .usingRecursiveComparison()
                            .ignoringFields("distance")
                            .ignoringCollectionOrder()
                            .isEqualTo(expected)
            );
        }

        @Test
        void 셀럽과_음식점이름과_거리를_기준으로_음식점_조회_테스트() {
            // given
            Long celebId = 1L;
            String restaurantName = "로이스";
            LocationSearchCond locationSearchCond = 박스_1_2번_지점포함;
            List<RestaurantSearchQueryResponse> expected = seed.stream()
                    .filter(it -> isCelebVisited(celebId, it)
                            && isContainsRestaurantName(restaurantName, it)
                            && isRestaurantInArea(locationSearchCond, it))
                    .toList();

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(celebId, null, restaurantName),
                    new LocationSearchCond(
                            locationSearchCond.lowLatitude(),
                            locationSearchCond.highLatitude(),
                            locationSearchCond.lowLongitude(),
                            locationSearchCond.highLongitude()
                    ),
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.getContent())
                            .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::distance))
                            .usingRecursiveComparison()
                            .ignoringFields("distance")
                            .ignoringCollectionOrder()
                            .isEqualTo(expected)
            );
        }

        @Test
        void 로그인_상태에서_음식점을_조회하면_좋아요한_음식점의_좋아요_여부에_참값이_반환한다() {
            // given
            OauthMember 오도 = oauthMemberRepository.save(멤버("오도"));
            Restaurant restaurant1 = restaurantRepository.getById(seed.get(0).id());
            Restaurant restaurant2 = restaurantRepository.getById(seed.get(2).id());
            Restaurant restaurant3 = restaurantRepository.getById(seed.get(4).id());
            Restaurant restaurant4 = restaurantRepository.getById(seed.get(9).id());
            restaurantLikeRepository.saveAll(List.of(
                    RestaurantLike.create(restaurant1, 오도),
                    RestaurantLike.create(restaurant2, 오도),
                    RestaurantLike.create(restaurant3, 오도),
                    RestaurantLike.create(restaurant4, 오도)
            ));

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, null, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    오도.id());

            // then
            for (RestaurantSearchQueryResponse response : result.getContent()) {
                Restaurant restaurant = restaurantRepository.getById(response.id());
                Optional<RestaurantLike> foundRestaurantLike
                        = restaurantLikeRepository.findByRestaurantAndMember(restaurant, 오도);
                foundRestaurantLike.ifPresentOrElse(
                        it -> assertThat(response.isLiked()).isTrue(),
                        () -> assertThat(response.isLiked()).isFalse()
                );
            }
        }
    }

    @Nested
    class 음식점을_기준으로_일정_거리_내의_모든_음식점_조회 {

        @ParameterizedTest
        @ValueSource(ints = {10, 50, 100, 500, 1000, 2000, 3000, 5000, 30000})
        void 특정_음식점을_기준으로_일정_거리_내에_있는_모든_음식점_조회_테스트(int specificDistance) {
            // given
            List<RestaurantSearchQueryResponse> expected = seed.stream()
                    .filter(it -> it.distance() <= specificDistance)
                    .toList();

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllNearByDistanceWithoutSpecificRestaurant(
                    1L,
                    specificDistance,
                    PageRequest.of(0, 4),
                    null
            );

            // then
            assertThat(result.getContent())
                    .usingRecursiveComparison()
                    .comparingOnlyFields("id")
                    .ignoringCollectionOrder()
                    .isEqualTo(expected);
        }

        @Test
        void 좋아요_여부가_포함된다() {
            // given
            OauthMember 도기 = oauthMemberRepository.save(멤버("도기"));
            for (RestaurantSearchQueryResponse RestaurantSearchQueryResponse : seed) {
                Restaurant restaurant = restaurantRepository.getById(RestaurantSearchQueryResponse.id());
                restaurantLikeRepository.save(RestaurantLike.create(restaurant, 도기));
            }

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllNearByDistanceWithoutSpecificRestaurant(
                    seed.get(0).id(),
                    50000,
                    PageRequest.of(0, 4),
                    도기.id()
            );

            // then
            assertThat(result.getContent())
                    .extracting(RestaurantSearchQueryResponse::isLiked)
                    .allMatch(isLiked -> isLiked);
        }
    }

    @Nested
    class 위시리스트_조회 {

        @Test
        void 멤버_아이디로_음식점_좋아요를_검색한다() {
            // given
            OauthMember 오도 = oauthMemberRepository.save(멤버("오도"));
            Restaurant restaurant1 = restaurantRepository.getById(seed.get(0).id());
            Restaurant restaurant2 = restaurantRepository.getById(seed.get(2).id());
            Restaurant restaurant3 = restaurantRepository.getById(seed.get(4).id());
            Restaurant restaurant4 = restaurantRepository.getById(seed.get(9).id());
            restaurantLikeRepository.saveAll(List.of(
                    RestaurantLike.create(restaurant1, 오도),
                    RestaurantLike.create(restaurant2, 오도),
                    RestaurantLike.create(restaurant3, 오도),
                    RestaurantLike.create(restaurant4, 오도)
            ));

            // when
            List<LikedRestaurantQueryResponse> result =
                    restaurantQueryService.findAllLikedRestaurantByMemberId(오도.id());

            // then
            List<LikedRestaurantQueryResponse> expected = new ArrayList<>(List.of(
                    toRestaurantLikeQueryResponse(seed.get(0)),
                    toRestaurantLikeQueryResponse(seed.get(2)),
                    toRestaurantLikeQueryResponse(seed.get(4)),
                    toRestaurantLikeQueryResponse(seed.get(9))
            ));
            assertThat(result)
                    .usingRecursiveComparison()
                    .ignoringCollectionOrder()
                    .isEqualTo(expected);
        }

        private LikedRestaurantQueryResponse toRestaurantLikeQueryResponse(
                RestaurantSearchQueryResponse RestaurantSearchQueryResponse) {
            return new LikedRestaurantQueryResponse(
                    RestaurantSearchQueryResponse.id(),
                    RestaurantSearchQueryResponse.name(),
                    RestaurantSearchQueryResponse.category(),
                    RestaurantSearchQueryResponse.roadAddress(),
                    RestaurantSearchQueryResponse.latitude(),
                    RestaurantSearchQueryResponse.longitude(),
                    RestaurantSearchQueryResponse.phoneNumber(),
                    RestaurantSearchQueryResponse.naverMapUrl(),
                    RestaurantSearchQueryResponse.celebs(),
                    RestaurantSearchQueryResponse.images()
            );
        }
    }

    @Nested
    class 전체_조회시_좋아요의_수와_여부_테스트 {

        @BeforeEach
        void setUp() {
            restaurantLikeRepository.deleteAll();
            videoRepository.deleteAll();
            restaurantImageRepository.deleteAll();
            restaurantRepository.deleteAll();
            celebRepository.deleteAll();
            oauthMemberRepository.deleteAll();
        }

        @Test
        void 회원이_좋아요_누른_음식점을_조회하면_좋아요한_음식점의_여부에_참이_반환되고_모두의_좋아요수가_함께_반환된다() {
            // given
            Celeb 셀럽 = celebRepository.save(셀럽("셀럽"));
            Restaurant 말랑이_좋아요한_음식점 = 음식점("말랑이 좋아요한 음식점");
            Restaurant 말랑과_로이스가_좋아요한_음식점 = 음식점("말랑과 로이스가 좋아요한 음식점");
            Restaurant 로이스가_좋아요한_음식점 = 음식점("로이스가 좋아요한 음식점");
            Restaurant 아무도_좋아하지_않는_음식점 = 음식점("아무도 좋아하지 않는 음식점");
            restaurantRepository.saveAll(List.of(말랑이_좋아요한_음식점, 말랑과_로이스가_좋아요한_음식점,
                    로이스가_좋아요한_음식점, 아무도_좋아하지_않는_음식점));
            videoRepository.saveAll(List.of(
                    영상("url", 말랑이_좋아요한_음식점, 셀럽),
                    영상("url", 말랑과_로이스가_좋아요한_음식점, 셀럽),
                    영상("url", 로이스가_좋아요한_음식점, 셀럽),
                    영상("url", 아무도_좋아하지_않는_음식점, 셀럽)
            ));

            OauthMember 말랑 = 멤버("말랑");
            OauthMember 로이스 = 멤버("로이스");
            oauthMemberRepository.saveAll(List.of(말랑, 로이스));
            likeService.like(말랑이_좋아요한_음식점.id(), 말랑.id());
            likeService.like(말랑과_로이스가_좋아요한_음식점.id(), 말랑.id());
            likeService.like(말랑과_로이스가_좋아요한_음식점.id(), 로이스.id());
            likeService.like(로이스가_좋아요한_음식점.id(), 로이스.id());

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, null, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    말랑.id()
            );

            // then
            List<RestaurantSearchQueryResponse> 예상 = List.of(
                    toRestaurantSearchResponse(말랑이_좋아요한_음식점, true, 1, List.of(셀럽), Collections.emptyList()),
                    toRestaurantSearchResponse(말랑과_로이스가_좋아요한_음식점, true, 2, List.of(셀럽), Collections.emptyList()),
                    toRestaurantSearchResponse(로이스가_좋아요한_음식점, false, 1, List.of(셀럽), Collections.emptyList()),
                    toRestaurantSearchResponse(아무도_좋아하지_않는_음식점, false, 0, List.of(셀럽), Collections.emptyList())
            );
            assertThat(result.getContent()).usingRecursiveComparison()
                    .ignoringExpectedNullFields()
                    .isEqualTo(예상);
        }

        private static RestaurantSearchQueryResponse toRestaurantSearchResponse(
                Restaurant restaurant,
                boolean isLiked,
                int likeCount,
                List<Celeb> celebs,
                List<RestaurantImage> images
        ) {
            return new RestaurantSearchQueryResponse(
                    restaurant.id(),
                    restaurant.name(),
                    restaurant.category(),
                    restaurant.superCategory(),
                    restaurant.roadAddress(),
                    restaurant.latitude(),
                    restaurant.longitude(),
                    restaurant.phoneNumber(),
                    restaurant.naverMapUrl(),
                    restaurant.viewCount(),
                    null,
                    likeCount,
                    isLiked,
                    restaurant.totalRating(),
                    celebs.stream().map(it -> new CelebQueryResponse(
                            restaurant.id(),
                            it.id(),
                            it.name(),
                            it.youtubeChannelName(),
                            it.profileImageUrl())
                    ).toList(),
                    images.stream().map(it -> new RestaurantImageQueryResponse(
                            restaurant.id(),
                            it.id(),
                            it.name(),
                            it.author(),
                            it.socialMedia().name())
                    ).toList()
            );
        }
    }

    @Nested
    class 조회수_테스트 {

        private RestaurantSearchQueryResponse restaurantSearchQueryResponse1;
        private RestaurantSearchQueryResponse restaurantSearchQueryResponse2;
        private RestaurantSearchQueryResponse restaurantSearchQueryResponse3;
        private RestaurantSearchQueryResponse restaurantSearchQueryResponse4;

        @BeforeEach
        void setUp() {
            restaurantSearchQueryResponse1 = seed.get(0);
            restaurantSearchQueryResponse2 = seed.get(2);
            restaurantSearchQueryResponse3 = seed.get(4);
            restaurantSearchQueryResponse4 = seed.get(9);
        }

        @Test
        void 음식점을_조회하면_조회수를_함께_반환한다() {
            // given
            음식점들의_조회수를_높인다();
            seed.set(0, increaseViewCount(restaurantSearchQueryResponse1, 4));
            seed.set(2, increaseViewCount(restaurantSearchQueryResponse2, 2));
            seed.set(4, increaseViewCount(restaurantSearchQueryResponse3, 1));
            seed.set(9, increaseViewCount(restaurantSearchQueryResponse4, 5));

            // when
            Page<RestaurantSearchQueryResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, null, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.getContent())
                            .isSortedAccordingTo(comparing(RestaurantSearchQueryResponse::distance))
                            .usingRecursiveComparison()
                            .ignoringFields("distance")
                            .ignoringCollectionOrder()
                            .isEqualTo(seed)
            );
        }

        private void 음식점들의_조회수를_높인다() {
            restaurantService.increaseViewCount(restaurantSearchQueryResponse1.id());
            restaurantService.increaseViewCount(restaurantSearchQueryResponse1.id());
            restaurantService.increaseViewCount(restaurantSearchQueryResponse1.id());
            restaurantService.increaseViewCount(restaurantSearchQueryResponse1.id());

            restaurantService.increaseViewCount(restaurantSearchQueryResponse2.id());
            restaurantService.increaseViewCount(restaurantSearchQueryResponse2.id());

            restaurantService.increaseViewCount(restaurantSearchQueryResponse3.id());

            restaurantService.increaseViewCount(restaurantSearchQueryResponse4.id());
            restaurantService.increaseViewCount(restaurantSearchQueryResponse4.id());
            restaurantService.increaseViewCount(restaurantSearchQueryResponse4.id());
            restaurantService.increaseViewCount(restaurantSearchQueryResponse4.id());
            restaurantService.increaseViewCount(restaurantSearchQueryResponse4.id());
        }

        private RestaurantSearchQueryResponse increaseViewCount(
                RestaurantSearchQueryResponse restaurantWithCelebsAndImagesSimpleResponse, int value) {
            return new RestaurantSearchQueryResponse(
                    restaurantWithCelebsAndImagesSimpleResponse.id(),
                    restaurantWithCelebsAndImagesSimpleResponse.name(),
                    restaurantWithCelebsAndImagesSimpleResponse.category(),
                    restaurantWithCelebsAndImagesSimpleResponse.superCategory(),
                    restaurantWithCelebsAndImagesSimpleResponse.roadAddress(),
                    restaurantWithCelebsAndImagesSimpleResponse.latitude(),
                    restaurantWithCelebsAndImagesSimpleResponse.longitude(),
                    restaurantWithCelebsAndImagesSimpleResponse.phoneNumber(),
                    restaurantWithCelebsAndImagesSimpleResponse.naverMapUrl(),
                    restaurantWithCelebsAndImagesSimpleResponse.viewCount() + value,
                    restaurantWithCelebsAndImagesSimpleResponse.distance(),
                    restaurantWithCelebsAndImagesSimpleResponse.likeCount(),
                    restaurantWithCelebsAndImagesSimpleResponse.isLiked(),
                    restaurantWithCelebsAndImagesSimpleResponse.rating(),
                    restaurantWithCelebsAndImagesSimpleResponse.celebs(),
                    restaurantWithCelebsAndImagesSimpleResponse.images()
            );
        }
    }
}
