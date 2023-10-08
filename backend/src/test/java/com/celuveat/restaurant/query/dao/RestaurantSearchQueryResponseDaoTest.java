package com.celuveat.restaurant.query.dao;

import static com.celuveat.auth.fixture.OauthMemberFixture.도기;
import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.celeb.fixture.CelebFixture.맛객리우;
import static com.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celeb.fixture.CelebFixture.회사랑;
import static com.celuveat.restaurant.fixture.RestaurantFixture.RestaurantPointFixture.대성집만_포함한_검색_영역;
import static com.celuveat.restaurant.fixture.RestaurantFixture.RestaurantPointFixture.대한민국_전체를_포함한_검색_영역;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static com.celuveat.restaurant.fixture.RestaurantFixture.모던샤브하우스;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantFixture.하늘초밥;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.대성집_사진;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.모던샤브하우스_사진;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.하늘초밥_사진;
import static com.celuveat.video.fixture.VideoFixture.맛객리우의_모던샤브하우스_영상;
import static com.celuveat.video.fixture.VideoFixture.성시경의_대성집_영상;
import static com.celuveat.video.fixture.VideoFixture.영상;
import static com.celuveat.video.fixture.VideoFixture.회사랑의_하늘초밥_영상;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.common.DaoTest;
import com.celuveat.common.TestData;
import com.celuveat.common.util.Base64Util;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantImageQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchQueryResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;

@DisplayName("음식점 검색 DAO(RestaurantSearchQueryResponseDao) 은(는)")
class RestaurantSearchQueryResponseDaoTest extends DaoTest {

    @Autowired
    private RestaurantSearchQueryResponseDao restaurantSearchQueryResponseDao;

    @Override
    protected TestData prepareTestData() {
        return testData; // ignored
    }

    @Nested
    class 음식점_검색_시 extends DaoTest {

        private final Restaurant 대성집 = 대성집();
        private final Restaurant 하늘초밥 = 하늘초밥();
        private final Restaurant 모던샤브하우스 = 모던샤브하우스();
        private final Celeb 성시경 = 성시경();
        private final Celeb 회사랑 = 회사랑();
        private final Celeb 맛객리우 = 맛객리우();
        private final OauthMember 말랑 = 말랑();
        private final OauthMember 도기 = 도기();
        private final RestaurantImage 대성집_사진_1 = 대성집_사진(대성집, 1);
        private final RestaurantImage 대성집_사진_2 = 대성집_사진(대성집, 2);
        private final RestaurantImage 하늘초밥_사진_1 = 하늘초밥_사진(하늘초밥, 1);
        private final RestaurantImage 모던샤브하우스_사진_1 = 모던샤브하우스_사진(모던샤브하우스, 1);

        @Override
        protected TestData prepareTestData() {
            testData.addMembers(말랑, 도기);
            testData.addCelebs(성시경, 회사랑, 맛객리우);
            testData.addRestaurants(대성집, 하늘초밥, 모던샤브하우스);
            testData.addRestaurantImages(
                    대성집_사진_1,
                    대성집_사진_2,
                    하늘초밥_사진_1,
                    모던샤브하우스_사진_1
            );
            testData.addVideos(
                    성시경의_대성집_영상(성시경, 대성집),
                    회사랑의_하늘초밥_영상(회사랑, 하늘초밥),
                    맛객리우의_모던샤브하우스_영상(맛객리우, 모던샤브하우스)
            );
            testData.addRestaurantLikes(
                    RestaurantLike.create(대성집, 말랑)
            );
            testData.addRestaurantReviews(
                    RestaurantReview.create(대성집, 말랑, "맛나요", 4.6),
                    RestaurantReview.create(대성집, 도기, "그냥 그래요", 4.35)
            );
            return testData;
        }

        @Test
        void 위경도에_포함된_음식점을_조회한다() {
            // when
            List<RestaurantSearchQueryResponse> 대성집만_포함한_검색_영역으로_조회한_결과 = restaurantSearchQueryResponseDao.find(
                    new RestaurantSearchCond(null, null, null),
                    대성집만_포함한_검색_영역(),
                    PageRequest.of(0, 18),
                    null
            ).getContent();
            List<RestaurantSearchQueryResponse> 대한민국_전체_영역으로_조회한_결과 = restaurantSearchQueryResponseDao.find(
                    new RestaurantSearchCond(null, null, null),
                    대한민국_전체를_포함한_검색_영역(),
                    PageRequest.of(0, 18),
                    null
            ).getContent();

            // then
            assertThat(대성집만_포함한_검색_영역으로_조회한_결과)
                    .hasSize(1)
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .isEqualTo(List.of(
                            restaurantSearchQueryResponse(
                                    대성집,
                                    1,  // 좋아요 수
                                    false,  // 좋아요 눌렀는지 여부
                                    4.5,  // 평점: ((4.7 + 3.22) / 2) 를 소숫점 2째 자리에서 반올림
                                    List.of(성시경),
                                    List.of(대성집_사진_1, 대성집_사진_2)
                            )
                    ));
            assertThat(대한민국_전체_영역으로_조회한_결과)
                    .hasSize(3)
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .isEqualTo(List.of(
                            restaurantSearchQueryResponse(
                                    하늘초밥,
                                    0, false, 0,
                                    List.of(회사랑),
                                    List.of(하늘초밥_사진_1)
                            ),
                            restaurantSearchQueryResponse(
                                    모던샤브하우스,
                                    0, false, 0,
                                    List.of(맛객리우),
                                    List.of(모던샤브하우스_사진_1)
                            ),
                            restaurantSearchQueryResponse(
                                    대성집,
                                    1, false, 4.5,
                                    List.of(성시경),
                                    List.of(대성집_사진_1, 대성집_사진_2)
                            )
                    ));
        }

        @Test
        void 좋아요를_눌렀는지_여부도_반환된다() {
            // when
            List<RestaurantSearchQueryResponse> 성시경_ID로_검색한_결과 = restaurantSearchQueryResponseDao.find(
                    new RestaurantSearchCond(null, null, null),
                    대한민국_전체를_포함한_검색_영역(),
                    PageRequest.of(0, 18),
                    말랑.id()
            ).getContent();

            // then
            assertThat(성시경_ID로_검색한_결과)
                    .hasSize(3)
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .isEqualTo(List.of(
                            restaurantSearchQueryResponse(
                                    하늘초밥,
                                    0, false, 0,
                                    List.of(회사랑),
                                    List.of(하늘초밥_사진_1)
                            ),
                            restaurantSearchQueryResponse(
                                    모던샤브하우스,
                                    0, false, 0,
                                    List.of(맛객리우),
                                    List.of(모던샤브하우스_사진_1)
                            ),
                            restaurantSearchQueryResponse(
                                    대성집,
                                    1, true, 4.5,
                                    List.of(성시경),
                                    List.of(대성집_사진_1, 대성집_사진_2)
                            )
                    ));
        }

        @Test
        void 특정_셀럽의_ID로_음식점들을_조회한다() {
            // when
            List<RestaurantSearchQueryResponse> 성시경_ID로_검색한_결과 = restaurantSearchQueryResponseDao.find(
                    new RestaurantSearchCond(성시경.id(), null, null),
                    대한민국_전체를_포함한_검색_영역(),
                    PageRequest.of(0, 18),
                    null
            ).getContent();

            // then
            assertThat(성시경_ID로_검색한_결과)
                    .hasSize(1)
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .isEqualTo(List.of(
                            restaurantSearchQueryResponse(
                                    대성집,
                                    1, false, 4.5,
                                    List.of(성시경),
                                    List.of(대성집_사진_1, 대성집_사진_2)
                            )
                    ));
        }

        @Test
        void 특정_카테고리로_음식점들을_조회한다() {
            // when
            List<RestaurantSearchQueryResponse> 성시경_ID로_검색한_결과 = restaurantSearchQueryResponseDao.find(
                    new RestaurantSearchCond(null, 대성집.superCategory(), null),
                    대한민국_전체를_포함한_검색_영역(),
                    PageRequest.of(0, 18),
                    null
            ).getContent();

            // then
            assertThat(성시경_ID로_검색한_결과)
                    .hasSize(1)
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .isEqualTo(List.of(
                            restaurantSearchQueryResponse(
                                    대성집,
                                    1, false, 4.5,
                                    List.of(성시경),
                                    List.of(대성집_사진_1, 대성집_사진_2)
                            )
                    ));
        }

        @Test
        void 음식점_이름으로_음식점들을_조회한다() {
            // when
            List<RestaurantSearchQueryResponse> 성시경_ID로_검색한_결과 = restaurantSearchQueryResponseDao.find(
                    new RestaurantSearchCond(null, null, "하늘초밥"),
                    대한민국_전체를_포함한_검색_영역(),
                    PageRequest.of(0, 18),
                    null
            ).getContent();

            // then
            assertThat(성시경_ID로_검색한_결과)
                    .hasSize(1)
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .isEqualTo(List.of(
                            restaurantSearchQueryResponse(
                                    하늘초밥,
                                    0, false, 0,
                                    List.of(회사랑),
                                    List.of(하늘초밥_사진_1)
                            )
                    ));
        }
    }

    @Nested
    class 근처_음식점_조회_시 extends DaoTest {

        private final Restaurant 대성집 = 대성집();

        private final Restaurant 대성집_1000m_거리_음식점 = 음식점(
                "대성집 1000m 거리 음식점",
                "한식",
                일정_거리내_위경도(대성집.latitude(), 대성집.longitude(), 1000).getFirst(),
                일정_거리내_위경도(대성집.latitude(), 대성집.longitude(), 1000).getSecond()
        );
        private final Restaurant 대성집_2000m_거리_음식점 = 음식점(
                "대성집 2000m 거리 음식점",
                "한식",
                일정_거리내_위경도(대성집.latitude(), 대성집.longitude(), 2000).getFirst(),
                일정_거리내_위경도(대성집.latitude(), 대성집.longitude(), 2000).getSecond()
        );
        private final Restaurant 대성집_3000m_거리_음식점 = 음식점(
                "대성집 3000m 거리 음식점",
                "한식",
                일정_거리내_위경도(대성집.latitude(), 대성집.longitude(), 3000).getFirst(),
                일정_거리내_위경도(대성집.latitude(), 대성집.longitude(), 3000).getSecond()
        );
        private final Celeb 성시경 = 성시경();
        private final Celeb 맛객리우 = 맛객리우();
        private final RestaurantImage 대성집_사진_1 = 대성집_사진(대성집, 1);

        private static Pair<Double, Double> 일정_거리내_위경도(double 위도, double 경도, int 거리) {
            double latitudeChange = (double) 거리 / 111320.0;
            double longitudeChange = (double) 거리 / (111320.0 * Math.cos(Math.toRadians(위도)));
            double newLatitude = 위도 + latitudeChange * 0.7;
            double newLongitude = 경도 + longitudeChange * 0.7;
            return Pair.of(newLatitude, newLongitude);
        }

        @Override
        protected TestData prepareTestData() {
            testData.addCelebs(성시경, 맛객리우);
            testData.addRestaurants(대성집, 대성집_1000m_거리_음식점, 대성집_2000m_거리_음식점, 대성집_3000m_거리_음식점);
            testData.addRestaurantImages(
                    대성집_사진_1
            );
            testData.addVideos(
                    성시경의_대성집_영상(성시경, 대성집),
                    영상(대성집_1000m_거리_음식점, 맛객리우),
                    영상(대성집_2000m_거리_음식점, 성시경),
                    영상(대성집_3000m_거리_음식점, 맛객리우)
            );
            return testData;
        }

        @Test
        void 특정_음식점에서_일정_거리_내_주변_음식점을_조회한다() {
            // when
            List<RestaurantSearchQueryResponse> 대성집_1000m_내외_음식점_조회_결과 = restaurantSearchQueryResponseDao.findNearBy(
                    대성집.id(),
                    1000, // 단위: M
                    PageRequest.of(0, 18),
                    null
            ).getContent();
            List<RestaurantSearchQueryResponse> 대성집_2000m_내외_음식점_조회_결과 = restaurantSearchQueryResponseDao.findNearBy(
                    대성집.id(),
                    2000,
                    PageRequest.of(0, 18),
                    null
            ).getContent();
            List<RestaurantSearchQueryResponse> 대성집_3000m_내외_음식점_조회_결과 = restaurantSearchQueryResponseDao.findNearBy(
                    대성집.id(),
                    3000,
                    PageRequest.of(0, 18),
                    null
            ).getContent();

            // then
            assertThat(대성집_1000m_내외_음식점_조회_결과)
                    .hasSize(1)
                    .allSatisfy(restaurant -> {
                        assertThat(restaurant.distance()).isLessThanOrEqualTo(1000);
                    })
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .isEqualTo(List.of(
                            restaurantSearchQueryResponse(
                                    대성집_1000m_거리_음식점,
                                    0, false, 0,
                                    List.of(맛객리우),
                                    List.of()
                            )
                    ));
            assertThat(대성집_2000m_내외_음식점_조회_결과)
                    .hasSize(2)
                    .allSatisfy(restaurant -> {
                        assertThat(restaurant.distance()).isLessThanOrEqualTo(2000);
                    })
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .isEqualTo(List.of(
                            restaurantSearchQueryResponse(
                                    대성집_1000m_거리_음식점,
                                    0, false, 0,
                                    List.of(맛객리우),
                                    List.of()
                            ),
                            restaurantSearchQueryResponse(
                                    대성집_2000m_거리_음식점,
                                    0, false, 0,
                                    List.of(성시경),
                                    List.of()
                            )
                    ));
            assertThat(대성집_3000m_내외_음식점_조회_결과)
                    .hasSize(3)
                    .allSatisfy(restaurant -> {
                        assertThat(restaurant.distance()).isLessThanOrEqualTo(3000);
                    })
                    .usingRecursiveComparison()
                    .ignoringFields("distance")
                    .isEqualTo(List.of(
                            restaurantSearchQueryResponse(
                                    대성집_1000m_거리_음식점,
                                    0, false, 0,
                                    List.of(맛객리우),
                                    List.of()
                            ),
                            restaurantSearchQueryResponse(
                                    대성집_2000m_거리_음식점,
                                    0, false, 0,
                                    List.of(성시경),
                                    List.of()
                            ),
                            restaurantSearchQueryResponse(
                                    대성집_3000m_거리_음식점,
                                    0, false, 0,
                                    List.of(맛객리우),
                                    List.of()
                            )
                    ));
        }
    }

    private static RestaurantSearchQueryResponse restaurantSearchQueryResponse(
            Restaurant 음식점, int 좋아요_수,
            boolean 좋아요_눌렀는지_여부, double 평점,
            List<Celeb> 셀럽들, List<RestaurantImage> 음식점_사진들
    ) {
        return new RestaurantSearchQueryResponse(
                음식점.id(),
                음식점.name(),
                음식점.category(),
                음식점.superCategory(),
                음식점.roadAddress(),
                음식점.latitude(),
                음식점.longitude(),
                음식점.phoneNumber(),
                음식점.naverMapUrl(),
                음식점.viewCount(),
                0,  // 거리
                좋아요_수,
                좋아요_눌렀는지_여부,
                평점,
                celebQueryResponses(음식점, 셀럽들),
                restaurantImageQueryResponses(음식점, 음식점_사진들)
        );
    }

    private static List<CelebQueryResponse> celebQueryResponses(Restaurant 음식점, List<Celeb> 셀럽들) {
        return 셀럽들.stream()
                .map(셀럽 -> new CelebQueryResponse(
                        음식점.id(),
                        셀럽.id(),
                        셀럽.name(),
                        셀럽.youtubeChannelName(),
                        셀럽.profileImageUrl())
                ).toList();
    }

    private static List<RestaurantImageQueryResponse> restaurantImageQueryResponses(
            Restaurant 음식점,
            List<RestaurantImage> 사진들
    ) {
        return 사진들.stream()
                .map(사진 -> new RestaurantImageQueryResponse(
                        음식점.id(),
                        사진.id(),
                        Base64Util.encode(사진.name()),
                        사진.author(),
                        사진.socialMedia().name())
                ).toList();
    }
}
