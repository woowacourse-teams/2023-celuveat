package com.celuveat.restaurant.query;

import static com.celuveat.restaurant.fixture.RestaurantFixture.좋아요_누르지_않음;
import static com.celuveat.restaurant.fixture.RestaurantFixture.좋아요_누름;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.celuveat.common.util.Base64Util;
import com.celuveat.restaurant.query.dao.RestaurantReviewsQueryResponseDao;
import com.celuveat.restaurant.query.dto.RestaurantReviewsQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantReviewsQueryResponse.RestaurantReviewSingleQueryResponse;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 리뷰 조회 서비스(RestaurantReviewQueryService) 은(는)")
class RestaurantReviewQueryServiceTest {

    private final RestaurantReviewsQueryResponseDao restaurantReviewsQueryResponseDao =
            mock(RestaurantReviewsQueryResponseDao.class);
    private final RestaurantReviewQueryService restaurantReviewQueryService =
            new RestaurantReviewQueryService(restaurantReviewsQueryResponseDao);

    @Test
    void 음식점의_모든_리뷰를_조회한다() {
        // given
        given(restaurantReviewsQueryResponseDao.findByRestaurantId(1L, 3L))
                .willReturn(new RestaurantReviewsQueryResponse(
                        2,
                        List.of(
                                new RestaurantReviewSingleQueryResponse(
                                        1L,
                                        3L,
                                        "말랑",
                                        "https://말랑.Profile.com",
                                        "맛이 별로네..",
                                        LocalDate.now().toString(),
                                        2,
                                        좋아요_누름,
                                        1.0,
                                        List.of("말랑이의 리뷰 이미지1", "말랑이의 리뷰 이미지2")
                                ),
                                new RestaurantReviewSingleQueryResponse(
                                        2L,
                                        2L,
                                        "오도",
                                        "https://오도.Profile.com",
                                        "꿀맛..",
                                        LocalDate.now().toString(),
                                        5,
                                        좋아요_누르지_않음,
                                        5.0,
                                        Collections.emptyList()
                                )
                        )));

        // when
        RestaurantReviewsQueryResponse result =
                restaurantReviewQueryService.findByRestaurantId(1L, 3L);

        // then
        assertThat(result.totalElementsCount()).isEqualTo(2);
        assertThat(result.reviews())
                .satisfies(it -> {
                    assertThat(it)
                            .extracting(RestaurantReviewSingleQueryResponse::content)
                            .containsExactly("맛이 별로네..", "꿀맛..");
                    assertThat(it)
                            .extracting(RestaurantReviewSingleQueryResponse::images)
                            .containsExactly(
                                    Stream.of("말랑이의 리뷰 이미지1", "말랑이의 리뷰 이미지2")
                                            .map(Base64Util::encode)
                                            .toList(),
                                    Collections.emptyList()
                            );
                });
    }
}
