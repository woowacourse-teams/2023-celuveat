package com.celuveat.restaurant.command.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.PERMISSION_DENIED;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.common.IntegrationTest;
import com.celuveat.common.exception.BaseException;
import com.celuveat.common.exception.BaseExceptionType;
import com.celuveat.common.infra.aws.AwsS3ImageUploadClient;
import com.celuveat.restaurant.command.application.dto.DeleteReviewCommand;
import com.celuveat.restaurant.command.application.dto.SaveReviewRequestCommand;
import com.celuveat.restaurant.command.application.dto.UpdateReviewRequestCommand;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImageRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 리뷰 서비스(RestaurantReviewService) 은(는)")
class RestaurantReviewServiceTest {

    @Autowired
    private RestaurantReviewService restaurantReviewService;

    @Autowired
    private RestaurantReviewRepository restaurantReviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OauthMemberRepository oauthMemberRepository;

    @Autowired
    private RestaurantReviewImageRepository restaurantReviewImageRepository;

    @MockBean
    private AwsS3ImageUploadClient imageUploadClient;

    private Restaurant restaurant;
    private Restaurant otherRestaurant;
    private OauthMember member;
    private OauthMember otherMember;

    @BeforeEach
    void setUp() {
        restaurant = 음식점("로이스음식점");
        restaurantRepository.save(restaurant);
        otherRestaurant = 음식점("오도음식점");
        restaurantRepository.save(otherRestaurant);
        member = 멤버("도기");
        oauthMemberRepository.save(member);
        otherMember = 멤버("말랑");
        oauthMemberRepository.save(otherMember);
    }

    @Test
    void 리뷰를_저장한다() {
        // given
        SaveReviewRequestCommand command =
                new SaveReviewRequestCommand("정말 맛있어요", member.id(), restaurant.id(), 5.0, null);
        RestaurantReview expected = new RestaurantReview("정말 맛있어요", member, restaurant, 5.0);

        // when
        Long reviewId = restaurantReviewService.create(command);

        // then
        RestaurantReview result = restaurantReviewRepository.getById(reviewId);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "createdDate")
                .isEqualTo(expected);
    }

    @Test
    void 리뷰를_저장하면_음식점에_전체_평점과_리뷰수를_증가시킨다() {
        // given
        SaveReviewRequestCommand commandA =
                new SaveReviewRequestCommand("정말 맛있어요", member.id(), restaurant.id(), 5.0, null);
        SaveReviewRequestCommand commandB =
                new SaveReviewRequestCommand("정말 맛있어요", member.id(), restaurant.id(), 3.0, null);

        // when
        restaurantReviewService.create(commandA);
        restaurantReviewService.create(commandB);

        // then
        Restaurant savedRestaurant = restaurantRepository.getById(restaurant.id());
        assertThat(savedRestaurant.reviewCount()).isEqualTo(2);
        assertThat(savedRestaurant.ratingTotal()).isEqualTo(8.0);
    }

    @Test
    void 다른_사람의_리뷰를_수정하려하면_예외가_발생한다() {
        // given
        SaveReviewRequestCommand saveCommand =
                new SaveReviewRequestCommand("정말 맛있어요", member.id(), restaurant.id(), 5.0, null);
        Long reviewId = restaurantReviewService.create(saveCommand);
        UpdateReviewRequestCommand updateCommand =
                new UpdateReviewRequestCommand("더 맛있어졌어요", reviewId, otherMember.id(), 5.0);

        // when
        BaseExceptionType result = assertThrows(BaseException.class, () ->
                restaurantReviewService.update(updateCommand)
        ).exceptionType();

        // then
        assertThat(result).isEqualTo(PERMISSION_DENIED);
    }

    @Test
    void 리뷰를_수정한다() {
        // given
        SaveReviewRequestCommand saveCommand =
                new SaveReviewRequestCommand("정말 맛있어요", member.id(), restaurant.id(), 5.0, null);
        Long reviewId = restaurantReviewService.create(saveCommand);
        UpdateReviewRequestCommand updateCommand =
                new UpdateReviewRequestCommand("사장님이 초심을 잃었어요!", reviewId, member.id(), 0.0);
        RestaurantReview expected = new RestaurantReview("사장님이 초심을 잃었어요!", member, restaurant, 0.0);

        // when
        restaurantReviewService.update(updateCommand);

        // then
        RestaurantReview result = restaurantReviewRepository.getById(reviewId);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "createdDate")
                .isEqualTo(expected);
    }


    @Test
    void 리뷰를_수정하면_음식점의_전체_평점이_변경된다() {
        // given
        SaveReviewRequestCommand saveCommandA =
                new SaveReviewRequestCommand("정말 맛있어요", member.id(), restaurant.id(), 5.0, null);
        restaurantReviewService.create(saveCommandA);
        SaveReviewRequestCommand saveCommandB =
                new SaveReviewRequestCommand("정말 맛있어요", member.id(), restaurant.id(), 5.0, null);
        Long reviewId = restaurantReviewService.create(saveCommandB);
        UpdateReviewRequestCommand updateCommand =
                new UpdateReviewRequestCommand("사장님이 초심을 잃었어요!", reviewId, member.id(), 1.0);

        // when
        restaurantReviewService.update(updateCommand);

        // then
        Restaurant savedRestaurant = restaurantRepository.getById(restaurant.id());
        assertThat(savedRestaurant.reviewCount()).isEqualTo(2);
        assertThat(savedRestaurant.ratingTotal()).isNotEqualTo(10.0);
        assertThat(savedRestaurant.ratingTotal()).isEqualTo(6.0);
    }

    @Test
    void 다른_사람의_리뷰를_삭제하려하면_예외가_발생한다() {
        // given
        SaveReviewRequestCommand saveCommand =
                new SaveReviewRequestCommand("정말 맛있어요", member.id(), restaurant.id(), 5.0, null);
        Long reviewId = restaurantReviewService.create(saveCommand);
        DeleteReviewCommand deleteCommand = new DeleteReviewCommand(reviewId, otherMember.id());

        // when
        BaseExceptionType result = assertThrows(BaseException.class, () ->
                restaurantReviewService.delete(deleteCommand)
        ).exceptionType();

        // then
        assertThat(result).isEqualTo(PERMISSION_DENIED);
    }

    @Test
    void 리뷰를_삭제한다() {
        // given
        SaveReviewRequestCommand saveCommand =
                new SaveReviewRequestCommand("정말 맛있어요", member.id(), restaurant.id(), 5.0, null);
        Long reviewId = restaurantReviewService.create(saveCommand);
        DeleteReviewCommand deleteCommand = new DeleteReviewCommand(reviewId, member.id());

        // when
        restaurantReviewService.delete(deleteCommand);
        Optional<RestaurantReview> result = restaurantReviewRepository.findById(reviewId);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void 리뷰를_삭제하면_음식점의_전체_평점과_리뷰수가_감소한다() {
        // given
        SaveReviewRequestCommand saveCommand =
                new SaveReviewRequestCommand("정말 맛있어요", member.id(), restaurant.id(), 5.0, null);
        Long reviewId = restaurantReviewService.create(saveCommand);
        DeleteReviewCommand deleteCommand = new DeleteReviewCommand(reviewId, member.id());

        // when
        restaurantReviewService.delete(deleteCommand);

        // then
        Restaurant savedRestaurant = restaurantRepository.getById(restaurant.id());
        assertThat(savedRestaurant.reviewCount()).isEqualTo(0);
        assertThat(savedRestaurant.ratingTotal()).isEqualTo(0.0);
    }

    @Test
    void 리뷰를_사진과_함께_저장한다() {
        // given
        MultipartFile imageA = getMockImageFile("imageA", "imageA.webp");
        MultipartFile imageB = getMockImageFile("imageB", "imageB.webp");
        SaveReviewRequestCommand command =
                new SaveReviewRequestCommand("정말 맛있어요", member.id(), restaurant.id(), 5.0, List.of(imageA, imageB));
        willDoNothing().given(imageUploadClient).upload(imageA);
        willDoNothing().given(imageUploadClient).upload(imageB);
        RestaurantReview expected = new RestaurantReview("정말 맛있어요", member, restaurant, 5.0);

        // when
        Long reviewId = restaurantReviewService.create(command);

        // then
        RestaurantReview savedReview = restaurantReviewRepository.getById(reviewId);
        List<RestaurantReviewImage> reviewImages
                = restaurantReviewImageRepository.findRestaurantReviewImagesByRestaurantReview(savedReview);

        assertThat(savedReview).usingRecursiveComparison()
                .ignoringFields("id", "createdDate")
                .isEqualTo(expected);
        assertThat(reviewImages).hasSize(2);
        assertThat(reviewImages).extracting("name")
                .containsExactlyInAnyOrder("imageA", "imageB");
    }

    private MockMultipartFile getMockImageFile(final String name, final String originalFilename) {
        return new MockMultipartFile(
                name, originalFilename, "multipart/form-data", name.getBytes()
        );
    }
}
