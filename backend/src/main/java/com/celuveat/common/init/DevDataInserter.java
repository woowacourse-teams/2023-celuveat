package com.celuveat.common.init;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.celeb.command.domain.CelebRepository;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantImageRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.SocialMedia;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.command.domain.VideoRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Profile("local")
@RequiredArgsConstructor
@Component
public class DevDataInserter {

    private final Environment environment;
    private final CelebRepository celebRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final VideoRepository videoRepository;
    private final List<Celeb> celebs = new ArrayList<>();
    private final List<Restaurant> restaurants = new ArrayList<>();
    private final List<RestaurantImage> restaurantImages = new ArrayList<>();
    private final List<Video> videos = new ArrayList<>();


    @PostConstruct
    public void insertData() {
        String[] activeProfiles = environment.getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            if (activeProfile.equals("prod")) {
                throw new RuntimeException("PROD 환경에서 임시 데이터 삽입을 시도함");
            }
        }
        insertCelebData();
        insertRestaurantData();
        insertVideoData();
    }

    private void insertCelebData() {
        for (int i = 1; i <= 30; i++) {
            String name = "가짜 셀럽 " + i;
            Celeb build = Celeb.builder()
                    .name(name)
                    .profileImageUrl("https://avatars.githubusercontent.com/u/52229930?v=4")
                    .youtubeChannelName(name)
                    .build();
            celebs.add(celebRepository.save(build));
        }
    }

    private void insertRestaurantData() {
        Random random = new Random();
        for (int i = 1; i <= 20000; i++) {
            double minLatitude = 33.155748007706073;
            double maxLatitude = 38.54379008589715;
            double minLongitude = 126.11965518760987;
            double maxLongitude = 128.45315128135987;
            double latitude = minLatitude + (maxLatitude - minLatitude) * random.nextDouble();
            double longitude = minLongitude + (maxLongitude - minLongitude) * random.nextDouble();
            Restaurant build = Restaurant.builder()
                    .name("가짜 음식점 " + i)
                    .category("한식")
                    .roadAddress("가짜시 가짜동 가짜로")
                    .latitude(latitude)
                    .longitude(longitude)
                    .phoneNumber("010-1111-2222")
                    .naverMapUrl("https://naver.com")
                    .viewCount(0)
                    .build();
            restaurants.add(restaurantRepository.save(build));
            RestaurantImage nama = RestaurantImage.builder()
                    .restaurant(build)
                    .author("nama")
                    .name("가짜 이미지.jpeg")
                    .socialMedia(SocialMedia.INSTAGRAM)
                    .build();
            restaurantImages.add(restaurantImageRepository.save(nama));
        }
    }

    private void insertVideoData() {
        int celebIndex = 0;
        for (int i = 0; i < restaurants.size(); i++) {
            Video build = Video.builder()
                    .celeb(celebs.get(celebIndex % celebs.size()))
                    .restaurant(restaurants.get(i))
                    .uploadDate(LocalDate.now())
                    .youtubeUrl("https://youtube.com")
                    .build();
            videos.add(videoRepository.save(build));
            celebIndex++;
        }
    }

    @PreDestroy
    public void deleteData() {
        videoRepository.deleteAll(videos);
        celebRepository.deleteAll(celebs);
        restaurantImageRepository.deleteAll(restaurantImages);
        restaurantRepository.deleteAll(restaurants);
    }
}
