package com.celuveat.restaurant.application.mapper;

import com.celuveat.restaurant.application.dto.CelebQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.application.dto.RestaurantImageQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantSimpleResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public class RestaurantRelocator {

    public static RestaurantDetailResponse relocateCelebDataFirstByCelebId(
            Long celebId, RestaurantDetailResponse response
    ) {
        CelebQueryResponse targetCeleb = findCelebById(celebId, response.celebs());
        List<CelebQueryResponse> relocatedCelebs = relocateTargetToFirst(targetCeleb, response.celebs());
        List<RestaurantImageQueryResponse> relocatedImages =
                relocateImageToFirstByCeleb(targetCeleb, response.images());
        return RestaurantDetailResponse.of(response, relocatedCelebs, relocatedImages);
    }

    private static CelebQueryResponse findCelebById(Long celebId, List<CelebQueryResponse> celebQueryResponses) {
        return celebQueryResponses.stream()
                .filter(celeb -> celeb.id().equals(celebId))
                .findFirst()
                .orElseThrow();
    }

    private static <T> List<T> relocateTargetToFirst(T target, List<T> list) {
        if (!list.contains(target)) {
            return list;
        }
        List<T> result = new ArrayList<>(list);
        Collections.swap(result, 0, result.indexOf(target));
        return result;
    }

    private static List<RestaurantImageQueryResponse> relocateImageToFirstByCeleb(
            CelebQueryResponse targetCeleb,
            List<RestaurantImageQueryResponse> images
    ) {
        return findImageByCeleb(targetCeleb, images)
                .map(targetImage -> relocateTargetToFirst(targetImage, images))
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

    public static Page<RestaurantSimpleResponse> relocateCelebDataFirstInResponsesByCelebId(
            Long celebId, Page<RestaurantSimpleResponse> result
    ) {
        return result.map(it -> relocatedCelebDataFirstResponseByCelebId(celebId, it));
    }

    private static RestaurantSimpleResponse relocatedCelebDataFirstResponseByCelebId(
            Long celebId, RestaurantSimpleResponse response
    ) {
        CelebQueryResponse targetCeleb = findCelebById(celebId, response.celebs());
        List<CelebQueryResponse> relocatedCelebs = relocateTargetToFirst(targetCeleb, response.celebs());
        List<RestaurantImageQueryResponse> relocatedImages =
                relocateImageToFirstByCeleb(targetCeleb, response.images());
        return RestaurantSimpleResponse.of(response, relocatedCelebs, relocatedImages);
    }
}
