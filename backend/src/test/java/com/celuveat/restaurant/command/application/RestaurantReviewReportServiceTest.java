package com.celuveat.restaurant.command.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantReviewFixture.음식점_리뷰;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewReport;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewReportRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 리뷰 신고 서비스(RestaurantReviewReportService) 은(는)")
class RestaurantReviewReportServiceTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantReviewRepository restaurantReviewRepository;

    @Autowired
    private OauthMemberRepository oauthMemberRepository;

    @Autowired
    private RestaurantReviewReportRepository restaurantReviewReportRepository;

    @Autowired
    private RestaurantReviewReportService restaurantReviewReportService;

    @Test
    void 음식점_리뷰를_신고한다() {
        // given
        Restaurant 맛집 = 음식점("맛집");
        OauthMember 오도 = 멤버("오도");
        RestaurantReview 음식점_리뷰 = 음식점_리뷰(오도, 맛집);
        OauthMember 로이스 = 멤버("로이스");
        OauthMember 도기 = 멤버("도기");
        restaurantRepository.save(맛집);
        oauthMemberRepository.save(오도);
        restaurantReviewRepository.save(음식점_리뷰);
        oauthMemberRepository.save(로이스);
        oauthMemberRepository.save(도기);
        List<RestaurantReviewReport> expected = List.of(
                new RestaurantReviewReport("부적절한 댓글입니다1", 음식점_리뷰, 로이스),
                new RestaurantReviewReport("부적절한 댓글입니다2", 음식점_리뷰, 도기)
        );

        // when
        restaurantReviewReportService.report("부적절한 댓글입니다1", 음식점_리뷰.id(), 로이스.id());
        restaurantReviewReportService.report("부적절한 댓글입니다2", 음식점_리뷰.id(), 도기.id());
        List<RestaurantReviewReport> result = restaurantReviewReportRepository.findAllByRestaurantReviewId(음식점_리뷰.id());

        // then
        assertThat(result).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expected);
    }
}
