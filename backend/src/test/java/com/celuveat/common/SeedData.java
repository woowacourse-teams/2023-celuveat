package com.celuveat.common;

import static com.celuveat.celeb.fixture.CelebFixture.셀럽;
import static com.celuveat.restaurant.fixture.LocationFixture.지점1;
import static com.celuveat.restaurant.fixture.LocationFixture.지점2;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.음식점사진;
import static com.celuveat.video.fixture.VideoFixture.영상;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.celeb.domain.CelebRepository;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.restaurant.domain.dto.RestaurantWithDistance;
import com.celuveat.video.domain.Video;
import com.celuveat.video.domain.VideoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
@RequiredArgsConstructor
public class SeedData {

    private final CelebRepository celebRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final VideoRepository videoRepository;

    public List<RestaurantQueryResponse> insertSeedData() {
        List<Celeb> celebs = celebRepository.saveAll(
                List.of(셀럽("말랑"), 셀럽("도기"), 셀럽("오도"), 셀럽("로이스"))
        );
        Celeb 말랑 = celebs.get(0);
        Celeb 도기 = celebs.get(1);
        Celeb 오도 = celebs.get(2);
        Celeb 로이스 = celebs.get(3);

        List<Restaurant> restaurants = restaurantRepository.saveAll(List.of(
                음식점("말랑1호점", 지점1.latitude(), 지점1.longitude()),
                음식점("말랑2호점", 지점1.latitude(), 지점2.longitude()),
                음식점("말랑3호점", 지점2.latitude(), 지점2.longitude()),
                음식점("도기1호점", 지점1.latitude(), 지점1.longitude()),
                음식점("도기2호점", 지점2.latitude(), 지점2.longitude()),
                음식점("도기3호점", 지점2.latitude(), 지점2.longitude()),
                음식점("오도1호점", 지점1.latitude(), 지점1.longitude()),
                음식점("오도2호점", 지점2.latitude(), 지점2.longitude()),
                음식점("로이스1호점", 지점1.latitude(), 지점1.longitude()),
                음식점("로이스2호점", 지점2.latitude(), 지점2.longitude())
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
                음식점사진("말랑1호점 - 1", 말랑1호점, "말랑"),
                음식점사진("말랑1호점 - 2", 말랑1호점, "말랑"),
                음식점사진("말랑2호점 - 1", 말랑2호점, "말랑"),
                음식점사진("말랑3호점 - 1", 말랑3호점, "말랑"),
                음식점사진("말랑3호점 - 2", 말랑3호점, "말랑"),
                음식점사진("말랑3호점 - 3", 말랑3호점, "말랑"),
                음식점사진("도기1호점 - 1", 도기1호점, "도기"),
                음식점사진("도기1호점 - 2", 도기1호점, "오도"),
                음식점사진("도기2호점 - 1", 도기2호점, "도기"),
                음식점사진("도기2호점 - 2", 도기2호점, "도기"),
                음식점사진("도기3호점 - 1", 도기3호점, "도기"),
                음식점사진("도기3호점 - 2", 도기3호점, "오도"),
                음식점사진("오도1호점 - 1", 오도1호점, "말랑"),
                음식점사진("오도1호점 - 2", 오도1호점, "도기"),
                음식점사진("오도1호점 - 3", 오도1호점, "오도"),
                음식점사진("오도2호점 - 1", 오도2호점, "오도"),
                음식점사진("오도2호점 - 2", 오도2호점, "오도"),
                음식점사진("로이스1호점 - 1", 로이스1호점, "로이스"),
                음식점사진("로이스2호점 - 1", 로이스2호점, "오도"),
                음식점사진("로이스2호점 - 2", 로이스2호점, "로이스")
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

        return List.of(
                RestaurantQueryResponse.from(
                        withDistance(말랑1호점, 12.3), List.of(말랑, 도기), List.of(말랑1호점_1, 말랑1호점_2), false, 0
                ),
                RestaurantQueryResponse.from(
                        withDistance(말랑2호점, 9.3), List.of(말랑), List.of(말랑2호점_1), false, 0
                ),
                RestaurantQueryResponse.from(
                        withDistance(말랑3호점, 4.2), List.of(말랑), List.of(말랑3호점_1, 말랑3호점_2, 말랑3호점_3), false, 0
                ),
                RestaurantQueryResponse.from(
                        withDistance(도기1호점, 121.3), List.of(도기, 오도, 로이스), List.of(도기1호점_1, 도기1호점_2), false, 0
                ),
                RestaurantQueryResponse.from(
                        withDistance(도기2호점, 2.3), List.of(도기), List.of(도기2호점_1, 도기2호점_2), false, 0
                ),
                RestaurantQueryResponse.from(
                        withDistance(도기3호점, 12.1152), List.of(도기, 오도), List.of(도기3호점_1, 도기3호점_2), false, 0
                ),
                RestaurantQueryResponse.from(
                        withDistance(오도1호점, 2.34), List.of(오도, 로이스, 말랑), List.of(오도1호점_1, 오도1호점_2, 오도1호점_3), false, 0
                ),
                RestaurantQueryResponse.from(
                        withDistance(오도2호점, 1123.3), List.of(오도), List.of(오도2호점_1, 오도2호점_2), false, 0
                ),
                RestaurantQueryResponse.from(
                        withDistance(로이스1호점, 11112.3), List.of(말랑, 도기, 오도, 로이스), List.of(로이스1호점_1), false, 0
                ),
                RestaurantQueryResponse.from(
                        withDistance(로이스2호점, 1852.4), List.of(로이스), List.of(로이스2호점_1, 로이스2호점_2), false, 0
                )
        );
    }

    private RestaurantWithDistance withDistance(Restaurant restaurant, Double distance) {
        return new RestaurantWithDistance(
                restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.roadAddress(),
                restaurant.latitude(),
                restaurant.longitude(),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                restaurant.viewCount(),
                distance
        );
    }

    public List<Video> insertVideoSeedData() {
        Restaurant 로이스1호점 = restaurantRepository.save(음식점("로이스1호점"));
        Restaurant 로이스2호점 = restaurantRepository.save(음식점("로이스2호점"));
        Celeb 로이스 = 셀럽("로이스");
        Celeb 도기 = 셀럽("도기");
        Celeb 말랑 = 셀럽("말랑");
        Celeb 오도 = 셀럽("오도");
        celebRepository.saveAll(List.of(로이스, 도기, 말랑, 오도));
        return videoRepository.saveAll(List.of(
                        영상("ww.comA", 로이스1호점, 로이스),
                        영상("ww.comB", 로이스1호점, 도기),
                        영상("ww.comC", 로이스2호점, 말랑),
                        영상("ww.comD", 로이스2호점, 오도)
                )
        );
    }
}
