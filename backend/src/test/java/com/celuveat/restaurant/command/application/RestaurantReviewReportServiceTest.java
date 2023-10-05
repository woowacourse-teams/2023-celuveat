package com.celuveat.restaurant.command.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantReviewFixture.음식점_리뷰;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewReport;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("음식점 리뷰 신고 서비스(RestaurantReviewReportService) 은(는)")
class RestaurantReviewReportServiceTest extends IntegrationTest {

    @Test
    void 음식점_리뷰를_신고한다() {
        // given
        Restaurant 맛집 = restaurantRepository.save(음식점("맛집"));
        OauthMember 오도 = oauthMemberRepository.save(멤버("오도"));
        RestaurantReview 음식점_리뷰 = restaurantReviewRepository.save(음식점_리뷰(오도, 맛집));
        OauthMember 로이스 = oauthMemberRepository.save(멤버("로이스"));
        OauthMember 도기 = oauthMemberRepository.save(멤버("도기"));

        // when
        restaurantReviewReportService.report("부적절한 댓글입니다1", 음식점_리뷰.id(), 로이스.id());
        restaurantReviewReportService.report("부적절한 댓글입니다2", 음식점_리뷰.id(), 도기.id());
        List<RestaurantReviewReport> result = restaurantReviewReportRepository.findAllByRestaurantReviewId(음식점_리뷰.id());

        // then
        List<RestaurantReviewReport> expected = List.of(
                new RestaurantReviewReport("부적절한 댓글입니다1", 음식점_리뷰, 로이스),
                new RestaurantReviewReport("부적절한 댓글입니다2", 음식점_리뷰, 도기)
        );

        assertThat(result).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expected);
    }
}
