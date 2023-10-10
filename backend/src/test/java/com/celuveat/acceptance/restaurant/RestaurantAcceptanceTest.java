package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.생성됨;
import static com.celuveat.acceptance.common.AcceptanceSteps.없음;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.잘못된_요청;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.근처_음식점_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.상세_조회_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.상세_조회_응답;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.위치_검색_영역_요청_데이터;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_결과;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_조건_요청_데이터;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_상세_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_좋아요_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_좋아요_정렬_검색_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.정보_수정_제안_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.좋아요한_음식점_검색_결과;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.좋아요한_음식점_조회_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.좋아요한_음식점_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.특정_거리_이내에_있는_음식점인지_검증한다;
import static com.celuveat.auth.fixture.OauthMemberFixture.도기;
import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.auth.fixture.OauthMemberFixture.오도;
import static com.celuveat.celeb.fixture.CelebFixture.맛객리우;
import static com.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celeb.fixture.CelebFixture.회사랑;
import static com.celuveat.restaurant.fixture.RestaurantFixture.RestaurantPointFixture.대한민국_전체를_포함한_검색_영역_요청;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static com.celuveat.restaurant.fixture.RestaurantFixture.모던샤브하우스;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantFixture.좋아요_누르지_않음;
import static com.celuveat.restaurant.fixture.RestaurantFixture.좋아요_누름;
import static com.celuveat.restaurant.fixture.RestaurantFixture.하늘초밥;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.대성집_사진;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.모던샤브하우스_사진;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.하늘초밥_사진;
import static com.celuveat.restaurant.util.RestaurantQueryTestUtils.일정_거리내_위경도;
import static com.celuveat.video.fixture.VideoFixture.맛객리우의_모던샤브하우스_영상;
import static com.celuveat.video.fixture.VideoFixture.성시경의_대성집_영상;
import static com.celuveat.video.fixture.VideoFixture.영상;
import static com.celuveat.video.fixture.VideoFixture.회사랑의_하늘초밥_영상;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("음식점 인수테스트")
public class RestaurantAcceptanceTest extends AcceptanceTest {

    @Nested
    class 음식점_조회_API {

        @Nested
        class 음식점_검색_시 {

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

            @BeforeEach
            void setUp() {
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
                        영상(회사랑, 대성집),
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
                초기_데이터_저장();
            }

            @Test
            void 검색영역_조건으로_음식점을_조회한다() {
                // when
                var 응답 = 음식점_검색_요청(
                        음식점_검색_조건_요청_데이터(없음, 없음, 없음),
                        대한민국_전체를_포함한_검색_영역_요청()
                );

                // then
                var 예상_응답 = List.of(
                        음식점_검색_결과(
                                하늘초밥,
                                좋아요_누르지_않음, 0,
                                List.of(회사랑),
                                List.of(하늘초밥_사진_1)
                        ),
                        음식점_검색_결과(
                                모던샤브하우스,
                                좋아요_누르지_않음, 0,
                                List.of(맛객리우),
                                List.of(모던샤브하우스_사진_1)
                        ),
                        음식점_검색_결과(
                                대성집,
                                좋아요_누르지_않음, 4.5,
                                List.of(회사랑, 성시경),
                                List.of(대성집_사진_1, 대성집_사진_2)
                        )
                );
                조회_결과를_검증한다(예상_응답, 응답);
            }

            @Test
            void 상위_카테고리로_조회시_포함된_하위_카테고리들이_조회된다() {
                // when
                var 응답 = 음식점_검색_요청(
                        음식점_검색_조건_요청_데이터(없음, "일식", 없음),
                        대한민국_전체를_포함한_검색_영역_요청()
                );

                // then
                var 예상_응답 = List.of(
                        음식점_검색_결과(
                                하늘초밥,
                                좋아요_누르지_않음, 0,
                                List.of(회사랑),
                                List.of(하늘초밥_사진_1)
                        ),
                        음식점_검색_결과(
                                모던샤브하우스,
                                좋아요_누르지_않음, 0,
                                List.of(맛객리우),
                                List.of(모던샤브하우스_사진_1)
                        )
                );
                조회_결과를_검증한다(예상_응답, 응답);
            }

            @Test
            void 음식점_좋아요_기준_정렬() {
                // when
                var 응답 = 음식점_좋아요_정렬_검색_요청(
                        음식점_검색_조건_요청_데이터(없음, 없음, 없음),
                        대한민국_전체를_포함한_검색_영역_요청()
                );

                // then
                var 예상_응답 = List.of(
                        음식점_검색_결과(
                                대성집,
                                좋아요_누르지_않음, 4.5,
                                List.of(회사랑, 성시경),
                                List.of(대성집_사진_1, 대성집_사진_2)
                        ),
                        음식점_검색_결과(
                                하늘초밥,
                                좋아요_누르지_않음, 0,
                                List.of(회사랑),
                                List.of(하늘초밥_사진_1)
                        ),
                        음식점_검색_결과(
                                모던샤브하우스,
                                좋아요_누르지_않음, 0,
                                List.of(맛객리우),
                                List.of(모던샤브하우스_사진_1)
                        )
                );
                조회_결과를_검증한다(예상_응답, 응답);
            }

            @Test
            void 음식점_검색_조건으로_검색한다() {
                // given
                var 말랑_세션_ID = 로그인후_세션아이디를_가져온다(말랑);

                // when
                var 응답 = 음식점_검색_요청(
                        말랑_세션_ID,
                        음식점_검색_조건_요청_데이터(null, null, "대성집"),
                        대한민국_전체를_포함한_검색_영역_요청()
                );

                // then
                var 예상_응답 = List.of(
                        음식점_검색_결과(
                                대성집,
                                좋아요_누름, 4.5,
                                List.of(회사랑, 성시경),
                                List.of(대성집_사진_1, 대성집_사진_2)
                        )
                );
                조회_결과를_검증한다(예상_응답, 응답);
            }

            @Test
            void 음식점_전체_조회시_셀럽_필터를_사용하면_해당_셀럽과_셀럽의_이미지가_첫번째로_설정된다() {
                // when
                var 응답 = 음식점_검색_요청(
                        음식점_검색_조건_요청_데이터(성시경.id(), null, "대성집"),
                        대한민국_전체를_포함한_검색_영역_요청()
                );

                // then
                var 예상_응답 = List.of(
                        음식점_검색_결과(
                                대성집,
                                좋아요_누르지_않음, 4.5,
                                List.of(성시경, 회사랑),
                                List.of(대성집_사진_1, 대성집_사진_2)
                        )
                );
                조회_결과를_검증한다(예상_응답, 응답);
            }

            @Test
            void 위경도_검색조건이_모두_Null_이면_전국지도로_검색한다() {
                // when
                var 응답 = 음식점_좋아요_정렬_검색_요청(
                        음식점_검색_조건_요청_데이터(없음, 없음, 없음),
                        위치_검색_영역_요청_데이터(없음, 없음, 없음, 없음)
                );

                // then
                var 예상_응답 = List.of(
                        음식점_검색_결과(
                                대성집,
                                좋아요_누르지_않음, 4.5,
                                List.of(회사랑, 성시경),
                                List.of(대성집_사진_1, 대성집_사진_2)
                        ),
                        음식점_검색_결과(
                                하늘초밥,
                                좋아요_누르지_않음, 0,
                                List.of(회사랑),
                                List.of(하늘초밥_사진_1)
                        ),
                        음식점_검색_결과(
                                모던샤브하우스,
                                좋아요_누르지_않음, 0,
                                List.of(맛객리우),
                                List.of(모던샤브하우스_사진_1)
                        )
                );
                조회_결과를_검증한다(예상_응답, 응답);
            }

            @Test
            void 위경도_검색조건이_몇개만_null_이면_오류이다() {
                // when
                var 응답 = 음식점_검색_요청(
                        음식점_검색_조건_요청_데이터(없음, 없음, 없음),
                        위치_검색_영역_요청_데이터(없음, 없음, 10.1, 없음)
                );

                // then
                응답_상태를_검증한다(응답, 잘못된_요청);
            }
        }

        @Nested
        class 근처_음식점_조회_시 {

            private final OauthMember 말랑 = 말랑();
            private final Celeb 성시경 = 성시경();
            private final Celeb 맛객리우 = 맛객리우();
            private final Restaurant 대성집 = 대성집();
            private final Restaurant 대성집_1000m_거리_음식점 = 음식점(
                    "대성집 1000m 거리 음식점",
                    "한식",
                    일정_거리내_위경도(대성집.latitude(), 대성집.longitude(), 1000).getFirst(),
                    일정_거리내_위경도(대성집.latitude(), 대성집.longitude(), 1000).getSecond()
            );
            private final RestaurantImage 대성집_1000m_거리_음식점_사진_1 = 대성집_사진(대성집_1000m_거리_음식점, 1);
            private final RestaurantImage 대성집_1000m_거리_음식점_사진_2 = 대성집_사진(대성집_1000m_거리_음식점, 2);
            private final Restaurant 대성집_2000m_거리_음식점 = 음식점(
                    "대성집 2000m 거리 음식점",
                    "한식",
                    일정_거리내_위경도(대성집.latitude(), 대성집.longitude(), 2000).getFirst(),
                    일정_거리내_위경도(대성집.latitude(), 대성집.longitude(), 2000).getSecond()
            );
            private final RestaurantImage 대성집_2000m_거리_음식점_사진_1 = 대성집_사진(대성집_2000m_거리_음식점, 1);
            private final Restaurant 대성집_3000m_거리_음식점 = 음식점(
                    "대성집 3000m 거리 음식점",
                    "한식",
                    일정_거리내_위경도(대성집.latitude(), 대성집.longitude(), 3000).getFirst(),
                    일정_거리내_위경도(대성집.latitude(), 대성집.longitude(), 3000).getSecond()
            );

            @BeforeEach
            void setUp() {
                testData.addMembers(말랑);
                testData.addCelebs(성시경, 맛객리우);
                testData.addRestaurants(대성집, 대성집_1000m_거리_음식점, 대성집_2000m_거리_음식점, 대성집_3000m_거리_음식점);
                testData.addRestaurantImages(
                        대성집_1000m_거리_음식점_사진_1,
                        대성집_1000m_거리_음식점_사진_2,
                        대성집_2000m_거리_음식점_사진_1
                );
                testData.addVideos(
                        성시경의_대성집_영상(성시경, 대성집),
                        영상(맛객리우, 대성집_1000m_거리_음식점),
                        영상(성시경, 대성집_2000m_거리_음식점),
                        영상(맛객리우, 대성집_3000m_거리_음식점)
                );
                testData.addRestaurantLikes(RestaurantLike.create(대성집_1000m_거리_음식점, 말랑));
                초기_데이터_저장();
            }

            @Test
            void 특정_음식점을_기준으로_일정_거리_내에_있는_모든_음식점을_조회한다() {
                // when
                var 요청_결과 = 근처_음식점_조회_요청(대성집.id(), 2000);

                // then
                var 예상_응답 = List.of(
                        음식점_검색_결과(
                                대성집_1000m_거리_음식점,
                                좋아요_누르지_않음, 0,
                                List.of(맛객리우),
                                List.of(대성집_1000m_거리_음식점_사진_1, 대성집_1000m_거리_음식점_사진_2)
                        ),
                        음식점_검색_결과(
                                대성집_2000m_거리_음식점,
                                좋아요_누르지_않음, 0,
                                List.of(성시경),
                                List.of(대성집_2000m_거리_음식점_사진_1)
                        )
                );
                특정_거리_이내에_있는_음식점인지_검증한다(예상_응답, 요청_결과, 2000);
            }

            @Test
            void 근처_음식점을_조회할때_세션이_담기면_좋아요_여부도_포함된다() {
                // when
                var 말랑_세션_ID = 로그인후_세션아이디를_가져온다(말랑);
                var 요청_결과 = 근처_음식점_조회_요청(말랑_세션_ID, 대성집.id(), 2000);

                // then
                var 예상_응답 = List.of(
                        음식점_검색_결과(
                                대성집_1000m_거리_음식점,
                                좋아요_누름, 0,
                                List.of(맛객리우),
                                List.of(대성집_1000m_거리_음식점_사진_1, 대성집_1000m_거리_음식점_사진_2)
                        ),
                        음식점_검색_결과(
                                대성집_2000m_거리_음식점,
                                좋아요_누르지_않음, 0,
                                List.of(성시경),
                                List.of(대성집_2000m_거리_음식점_사진_1)
                        )
                );
                특정_거리_이내에_있는_음식점인지_검증한다(예상_응답, 요청_결과, 2000);
            }
        }

        @Nested
        class 음식점_상세_조회_시 {

            private final OauthMember 말랑 = 말랑();
            private final OauthMember 오도 = 오도();
            private final Restaurant 대성집 = 대성집();
            private final RestaurantImage 대성집_사진_1 = 대성집_사진(대성집, 1);
            private final RestaurantImage 대성집_사진_2 = 대성집_사진(대성집, 2);
            private final Celeb 성시경 = 성시경();
            private final Celeb 회사랑 = 회사랑();
            private final Celeb 맛객리우 = 맛객리우();
            private final RestaurantReview 대성집_리뷰_1 = RestaurantReview.create(대성집, 말랑, "음...", 4.0);
            private final RestaurantReview 대성집_리뷰_2 = RestaurantReview.create(대성집, 오도, "흠...", 2.0);

            @BeforeEach
            void setUp() {
                testData.addMembers(말랑, 오도);
                testData.addCelebs(성시경, 회사랑, 맛객리우);
                testData.addRestaurants(대성집);
                testData.addRestaurantImages(대성집_사진_1, 대성집_사진_2);
                testData.addVideos(
                        성시경의_대성집_영상(성시경, 대성집)
                );
                testData.addRestaurantLikes(RestaurantLike.create(대성집, 말랑));
                testData.addRestaurantReviews(대성집_리뷰_1, 대성집_리뷰_2);
                초기_데이터_저장();
            }

            @Test
            void 음식점ID로_조회() {
                // when
                var 응답 = 음식점_상세_조회_요청(대성집.id(), 성시경.id());

                // then
                var 예상_응답 = 상세_조회_응답(
                        대성집,
                        좋아요_누르지_않음,
                        3.0,
                        List.of(성시경),
                        List.of(대성집_사진_1, 대성집_사진_2)
                );
                상세_조회_결과를_검증한다(예상_응답, 응답);
            }

            @Test
            void 음식점ID로_조회시_좋아요_여부_반영() {
                // given
                String 말랑_세션_ID = 로그인후_세션아이디를_가져온다(말랑);

                // when
                var 응답 = 음식점_상세_조회_요청(말랑_세션_ID, 대성집.id(), 성시경.id());

                // then
                var 예상_응답 = 상세_조회_응답(
                        대성집,
                        좋아요_누름,
                        3.0,
                        List.of(성시경),
                        List.of(대성집_사진_1, 대성집_사진_2)
                );
                상세_조회_결과를_검증한다(예상_응답, 응답);
            }

            @Test
            void 셀럽ID_없이_음식점을_상세_조회하면_예외가_발생한다() {
                // when
                var 조회_음식점_ID = 1L;
                var 응답 = 음식점_상세_조회_요청(조회_음식점_ID, (Long) 없음);

                // then
                응답_상태를_검증한다(응답, 잘못된_요청);
            }
        }
    }

    @Nested
    class 정보_수정_제안_API {

        private final Restaurant 대성집 = 대성집();

        @BeforeEach
        void setUp() {
            testData.addRestaurants(대성집);
            초기_데이터_저장();
        }

        @Test
        void 정보_수정_제안_요청을_보낸다() {
            // when
            var 응답 = 정보_수정_제안_요청(대성집.id(), "음식점 정보가 이상해요", "일 똑바로 하세요 셀럽잇");

            // then
            응답_상태를_검증한다(응답, 생성됨);
        }
    }

    @Nested
    class 음식점_좋아요_API {

        private final OauthMember 말랑 = 말랑();
        private final Restaurant 대성집 = 대성집();
        private final Restaurant 하늘초밥 = 하늘초밥();
        private final Celeb 성시경 = 성시경();
        private final Celeb 회사랑 = 회사랑();
        private final RestaurantImage 대성집_사진_1 = 대성집_사진(대성집, 1);
        private final RestaurantImage 대성집_사진_2 = 대성집_사진(대성집, 2);
        private final RestaurantImage 하늘초밥_사진_1 = 하늘초밥_사진(하늘초밥, 1);

        @BeforeEach
        protected void setUp() {
            testData.addMembers(말랑);
            testData.addCelebs(성시경, 회사랑);
            testData.addRestaurants(대성집, 하늘초밥);
            testData.addRestaurantImages(
                    대성집_사진_1,
                    대성집_사진_2,
                    하늘초밥_사진_1
            );
            testData.addVideos(
                    성시경의_대성집_영상(성시경, 대성집),
                    회사랑의_하늘초밥_영상(회사랑, 하늘초밥)
            );
            초기_데이터_저장();
        }

        @Test
        void 음식점_좋아요를_누른다() {
            // given
            var 말랑_세션_ID = 로그인후_세션아이디를_가져온다(말랑);

            // when
            var 좋아요_응답 = 음식점_좋아요_요청(말랑_세션_ID, 대성집.id());

            // then
            응답_상태를_검증한다(좋아요_응답, 정상_처리);
            var 음식점_상세_조회_응답 = 음식점_상세_조회_요청(말랑_세션_ID, 대성집.id(), 성시경.id());
            var 예상_응답 = 상세_조회_응답(
                    대성집,
                    좋아요_누름,
                    1,
                    0.0,
                    List.of(성시경),
                    List.of(대성집_사진_1, 대성집_사진_2)
            );
            상세_조회_결과를_검증한다(예상_응답, 음식점_상세_조회_응답);
        }

        @Test
        void 좋아요한_음식점을_조회한다() {
            // given
            var 말랑_세션_아이디 = 로그인후_세션아이디를_가져온다(말랑);

            // when
            음식점_좋아요_요청(말랑_세션_아이디, 대성집.id());
            음식점_좋아요_요청(말랑_세션_아이디, 하늘초밥.id());

            // when
            var 응답 = 좋아요한_음식점_조회_요청(말랑_세션_아이디);

            // then
            응답_상태를_검증한다(응답, 정상_처리);
            // then
            var 예상_응답 = List.of(
                    좋아요한_음식점_검색_결과(
                            하늘초밥,
                            1,
                            0.0,
                            List.of(회사랑),
                            List.of(하늘초밥_사진_1)
                    ),
                    좋아요한_음식점_검색_결과(
                            대성집,
                            1,
                            0.0,
                            List.of(성시경),
                            List.of(대성집_사진_1, 대성집_사진_2)
                    )
            );
            좋아요한_음식점_조회_결과를_검증한다(예상_응답, 응답);
        }
    }
}
