package com.celuveat.restaurant.query;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.celeb.fixture.CelebFixture.셀럽;
import static com.celuveat.restaurant.fixture.LocationFixture.isRestaurantInArea;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1_2번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.전체영역_검색_범위;
import static com.celuveat.restaurant.fixture.RestaurantFixture.isCelebVisited;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.음식점사진;
import static com.celuveat.restaurant.fixture.RestaurantLikeFixture.음식점_좋아요;
import static com.celuveat.video.fixture.VideoFixture.영상;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.celeb.command.domain.CelebRepository;
import com.celuveat.common.IntegrationTest;
import com.celuveat.common.SeedData;
import com.celuveat.common.util.StringUtil;
import com.celuveat.restaurant.command.application.RestaurantLikeService;
import com.celuveat.restaurant.command.application.RestaurantService;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantImageRepository;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.command.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.query.dto.RestaurantImageQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchResponse;
import com.celuveat.video.command.domain.VideoRepository;
import com.celuveat.video.fixture.VideoFixture;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@IntegrationTest
@DisplayName("음식점 조회용 서비스(RestaurantQueryService) 은(는)")
class RestaurantQueryServiceTest {

    private final List<RestaurantSearchResponse> seed = new ArrayList<>();

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

    @Autowired
    private RestaurantLikeService likeService;

    @Autowired
    private CelebRepository celebRepository;

    @Autowired
    private RestaurantImageRepository restaurantImageRepository;

    @Autowired
    private VideoRepository videoRepository;

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
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(null, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(seed);
    }

    @Test
    void 셀럽으로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        for (RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (isCelebVisited(celebId, restaurantWithCelebsAndImagesSimpleResponse)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(celebId, null, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 카테고리로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        String category = "category:오도1호점";
        String superCategory = "superCategory:오도1호점";
        for (RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (restaurantWithCelebsAndImagesSimpleResponse.category().equals(category)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(null, superCategory, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 음식점_이름_포함으로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        String restaurantName = " 말 랑  \n";
        for (RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (restaurantWithCelebsAndImagesSimpleResponse.getName()
                    .contains(StringUtil.removeAllBlank(restaurantName))) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(null, null, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_카테고리로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        String superCategory = "superCategory:오도1호점";
        for (RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            List<Long> list = restaurantWithCelebsAndImagesSimpleResponse.celebs().stream().map(CelebQueryResponse::id)
                    .toList();
            if (list.contains(celebId) && restaurantWithCelebsAndImagesSimpleResponse.getSuperCategory()
                    .equals(superCategory)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(celebId, superCategory, null),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            List<Long> list = restaurantWithCelebsAndImagesSimpleResponse.getCelebs().stream()
                    .map(CelebQueryResponse::id)
                    .toList();
            if (restaurantWithCelebsAndImagesSimpleResponse.getName()
                    .contains(StringUtil.removeAllBlank(restaurantName))
                    && list.contains(celebId)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(celebId, null, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 카테고리와_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        String category = "category:말랑2호점";
        String superCategory = "superCategory:말랑2호점";
        String restaurantName = "\n      말 \n랑  \n";
        for (RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (restaurantWithCelebsAndImagesSimpleResponse.getName()
                    .contains(StringUtil.removeAllBlank(restaurantName))
                    && restaurantWithCelebsAndImagesSimpleResponse.getCategory().equals(category)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(null, superCategory, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_카테고리와_음식점_이름으로_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        Long celebId = 2L;
        String category = "category:로이스1호점";
        String superCategory = "superCategory:로이스1호점";
        String restaurantName = "로 이스";
        for (RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (restaurantWithCelebsAndImagesSimpleResponse.getName()
                    .contains(StringUtil.removeAllBlank(restaurantName))
                    && restaurantWithCelebsAndImagesSimpleResponse.getCategory().equals(category)
                    && isCelebVisited(celebId, restaurantWithCelebsAndImagesSimpleResponse)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
                new RestaurantSearchCond(celebId, superCategory, restaurantName),
                전체영역_검색_범위,
                PageRequest.of(0, 100),
                null
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_거리_기준으로_음식점_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        for (RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (isRestaurantInArea(박스_1번_지점포함, restaurantWithCelebsAndImagesSimpleResponse)
                    && isCelebVisited(celebId, restaurantWithCelebsAndImagesSimpleResponse)) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
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
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    void 셀럽과_음식점이름과_거리를_기준으로_음식점_조회_테스트() {
        // given
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        Long celebId = 1L;
        String restaurantName = "로이스";
        for (RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse : seed) {
            if (isRestaurantInArea(박스_1_2번_지점포함, restaurantWithCelebsAndImagesSimpleResponse)
                    && isCelebVisited(celebId, restaurantWithCelebsAndImagesSimpleResponse)
                    && restaurantWithCelebsAndImagesSimpleResponse.getName()
                    .contains(StringUtil.removeAllBlank(restaurantName))) {
                expected.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }

        // when
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
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
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    // TODO: 믿을게 도기 난 진짜 이거 바꿀 자신이 없져!!! - 동훈 올림
//    @Test
//    void 로그인_상태에서_음식점을_조회하면_좋아요한_음식점의_좋아요_여부에_참값이_반환한다() {
//        OauthMember 오도 = 멤버("오도");
//        oauthMemberRepository.save(오도);
//        RestaurantSearchResponse restaurantSimpleResponse1 = seed.get(0);
//        RestaurantSearchResponse restaurantSimpleResponse2 = seed.get(2);
//        RestaurantSearchResponse restaurantSimpleResponse3 = seed.get(4);
//        RestaurantSearchResponse restaurantSimpleResponse4 = seed.get(9);
//        Restaurant 말랑1호점 = restaurantRepository.getById(restaurantSimpleResponse1.id());
//        Restaurant 말랑3호점 = restaurantRepository.getById(restaurantSimpleResponse2.id());
//        Restaurant 도기2호점 = restaurantRepository.getById(restaurantSimpleResponse3.id());
//        Restaurant 로이스2호점 = restaurantRepository.getById(restaurantSimpleResponse4.id());
//        restaurantLikeRepository.saveAll(List.of(
//                음식점_좋아요(말랑1호점, 오도),
//                음식점_좋아요(말랑3호점, 오도),
//                음식점_좋아요(도기2호점, 오도),
//                음식점_좋아요(로이스2호점, 오도)
//        ));
//
//        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
//                new RestaurantSearchCond(null, null, null),
//                전체영역_검색_범위,
//                PageRequest.of(0, 100),
//                오도.id());
//
//        assertThat(result).isNotEmpty();
//        assertThat(result.getContent())
//                .isSortedAccordingTo(comparing(RestaurantSearchResponse::distance))
//                .usingRecursiveComparison()
//                .ignoringFields("distance")
//                .ignoringCollectionOrder()
//                .isEqualTo(seed);
//    }

    @Test
    void 멤버_아이디로_음식점_좋아요를_검색한다() {
        // given
        OauthMember 멤버 = 멤버("오도");
        oauthMemberRepository.save(멤버);
        RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse1 = seed.get(0);
        RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse2 = seed.get(2);
        RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse3 = seed.get(4);
        RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse4 = seed.get(9);
        Restaurant 말랑1호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse1.getId());
        Restaurant 말랑3호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse2.getId());
        Restaurant 도기2호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse3.getId());
        Restaurant 로이스2호점 = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse4.getId());
        restaurantLikeRepository.saveAll(List.of(
                음식점_좋아요(말랑1호점, 멤버),
                음식점_좋아요(말랑3호점, 멤버),
                음식점_좋아요(도기2호점, 멤버),
                음식점_좋아요(로이스2호점, 멤버)
        ));
        List<LikedRestaurantQueryResponse> expected = new ArrayList<>(List.of(
                toRestaurantLikeQueryResponse(restaurantWithCelebsAndImagesSimpleResponse4),
                toRestaurantLikeQueryResponse(restaurantWithCelebsAndImagesSimpleResponse3),
                toRestaurantLikeQueryResponse(restaurantWithCelebsAndImagesSimpleResponse2),
                toRestaurantLikeQueryResponse(restaurantWithCelebsAndImagesSimpleResponse1)
        ));

        // when
        List<LikedRestaurantQueryResponse> restaurantLikes =
                restaurantQueryService.findAllLikedRestaurantByMemberId(멤버.id());

        // then
        assertThat(restaurantLikes).usingRecursiveComparison().isEqualTo(expected);
    }

    private LikedRestaurantQueryResponse toRestaurantLikeQueryResponse(
            RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse) {
        return new LikedRestaurantQueryResponse(
                restaurantWithCelebsAndImagesSimpleResponse.getId(),
                restaurantWithCelebsAndImagesSimpleResponse.getName(),
                restaurantWithCelebsAndImagesSimpleResponse.getCategory(),
                restaurantWithCelebsAndImagesSimpleResponse.getRoadAddress(),
                restaurantWithCelebsAndImagesSimpleResponse.getLatitude(),
                restaurantWithCelebsAndImagesSimpleResponse.getLongitude(),
                restaurantWithCelebsAndImagesSimpleResponse.getPhoneNumber(),
                restaurantWithCelebsAndImagesSimpleResponse.getNaverMapUrl(),
                restaurantWithCelebsAndImagesSimpleResponse.getCelebs(),
                restaurantWithCelebsAndImagesSimpleResponse.getImages()
        );
    }

    @Test
    void 음식점_상세_조회_테스트() {
        // given
        RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse = seed.get(0);
        OauthMember oauthMember = 멤버("로이스");
        oauthMemberRepository.save(oauthMember);
        Restaurant restaurant = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse.getId());
        restaurantLikeRepository.save(new RestaurantLike(restaurant, oauthMember));

        // when
        RestaurantDetailResponse result =
                restaurantQueryService.findRestaurantDetailById(restaurant.id(), 1L, null);

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
        RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse = seed.get(0);
        OauthMember oauthMember = 멤버("로이스");
        oauthMemberRepository.save(oauthMember);
        Restaurant restaurant = restaurantRepository.getById(restaurantWithCelebsAndImagesSimpleResponse.getId());
        restaurantLikeRepository.save(new RestaurantLike(restaurant, oauthMember));

        // when
        RestaurantDetailResponse result =
                restaurantQueryService.findRestaurantDetailById(restaurant.id(), 1L, oauthMember.id());

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("likeCount", "viewCount")
                .isEqualTo(toRestaurantDetailQueryResponse(restaurantWithCelebsAndImagesSimpleResponse, true));
        assertThat(result.likeCount()).isEqualTo(1);
    }

    private RestaurantDetailResponse toRestaurantDetailQueryResponse(
            RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse,
            boolean isLiked
    ) {
        return new RestaurantDetailResponse(
                restaurantWithCelebsAndImagesSimpleResponse.getId(),
                restaurantWithCelebsAndImagesSimpleResponse.getName(),
                restaurantWithCelebsAndImagesSimpleResponse.getCategory(),
                restaurantWithCelebsAndImagesSimpleResponse.getSuperCategory(),
                restaurantWithCelebsAndImagesSimpleResponse.getRoadAddress(),
                restaurantWithCelebsAndImagesSimpleResponse.getLatitude(),
                restaurantWithCelebsAndImagesSimpleResponse.getLongitude(),
                restaurantWithCelebsAndImagesSimpleResponse.getPhoneNumber(),
                restaurantWithCelebsAndImagesSimpleResponse.getNaverMapUrl(),
                0, // likeCount
                0, // viewCount
                isLiked,
                restaurantWithCelebsAndImagesSimpleResponse.getRating(),
                restaurantWithCelebsAndImagesSimpleResponse.getCelebs(),
                restaurantWithCelebsAndImagesSimpleResponse.getImages()
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 50, 100, 500, 1000, 2000, 3000, 5000, 30000})
    void 특정_음식점을_기준으로_일정_거리_내에_있는_모든_음식점_조회_테스트(int specificDistance) {
        // given
        RestaurantSearchResponse restaurant = seed.get(0);

        // when
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllNearByDistanceWithoutSpecificRestaurant(
                restaurant.getId(),
                specificDistance,
                PageRequest.of(0, 4),
                null
        );

        // then
        assertThat(result.getContent())
                .extracting(RestaurantSearchResponse::getDistance)
                .allMatch(distance -> distance <= specificDistance);
        assertThat(result.getContent())
                .extracting(RestaurantSearchResponse::getName)
                .doesNotContain(restaurant.getName());
    }

    @Test
    void 주변_음식점을_조회할_때_좋아요_여부가_포함된다() {
        // given
        OauthMember 도기 = oauthMemberRepository.save(멤버("도기"));
        oauthMemberRepository.save(도기);
        for (RestaurantSearchResponse restaurantSearchResponse : seed) {
            Restaurant restaurant = restaurantRepository.getById(restaurantSearchResponse.getId());
            restaurantLikeRepository.save(음식점_좋아요(restaurant, 도기));
        }

        // when
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllNearByDistanceWithoutSpecificRestaurant(
                seed.get(0).getId(),
                50000,
                PageRequest.of(0, 4),
                도기.id()
        );

        // then
        List<RestaurantSearchResponse> content = result.getContent();
        assertThat(content).extracting(RestaurantSearchResponse::isLiked)
                .allMatch(isLiked -> isLiked);
    }

    @Test
    void 셀럽ID로_음식점_상세_조회_정렬_테스트() {
        // given
        Restaurant 로이스2호점 = restaurantRepository.save(음식점("로이스2호점"));
        List<Celeb> celebs = celebRepository.saveAll(List.of(셀럽("로이스2"), 셀럽("말랑2")));
        restaurantImageRepository.saveAll(List.of(
                        음식점사진("이미지1", 로이스2호점, "로이스2"),
                        음식점사진("이미지2", 로이스2호점, "말랑2")
                )
        );
        videoRepository.saveAll(List.of(
                        영상("youtube1.com", 로이스2호점, celebs.get(0)),
                        영상("youtube2.com", 로이스2호점, celebs.get(1))
                )
        );
        Celeb targetCeleb = celebs.get(1);

        // when
        RestaurantDetailResponse result =
                restaurantQueryService.findRestaurantDetailById(로이스2호점.id(), targetCeleb.id(), null);

        // then
        assertThat(result.celebs().get(0))
                .usingRecursiveComparison()
                .isEqualTo(CelebQueryResponse.from(로이스2호점.id(), targetCeleb));
        assertThat(result.images().get(0).author())
                .isEqualTo(targetCeleb.name());
    }

    @Nested
    class 좋아요수_테스트 {

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
        void 말랑으로_음식점을_조회하면_말랑이_좋아요한_음식점의_좋아요여부에_참이_반환되고_모두의_좋아요수가_함께_반환된다() {
            // given
            Celeb 셀럽 = celebRepository.save(셀럽("셀럽"));

            Restaurant 말랑이_좋아요한_음식점 = 음식점("말랑이 좋아요한 음식점");
            Restaurant 말랑과_로이스가_좋아요한_음식점 = 음식점("말랑과 로이스가 좋아요한 음식점");
            Restaurant 로이스가_좋아요한_음식점 = 음식점("로이스가 좋아요한 음식점");
            Restaurant 아무도_좋아하지_않는_음식점 = 음식점("아무도 좋아하지 않는 음식점");
            restaurantRepository.saveAll(List.of(말랑이_좋아요한_음식점, 말랑과_로이스가_좋아요한_음식점,
                    로이스가_좋아요한_음식점, 아무도_좋아하지_않는_음식점));

            videoRepository.saveAll(List.of(
                    VideoFixture.영상("url", 말랑이_좋아요한_음식점, 셀럽),
                    VideoFixture.영상("url", 말랑과_로이스가_좋아요한_음식점, 셀럽),
                    VideoFixture.영상("url", 로이스가_좋아요한_음식점, 셀럽),
                    VideoFixture.영상("url", 아무도_좋아하지_않는_음식점, 셀럽)
            ));

            OauthMember 말랑 = 멤버("말랑");
            OauthMember 로이스 = 멤버("로이스");
            oauthMemberRepository.saveAll(List.of(말랑, 로이스));

            likeService.like(말랑이_좋아요한_음식점.id(), 말랑.id());
            likeService.like(말랑과_로이스가_좋아요한_음식점.id(), 말랑.id());
            likeService.like(말랑과_로이스가_좋아요한_음식점.id(), 로이스.id());
            likeService.like(로이스가_좋아요한_음식점.id(), 로이스.id());

            // when
            Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, null, null),
                    new LocationSearchCond(0.0, 1000.0, 0.0, 1000.0),
                    PageRequest.of(0, 100),
                    말랑.id()
            );

            // then
            List<RestaurantSearchResponse> 예상 = List.of(
                    toRestaurantSimpleResponse(말랑이_좋아요한_음식점, true, 1, List.of(셀럽), Collections.emptyList()),
                    toRestaurantSimpleResponse(말랑과_로이스가_좋아요한_음식점, true, 2, List.of(셀럽), Collections.emptyList()),
                    toRestaurantSimpleResponse(로이스가_좋아요한_음식점, false, 1, List.of(셀럽), Collections.emptyList()),
                    toRestaurantSimpleResponse(아무도_좋아하지_않는_음식점, false, 0, List.of(셀럽), Collections.emptyList())
            );
            assertThat(result.getContent()).usingRecursiveComparison()
                    .ignoringExpectedNullFields()
                    .isEqualTo(예상);
        }

        private static RestaurantSearchResponse toRestaurantSimpleResponse(
                Restaurant restaurant,
                boolean isLiked,
                int likeCount,
                List<Celeb> celebs,
                List<RestaurantImage> images
        ) {
            return new RestaurantSearchResponse(
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
                    restaurant.averageRating(),
                    celebs.stream().map(it -> new CelebQueryResponse(
                            restaurant.id(),
                            it.id(),
                            it.name(),
                            it.youtubeChannelName(),
                            it.profileImageUrl())
                    ).toList(),
                    images.stream().map(it -> new RestaurantImageQueryResponse(
                            it.restaurant().id(),
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

        private RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse1;
        private RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse2;
        private RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse3;
        private RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse4;

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
            Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
                    new RestaurantSearchCond(null, null, null),
                    전체영역_검색_범위,
                    PageRequest.of(0, 100),
                    null
            );

            // then
            assertThat(result).isNotEmpty();
            assertThat(result.getContent())
                    .isSortedAccordingTo(comparing(RestaurantSearchResponse::getDistance))
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .ignoringCollectionOrder()
                    .isEqualTo(seed);
        }

        private void 음식점들의_조회수를_높인다() {
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse1.getId());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse1.getId());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse1.getId());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse1.getId());

            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse2.getId());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse2.getId());

            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse3.getId());

            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse4.getId());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse4.getId());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse4.getId());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse4.getId());
            restaurantService.increaseViewCount(restaurantWithCelebsAndImagesSimpleResponse4.getId());
        }

        private RestaurantSearchResponse increaseViewCount(
                RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse, int value) {
            return new RestaurantSearchResponse(
                    restaurantWithCelebsAndImagesSimpleResponse.getId(),
                    restaurantWithCelebsAndImagesSimpleResponse.getName(),
                    restaurantWithCelebsAndImagesSimpleResponse.getCategory(),
                    restaurantWithCelebsAndImagesSimpleResponse.getSuperCategory(),
                    restaurantWithCelebsAndImagesSimpleResponse.getRoadAddress(),
                    restaurantWithCelebsAndImagesSimpleResponse.getLatitude(),
                    restaurantWithCelebsAndImagesSimpleResponse.getLongitude(),
                    restaurantWithCelebsAndImagesSimpleResponse.getPhoneNumber(),
                    restaurantWithCelebsAndImagesSimpleResponse.getNaverMapUrl(),
                    restaurantWithCelebsAndImagesSimpleResponse.getViewCount() + value,
                    restaurantWithCelebsAndImagesSimpleResponse.getDistance(),
                    restaurantWithCelebsAndImagesSimpleResponse.getLikeCount(),
                    restaurantWithCelebsAndImagesSimpleResponse.isLiked(),
                    restaurantWithCelebsAndImagesSimpleResponse.getRating(),
                    restaurantWithCelebsAndImagesSimpleResponse.getCelebs(),
                    restaurantWithCelebsAndImagesSimpleResponse.getImages()
            );
        }
    }
}
