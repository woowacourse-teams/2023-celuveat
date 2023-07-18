package com.celuveat.restaurant.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.celeb.domain.CelebRepository;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.restaurant.domain.SocialMedia;
import com.celuveat.video.domain.Video;
import com.celuveat.video.domain.VideoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@SpringBootTest
@DisplayName("RestaurantQueryService 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RestaurantQueryServiceTest {

    @Autowired
    private CelebRepository celebRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantImageRepository restaurantImageRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private RestaurantQueryService restaurantQueryService;

    private Celeb 셀럽(String name) {
        return Celeb.builder()
                .name(name)
                .profileImageUrl(name)
                .youtubeChannelName(name)
                .build();
    }

    private Restaurant 음식점(String name) {
        return Restaurant.builder()
                .name(name)
                .roadAddress(name)
                .naverMapUrl(name)
                .phoneNumber(name)
                .category(name)
                .latitude(name)
                .longitude(name)
                .build();
    }

    private RestaurantImage 음식점사진(String name, Restaurant 음식점) {
        return RestaurantImage.builder()
                .name(name)
                .socialMedia(SocialMedia.INSTAGRAM)
                .author(name)
                .restaurant(음식점)
                .build();
    }

    private Video 영상(String name, Restaurant 음식점, Celeb 셀럽) {
        return Video.builder()
                .celeb(셀럽)
                .restaurant(음식점)
                .uploadDate(LocalDate.now())
                .youtubeUrl(name)
                .build();
    }

    @Test
    void 전체_음식점_조회_테스트() {
        // given
        List<Celeb> celebs = celebRepository.saveAll(
                List.of(셀럽("말랑"), 셀럽("도기"), 셀럽("오도"), 셀럽("로이스"))
        );
        Celeb 말랑 = celebs.get(0);
        Celeb 도기 = celebs.get(1);
        Celeb 오도 = celebs.get(2);
        Celeb 로이스 = celebs.get(3);

        List<Restaurant> restaurants = restaurantRepository.saveAll(List.of(
                음식점("말랑1호점"),
                음식점("말랑2호점"),
                음식점("말랑3호점"),
                음식점("도기1호점"),
                음식점("도기2호점"),
                음식점("도기3호점"),
                음식점("오도1호점"),
                음식점("오도2호점"),
                음식점("로이스1호점"),
                음식점("로이스2호점")
        ));

        Restaurant 말랑1호점 = restaurants.get(0);
        Restaurant 말랑2호점 = restaurants.get(1);
        Restaurant 말랑3호점 = restaurants.get(2);
        Restaurant 도기1호점 = restaurants.get(3);
        Restaurant 도기2호점 = restaurants.get(4);
        Restaurant 도기3호점 = restaurants.get(5);
        Restaurant 오도1호점 = restaurants.get(6);
        Restaurant 오도2호점 = restaurants.get(7);
        Restaurant 로이스1호점 = restaurants.get(8);
        Restaurant 로이스2호점 = restaurants.get(9);

        List<RestaurantImage> images = restaurantImageRepository.saveAll(List.of(
                음식점사진("말랑1호점 - 1", 말랑1호점),
                음식점사진("말랑1호점 - 2", 말랑1호점),
                음식점사진("말랑2호점 - 1", 말랑2호점),
                음식점사진("말랑3호점 - 1", 말랑3호점),
                음식점사진("말랑3호점 - 2", 말랑3호점),
                음식점사진("말랑3호점 - 3", 말랑3호점),
                음식점사진("도기1호점 - 1", 도기1호점),
                음식점사진("도기1호점 - 2", 도기1호점),
                음식점사진("도기2호점 - 1", 도기2호점),
                음식점사진("도기2호점 - 2", 도기2호점),
                음식점사진("도기3호점 - 1", 도기3호점),
                음식점사진("도기3호점 - 2", 도기3호점),
                음식점사진("오도1호점 - 1", 오도1호점),
                음식점사진("오도1호점 - 2", 오도1호점),
                음식점사진("오도1호점 - 3", 오도1호점),
                음식점사진("오도2호점 - 1", 오도2호점),
                음식점사진("오도2호점 - 2", 오도2호점),
                음식점사진("로이스1호점 - 1", 로이스1호점),
                음식점사진("로이스2호점 - 1", 로이스2호점),
                음식점사진("로이스2호점 - 2", 로이스2호점)
        ));
        RestaurantImage 말랑1호점_1 = images.get(0);
        RestaurantImage 말랑1호점_2 = images.get(1);
        RestaurantImage 말랑2호점_1 = images.get(2);
        RestaurantImage 말랑3호점_1 = images.get(3);
        RestaurantImage 말랑3호점_2 = images.get(4);
        RestaurantImage 말랑3호점_3 = images.get(5);
        RestaurantImage 도기1호점_1 = images.get(6);
        RestaurantImage 도기1호점_2 = images.get(7);
        RestaurantImage 도기2호점_1 = images.get(8);
        RestaurantImage 도기2호점_2 = images.get(9);
        RestaurantImage 도기3호점_1 = images.get(10);
        RestaurantImage 도기3호점_2 = images.get(11);
        RestaurantImage 오도1호점_1 = images.get(12);
        RestaurantImage 오도1호점_2 = images.get(13);
        RestaurantImage 오도1호점_3 = images.get(14);
        RestaurantImage 오도2호점_1 = images.get(15);
        RestaurantImage 오도2호점_2 = images.get(16);
        RestaurantImage 로이스1호점_1 = images.get(17);
        RestaurantImage 로이스2호점_1 = images.get(18);
        RestaurantImage 로이스2호점_2 = images.get(19);

        videoRepository.saveAll(List.of(
                영상("말랑1호점-말랑", 말랑1호점, 말랑),
                영상("말랑1호점-도기", 말랑1호점, 도기),
                영상("말랑2호점-말랑", 말랑2호점, 말랑),
                영상("말랑3호점-말랑", 말랑3호점, 말랑),
                영상("도기1호점-도기", 도기1호점, 도기),
                영상("도기1호점-오도", 도기1호점, 오도),
                영상("도기1호점-로이스", 도기1호점, 로이스),
                영상("도기2호점-도기", 도기2호점, 도기),
                영상("도기3호점-도기", 도기3호점, 도기),
                영상("도기3호점-오도", 도기3호점, 오도),
                영상("오도1호점-오도", 오도1호점, 오도),
                영상("오도1호점-로이스", 오도1호점, 로이스),
                영상("오도1호점-말랑", 오도1호점, 말랑),
                영상("오도2호점-오도", 오도2호점, 오도),
                영상("로이스1호점-말랑", 로이스1호점, 말랑),
                영상("로이스1호점-도기", 로이스1호점, 도기),
                영상("로이스1호점-오도", 로이스1호점, 오도),
                영상("로이스1호점-로이스", 로이스1호점, 로이스),
                영상("로이스2호점-로이스", 로이스2호점, 로이스)
        ));

        List<RestaurantQueryResponse> expected = List.of(
                RestaurantQueryResponse.from(말랑1호점, List.of(말랑, 도기), List.of(말랑1호점_1, 말랑1호점_2)),
                RestaurantQueryResponse.from(말랑2호점, List.of(말랑), List.of(말랑2호점_1)),
                RestaurantQueryResponse.from(말랑3호점, List.of(말랑), List.of(말랑3호점_1, 말랑3호점_2, 말랑3호점_3)),
                RestaurantQueryResponse.from(도기1호점, List.of(도기, 오도, 로이스), List.of(도기1호점_1, 도기1호점_2)),
                RestaurantQueryResponse.from(도기2호점, List.of(도기), List.of(도기2호점_1, 도기2호점_2)),
                RestaurantQueryResponse.from(도기3호점, List.of(도기, 오도), List.of(도기3호점_1, 도기3호점_2)),
                RestaurantQueryResponse.from(오도1호점, List.of(오도, 로이스, 말랑), List.of(오도1호점_1, 오도1호점_2, 오도1호점_3)),
                RestaurantQueryResponse.from(오도2호점, List.of(오도), List.of(오도2호점_1, 오도2호점_2)),
                RestaurantQueryResponse.from(로이스1호점, List.of(말랑, 도기, 오도, 로이스), List.of(로이스1호점_1)),
                RestaurantQueryResponse.from(로이스2호점, List.of(로이스), List.of(로이스2호점_1, 로이스2호점_2))
        );

        // when
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll();

        // then
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
