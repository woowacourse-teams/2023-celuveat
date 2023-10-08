package com.celuveat.restaurant.util;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.common.util.Base64Util;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantImageQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchQueryResponse;
import java.util.List;
import org.springframework.data.util.Pair;

public class RestaurantQueryTestUtils {

    public static RestaurantDetailQueryResponse restaurantDetailQueryResponse(
            Restaurant 음식점,
            boolean 좋아요_눌렀는지_여부, double 평점,
            List<Celeb> 셀럽들, List<RestaurantImage> 음식점_사진들
    ) {
        return new RestaurantDetailQueryResponse(
                음식점.id(),
                음식점.name(),
                음식점.category(),
                음식점.superCategory(),
                음식점.roadAddress(),
                음식점.latitude(),
                음식점.longitude(),
                음식점.phoneNumber(),
                음식점.naverMapUrl(),
                음식점.likeCount(),
                음식점.viewCount(),
                좋아요_눌렀는지_여부,
                평점,
                celebQueryResponses(음식점, 셀럽들),
                restaurantImageQueryResponses(음식점, 음식점_사진들)
        );
    }

    public static RestaurantSearchQueryResponse restaurantSearchQueryResponse(
            Restaurant 음식점,
            boolean 좋아요_눌렀는지_여부, double 평점,
            List<Celeb> 셀럽들, List<RestaurantImage> 음식점_사진들
    ) {
        return new RestaurantSearchQueryResponse(
                음식점.id(),
                음식점.name(),
                음식점.category(),
                음식점.superCategory(),
                음식점.roadAddress(),
                음식점.latitude(),
                음식점.longitude(),
                음식점.phoneNumber(),
                음식점.naverMapUrl(),
                음식점.viewCount(),
                음식점.likeCount(),
                좋아요_눌렀는지_여부,
                평점,
                0,  // 거리
                celebQueryResponses(음식점, 셀럽들),
                restaurantImageQueryResponses(음식점, 음식점_사진들)
        );
    }

    public static List<CelebQueryResponse> celebQueryResponses(Restaurant 음식점, List<Celeb> 셀럽들) {
        return 셀럽들.stream()
                .map(셀럽 -> new CelebQueryResponse(
                        음식점.id(),
                        셀럽.id(),
                        셀럽.name(),
                        셀럽.youtubeChannelName(),
                        셀럽.profileImageUrl())
                ).toList();
    }

    public static List<RestaurantImageQueryResponse> restaurantImageQueryResponses(
            Restaurant 음식점,
            List<RestaurantImage> 사진들
    ) {
        return 사진들.stream()
                .map(사진 -> new RestaurantImageQueryResponse(
                        음식점.id(),
                        사진.id(),
                        Base64Util.encode(사진.name()),
                        사진.author(),
                        사진.socialMedia().name())
                ).toList();
    }

    public static Pair<Double, Double> 일정_거리내_위경도(double 위도, double 경도, int 거리) {
        double latitudeChange = (double) 거리 / 111320.0;
        double longitudeChange = (double) 거리 / (111320.0 * Math.cos(Math.toRadians(위도)));
        double newLatitude = 위도 + latitudeChange * 0.7;
        double newLongitude = 경도 + longitudeChange * 0.7;
        return Pair.of(newLatitude, newLongitude);
    }
}
