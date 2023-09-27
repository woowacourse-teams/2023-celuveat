package com.celuveat.common;

import com.celuveat.celeb.command.domain.CelebRepository;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantImageRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.command.domain.VideoRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@ActiveProfiles("test")
@RequiredArgsConstructor
public class TestDataInserter {

    private final CelebRepository celebRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final VideoRepository videoRepository;

    public TestData insertData(TestDataCreator testDataCreator) {
        TestData testData = testDataCreator.create();
        celebRepository.saveAll(testData.celebs());
        restaurantRepository.saveAll(testData.restaurants());
        insertImages(testData.restaurantImages());
        insertVideos(testData.videos());
        return testData;
    }

    private void insertImages(Map<Restaurant, List<RestaurantImage>> images) {
        if (images == null) {
            return;
        }
        for (List<RestaurantImage> value : images.values()) {
            restaurantImageRepository.saveAll(value);
        }
    }

    private void insertVideos(Map<Restaurant, List<Video>> videos) {
        if (videos == null) {
            return;
        }
        for (List<Video> value : videos.values()) {
            videoRepository.saveAll(value);
        }
    }
}
