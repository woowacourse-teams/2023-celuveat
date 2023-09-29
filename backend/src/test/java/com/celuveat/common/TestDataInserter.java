package com.celuveat.common;

import com.celuveat.celeb.command.domain.CelebRepository;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantImageRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.command.domain.VideoRepository;
import java.util.List;
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
        return insertData(testData);
    }

    public TestData insertData(TestData testData) {
        celebRepository.saveAll(testData.celebs());
        restaurantRepository.saveAll(testData.restaurants());
        insertImages(testData.restaurantImages());
        insertVideos(testData.videos());
        return testData;
    }

    private void insertImages(List<RestaurantImage> images) {
        if (images == null) {
            return;
        }
        restaurantImageRepository.saveAll(images);
    }

    private void insertVideos(List<Video> videos) {
        if (videos == null) {
            return;
        }
        videoRepository.saveAll(videos);
    }
}
