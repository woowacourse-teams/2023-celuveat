package com.celuveat.restaurant.command.application;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.common.util.FileNameUtil;
import com.celuveat.restaurant.command.application.dto.DeleteReviewCommand;
import com.celuveat.restaurant.command.application.dto.SaveReviewRequestCommand;
import com.celuveat.restaurant.command.application.dto.UpdateReviewRequestCommand;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImageRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLikeRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantReviewService {

    private final RestaurantRepository restaurantRepository;
    private final OauthMemberRepository oauthMemberRepository;
    private final RestaurantReviewRepository restaurantReviewRepository;
    private final RestaurantReviewLikeRepository restaurantReviewLikeRepository;
    private final RestaurantReviewImageRepository restaurantReviewImageRepository;

    public Long create(SaveReviewRequestCommand command) {
        OauthMember member = oauthMemberRepository.getById(command.memberId());
        Restaurant restaurant = restaurantRepository.getById(command.restaurantId());
        RestaurantReview restaurantReview =
                RestaurantReview.create(command.content(), member, restaurant, command.rating());
        saveReviewImages(command.images(), restaurantReview);
        return restaurantReviewRepository.save(restaurantReview).id();
    }

    private void saveReviewImages(List<MultipartFile> images, RestaurantReview review) {
        if (Objects.isNull(images)) {
            return;
        }
        restaurantReviewImageRepository.saveAll(images.stream()
                .map(MultipartFile::getOriginalFilename)
                .map(FileNameUtil::removeExtension)
                .map(filename -> new RestaurantReviewImage(filename, review))
                .toList()
        );
    }

    public void update(UpdateReviewRequestCommand command) {
        RestaurantReview review = restaurantReviewRepository.getById(command.reviewId());
        review.update(command.content(), command.memberId(), command.rating());
    }

    public void delete(DeleteReviewCommand command) {
        RestaurantReview review = restaurantReviewRepository.getById(command.reviewId());
        review.delete(command.memberId());
        restaurantReviewLikeRepository.deleteAllByRestaurantReview(review);
        restaurantReviewImageRepository.deleteAllByRestaurantReview(review);
        restaurantReviewRepository.delete(review);
    }
}
