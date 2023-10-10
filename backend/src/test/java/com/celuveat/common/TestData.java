package com.celuveat.common;

import com.celuveat.administrativedistrict.domain.AdministrativeDistrict;
import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLike;
import com.celuveat.video.command.domain.Video;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder.Default;

public class TestData {

    @Default
    private final List<OauthMember> members = new ArrayList<>();

    @Default
    private final List<Celeb> celebs = new ArrayList<>();

    @Default
    private final List<Restaurant> restaurants = new ArrayList<>();

    @Default
    private final List<RestaurantImage> restaurantImages = new ArrayList<>();

    @Default
    private final List<Video> videos = new ArrayList<>();

    @Default
    private final List<RestaurantLike> restaurantLikes = new ArrayList<>();

    @Default
    private final List<RestaurantReview> restaurantReviews = new ArrayList<>();

    @Default
    private final List<RestaurantReviewLike> restaurantReviewLikes = new ArrayList<>();

    @Default
    private final List<AdministrativeDistrict> administrativeDistricts = new ArrayList<>();

    public void addMembers(OauthMember... members) {
        addMembers(Arrays.asList(members));
    }

    public void addMembers(List<OauthMember> members) {
        this.members.addAll(members);
    }

    public void addCelebs(Celeb... celebs) {
        addCelebs(Arrays.asList(celebs));
    }

    public void addCelebs(List<Celeb> celebs) {
        this.celebs.addAll(celebs);
    }

    public void addRestaurants(Restaurant... restaurants) {
        addRestaurants(Arrays.asList(restaurants));
    }

    public void addRestaurants(List<Restaurant> restaurants) {
        this.restaurants.addAll(restaurants);
    }

    public void addRestaurantImages(RestaurantImage... images) {
        addRestaurantImages(Arrays.asList(images));
    }

    public void addRestaurantImages(List<RestaurantImage> images) {
        this.restaurantImages.addAll(images);
    }

    public void addVideos(Video... videos) {
        addVideos(Arrays.asList(videos));
    }

    public void addVideos(List<Video> videos) {
        this.videos.addAll(videos);
    }

    public void addRestaurantLikes(RestaurantLike... restaurantLikes) {
        addRestaurantLikes(Arrays.asList(restaurantLikes));
    }

    public void addRestaurantLikes(List<RestaurantLike> restaurantLikes) {
        this.restaurantLikes.addAll(restaurantLikes);
    }

    public void addRestaurantReviews(RestaurantReview... restaurantReviews) {
        addRestaurantReviews(Arrays.asList(restaurantReviews));
    }

    public void addRestaurantReviews(List<RestaurantReview> restaurantReviews) {
        this.restaurantReviews.addAll(restaurantReviews);
    }

    public void addRestaurantReviewLikes(RestaurantReviewLike... restaurantReviewLikes) {
        addRestaurantReviewLikes(Arrays.asList(restaurantReviewLikes));
    }

    public void addRestaurantReviewLikes(List<RestaurantReviewLike> restaurantReviewLikes) {
        this.restaurantReviewLikes.addAll(restaurantReviewLikes);
    }

    public void addAdministrativeDistricts(AdministrativeDistrict... administrativeDistricts) {
        addAdministrativeDistricts(Arrays.asList(administrativeDistricts));
    }

    public void addAdministrativeDistricts(List<AdministrativeDistrict> administrativeDistricts) {
        this.administrativeDistricts.addAll(administrativeDistricts);
    }

    public List<OauthMember> members() {
        return members;
    }

    public List<Celeb> celebs() {
        return celebs;
    }

    public List<Restaurant> restaurants() {
        return restaurants;
    }

    public List<RestaurantImage> restaurantImages() {
        return restaurantImages;
    }

    public List<Video> videos() {
        return videos;
    }

    public List<RestaurantLike> restaurantLikes() {
        return restaurantLikes;
    }

    public List<RestaurantReview> restaurantReviews() {
        return restaurantReviews;
    }

    public List<RestaurantReviewLike> restaurantReviewLikes() {
        return restaurantReviewLikes;
    }

    public List<AdministrativeDistrict> administrativeDistricts() {
        return administrativeDistricts;
    }
}
