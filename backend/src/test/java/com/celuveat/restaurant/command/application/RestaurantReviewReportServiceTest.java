package com.celuveat.restaurant.command.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.도기;
import static com.celuveat.auth.fixture.OauthMemberFixture.로이스;
import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
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
        OauthMember 말랑 = oauthMemberRepository.save(말랑());
        OauthMember 로이스 = oauthMemberRepository.save(로이스());
        OauthMember 도기 = oauthMemberRepository.save(도기());
        Restaurant 대성집 = restaurantRepository.save(대성집());
        RestaurantReview 대성집_리뷰 = restaurantReviewRepository.save(음식점_리뷰(말랑, 대성집));

        // when
        restaurantReviewReportService.report("부적절한 리뷰인 것 같아요1", 대성집_리뷰.id(), 로이스.id());
        restaurantReviewReportService.report("부적절한 리뷰인 것 같아요2", 대성집_리뷰.id(), 도기.id());

        // then
        List<RestaurantReviewReport> 신고된_대성집_리뷰들 = restaurantReviewReportRepository
                .findAllByRestaurantReviewId(대성집_리뷰.id());
        assertThat(신고된_대성집_리뷰들)
                .extracting(RestaurantReviewReport::content)
                .containsExactly("부적절한 리뷰인 것 같아요1", "부적절한 리뷰인 것 같아요2");
    }
}
