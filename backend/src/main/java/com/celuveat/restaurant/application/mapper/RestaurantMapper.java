package com.celuveat.restaurant.application.mapper;

import com.celuveat.common.util.ListUtil;
import com.celuveat.restaurant.application.dto.CelebQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantImageQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public class RestaurantMapper {

    public static RestaurantDetailQueryResponse moveCelebDataFirstByCelebId(
            Long celebId, RestaurantDetailQueryResponse response
    ) {
        CelebQueryResponse targetCeleb = findCelebById(celebId, response.celebs());
        List<CelebQueryResponse> relocatedCelebs = ListUtil.moveTargetToFirst(targetCeleb, response.celebs());
        List<RestaurantImageQueryResponse> relocatedImages = moveImageToFirstByCeleb(targetCeleb, response.images());
        return RestaurantDetailQueryResponse.of(response, relocatedCelebs, relocatedImages);
    }

    private static CelebQueryResponse findCelebById(Long celebId, List<CelebQueryResponse> celebQueryResponses) {
        return celebQueryResponses.stream()
                .filter(celeb -> celeb.id().equals(celebId))
                .findFirst()
                .orElseThrow();
    }

    private static List<RestaurantImageQueryResponse> moveImageToFirstByCeleb(
            CelebQueryResponse targetCeleb,
            List<RestaurantImageQueryResponse> images
    ) {
        return findImageByCeleb(targetCeleb, images)
                .map(targetImage -> ListUtil.moveTargetToFirst(targetImage, images))
                .orElse(images);
    }

    private static Optional<RestaurantImageQueryResponse> findImageByCeleb(
            CelebQueryResponse targetCeleb,
            List<RestaurantImageQueryResponse> images
    ) {
        return images.stream()
                .filter(image -> image.author().equals(targetCeleb.name()))
                .findAny();
    }

    public static Page<RestaurantQueryResponse> movedCelebDataFirstResponsesByCelebId(
            Page<RestaurantQueryResponse> result, Long celebId
    ) {
        return result.map(it -> movedCelebDataFirstResponseByCelebId(celebId, it));
    }

    private static RestaurantQueryResponse movedCelebDataFirstResponseByCelebId(
            Long celebId, RestaurantQueryResponse response
    ) {
        CelebQueryResponse targetCeleb = findCelebById(celebId, response.celebs());
        List<CelebQueryResponse> relocatedCelebs = ListUtil.moveTargetToFirst(targetCeleb, response.celebs());
        List<RestaurantImageQueryResponse> relocatedImages = moveImageToFirstByCeleb(targetCeleb, response.images());
        return RestaurantQueryResponse.of(response, relocatedCelebs, relocatedImages);
    }
}
