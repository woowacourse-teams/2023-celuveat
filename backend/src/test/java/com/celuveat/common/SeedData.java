package com.celuveat.common;

import static com.celuveat.celeb.fixture.CelebFixture.셀럽;
import static com.celuveat.common.util.StringUtil.removeAllBlank;
import static com.celuveat.restaurant.fixture.LocationFixture.지점1;
import static com.celuveat.restaurant.fixture.LocationFixture.지점2;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.음식점사진;
import static com.celuveat.video.fixture.VideoFixture.영상;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.celeb.command.domain.CelebRepository;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantImageRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantImageQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchQueryResponse;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.command.domain.VideoRepository;
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

    public List<RestaurantSearchQueryResponse> insertSeedData() {
        var 말랑 = celebRepository.save(셀럽("말랑"));
        var 도기 = celebRepository.save(셀럽("도기"));
        var 오도 = celebRepository.save(셀럽("오도"));
        var 로이스 = celebRepository.save(셀럽("로이스"));

        var 말랑1호점 = restaurantRepository.save(음식점("말랑1호점", 지점1.latitude(), 지점1.longitude()));
        var 말랑2호점 = restaurantRepository.save(음식점("말랑2호점", 지점1.latitude(), 지점2.longitude()));
        var 말랑3호점 = restaurantRepository.save(음식점("말랑3호점", 지점2.latitude(), 지점2.longitude()));
        var 도기1호점 = restaurantRepository.save(음식점("도기1호점", 지점1.latitude(), 지점1.longitude()));
        var 도기2호점 = restaurantRepository.save(음식점("도기2호점", 지점2.latitude(), 지점2.longitude()));
        var 도기3호점 = restaurantRepository.save(음식점("도기3호점", 지점2.latitude(), 지점2.longitude()));
        var 오도1호점 = restaurantRepository.save(음식점("오도1호점", 지점1.latitude(), 지점1.longitude()));
        var 오도2호점 = restaurantRepository.save(음식점("오도2호점", 지점2.latitude(), 지점2.longitude()));
        var 로이스1호점 = restaurantRepository.save(음식점("로이스1호점", 지점1.latitude(), 지점1.longitude()));
        var 로이스2호점 = restaurantRepository.save(음식점("로이스2호점", 지점2.latitude(), 지점2.longitude()));

        var 말랑1호점_1 = restaurantImageRepository.save(음식점사진("말랑1호점 - 1", 말랑1호점, "로이스"));
        var 말랑1호점_2 = restaurantImageRepository.save(음식점사진("말랑1호점 - 2", 말랑1호점, "말랑"));
        var 말랑1호점_3 = restaurantImageRepository.save(음식점사진("말랑1호점 - 3", 말랑1호점, "오도"));
        var 말랑2호점_1 = restaurantImageRepository.save(음식점사진("말랑2호점 - 1", 말랑2호점, "말랑"));
        var 말랑3호점_1 = restaurantImageRepository.save(음식점사진("말랑3호점 - 1", 말랑3호점, "말랑"));
        var 말랑3호점_2 = restaurantImageRepository.save(음식점사진("말랑3호점 - 2", 말랑3호점, "말랑"));
        var 말랑3호점_3 = restaurantImageRepository.save(음식점사진("말랑3호점 - 3", 말랑3호점, "말랑"));
        var 도기1호점_1 = restaurantImageRepository.save(음식점사진("도기1호점 - 1", 도기1호점, "도기"));
        var 도기1호점_2 = restaurantImageRepository.save(음식점사진("도기1호점 - 2", 도기1호점, "오도"));
        var 도기2호점_1 = restaurantImageRepository.save(음식점사진("도기2호점 - 1", 도기2호점, "도기"));
        var 도기2호점_2 = restaurantImageRepository.save(음식점사진("도기2호점 - 2", 도기2호점, "도기"));
        var 도기3호점_1 = restaurantImageRepository.save(음식점사진("도기3호점 - 1", 도기3호점, "도기"));
        var 도기3호점_2 = restaurantImageRepository.save(음식점사진("도기3호점 - 2", 도기3호점, "오도"));
        var 오도1호점_1 = restaurantImageRepository.save(음식점사진("오도1호점 - 1", 오도1호점, "말랑"));
        var 오도1호점_2 = restaurantImageRepository.save(음식점사진("오도1호점 - 2", 오도1호점, "도기"));
        var 오도1호점_3 = restaurantImageRepository.save(음식점사진("오도1호점 - 3", 오도1호점, "오도"));
        var 오도2호점_1 = restaurantImageRepository.save(음식점사진("오도2호점 - 1", 오도2호점, "오도"));
        var 오도2호점_2 = restaurantImageRepository.save(음식점사진("오도2호점 - 2", 오도2호점, "오도"));
        var 로이스1호점_1 = restaurantImageRepository.save(음식점사진("로이스1호점 - 1", 로이스1호점, "로이스"));
        var 로이스2호점_1 = restaurantImageRepository.save(음식점사진("로이스2호점 - 1", 로이스2호점, "오도"));
        var 로이스2호점_2 = restaurantImageRepository.save(음식점사진("로이스2호점 - 2", 로이스2호점, "로이스"));

        videoRepository.saveAll(List.of(
                영상("말랑1호점-말랑", 말랑1호점, 말랑),
                영상("말랑1호점-도기", 말랑1호점, 도기),
                영상("말랑1호점-로이스", 말랑1호점, 로이스),
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
                toRestaurantSearchResponse(말랑1호점, 12.3, List.of(말랑, 도기, 로이스), List.of(말랑1호점_1, 말랑1호점_2, 말랑1호점_3)),
                toRestaurantSearchResponse(말랑2호점, 9.3, List.of(말랑), List.of(말랑2호점_1)),
                toRestaurantSearchResponse(말랑3호점, 4.2, List.of(말랑), List.of(말랑3호점_1, 말랑3호점_2, 말랑3호점_3)),
                toRestaurantSearchResponse(도기1호점, 121.3, List.of(도기, 오도, 로이스), List.of(도기1호점_1, 도기1호점_2)),
                toRestaurantSearchResponse(도기2호점, 2.3, List.of(도기), List.of(도기2호점_1, 도기2호점_2)),
                toRestaurantSearchResponse(도기3호점, 12.1152, List.of(도기, 오도), List.of(도기3호점_1, 도기3호점_2)),
                toRestaurantSearchResponse(오도1호점, 2.34, List.of(오도, 로이스, 말랑), List.of(오도1호점_1, 오도1호점_2, 오도1호점_3)),
                toRestaurantSearchResponse(오도2호점, 1123.3, List.of(오도), List.of(오도2호점_1, 오도2호점_2)),
                toRestaurantSearchResponse(로이스1호점, 11112.3, List.of(말랑, 도기, 오도, 로이스), List.of(로이스1호점_1)),
                toRestaurantSearchResponse(로이스2호점, 1852.4, List.of(로이스), List.of(로이스2호점_1, 로이스2호점_2))
        );
    }

    private RestaurantSearchQueryResponse toRestaurantSearchResponse(
            Restaurant restaurant,
            Double distance,
            List<Celeb> celebs,
            List<RestaurantImage> restaurantImages
    ) {
        RestaurantSearchQueryResponse response = new RestaurantSearchQueryResponse(
                restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.superCategory(),
                restaurant.roadAddress(),
                restaurant.latitude(),
                restaurant.longitude(),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                restaurant.viewCount(),
                distance,
                restaurant.likeCount(),
                restaurant.averageRating()
        );
        List<CelebQueryResponse> celebResponses = celebs.stream()
                .map(it -> CelebQueryResponse.from(restaurant.id(), it))
                .toList();
        List<RestaurantImageQueryResponse> imageResponse = restaurantImages.stream()
                .map(RestaurantImageQueryResponse::of)
                .toList();
        response.setCelebs(celebResponses);
        response.setImages(imageResponse);
        return response;
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
                        영상("ww.comA?v=absa", 로이스1호점, 로이스),
                        영상("ww.comB?v=adBwssa", 로이스1호점, 도기),
                        영상("ww.comC?v=adsbs12a", 로이스2호점, 말랑),
                        영상("ww.comD?v=sbsa", 로이스2호점, 오도)
                )
        );
    }

    public static boolean isCelebVisited(Long celebId, RestaurantSearchQueryResponse RestaurantSearchQueryResponse) {
        List<Long> celebIds = RestaurantSearchQueryResponse.celebs()
                .stream()
                .map(CelebQueryResponse::id)
                .toList();
        return celebIds.contains(celebId);
    }

    public static boolean isSameCategory(String category, RestaurantSearchQueryResponse RestaurantSearchQueryResponse) {
        return RestaurantSearchQueryResponse.superCategory().equals(category);
    }

    public static boolean isContainsRestaurantName(
            String restaurantName,
            RestaurantSearchQueryResponse RestaurantSearchQueryResponse
    ) {
        return RestaurantSearchQueryResponse.name().contains(removeAllBlank(restaurantName));
    }

    public static boolean isRestaurantInArea(
            LocationSearchCond locationSearchCond,
            RestaurantSearchQueryResponse restaurantWithCelebsAndImagesSimpleResponse
    ) {
        return locationSearchCond.lowLatitude() <= restaurantWithCelebsAndImagesSimpleResponse.latitude()
                && restaurantWithCelebsAndImagesSimpleResponse.latitude() <= locationSearchCond.highLatitude()
                && locationSearchCond.lowLongitude() <= restaurantWithCelebsAndImagesSimpleResponse.longitude()
                && restaurantWithCelebsAndImagesSimpleResponse.longitude() <= locationSearchCond.highLongitude();
    }

    public static List<String> 이름_추출(List<RestaurantSearchQueryResponse> responses) {
        return responses.stream()
                .map(RestaurantSearchQueryResponse::name)
                .toList();
    }
}
