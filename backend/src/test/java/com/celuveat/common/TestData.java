package com.celuveat.common;

import static java.util.stream.Collectors.groupingBy;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.video.command.domain.Video;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Builder.Default;

/**
 * EX) private TestData testData = new TestData();
 * <p>
 * private final TestDataCreator testDataCreator = () -> { testData.addCelebs(셀럽들); testData.addRestaurants(음식점들);
 * testData.addImages(음식점사진들); testData.addVideos(음식점영상들); return testData; };
 *
 * @BeforeEach void setUp() { testDataInserter.insertData(testDataCreator); }
 */
public class TestData {

    @Default
    private final List<Celeb> celebs = new ArrayList<>();

    @Default
    private final List<Restaurant> restaurants = new ArrayList<>();

    @Default
    private final List<RestaurantImage> restaurantImages = new ArrayList<>();

    @Default
    private final List<Video> videos = new ArrayList<>();

    public TestData() {
    }

    @Builder
    public TestData(
            List<Celeb> celebs,
            List<Restaurant> restaurants,
            List<RestaurantImage> restaurantImages,
            List<Video> videos
    ) {
        this.celebs.addAll(celebs);
        this.restaurants.addAll(restaurants);
        this.restaurantImages.addAll(restaurantImages);
        this.videos.addAll(videos);
    }

    public void addCelebs(List<Celeb> celebs) {
        this.celebs.addAll(celebs);
    }

    public void addCelebs(Map<String, Celeb> celebs) {
        this.celebs.addAll(celebs.values().stream().toList());
    }

    public void addRestaurants(List<Restaurant> restaurants) {
        this.restaurants.addAll(restaurants);
    }

    public void addImages(List<RestaurantImage> images) {
        this.restaurantImages.addAll(images);
    }

    public void addVideo(Video video) {
        this.videos.add(video);
    }

    public void addVideos(List<Video> videos) {
        this.videos.addAll(videos);
    }

    public Map<Restaurant, List<RestaurantImage>> imageWithRestaurant() {
        return restaurantImages.stream()
                .collect(groupingBy(RestaurantImage::restaurant));
    }

    public Map<Restaurant, List<Video>> videoWithRestaurant() {
        return videos.stream()
                .collect(groupingBy(Video::restaurant));
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
}
