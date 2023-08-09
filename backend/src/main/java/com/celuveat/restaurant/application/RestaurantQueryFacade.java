package com.celuveat.restaurant.application;

import com.celuveat.restaurant.application.dto.CelebQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantImageQueryResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantQueryFacade {

    private final RestaurantService restaurantService;
    private final RestaurantQueryService restaurantQueryService;

    @Transactional
    public RestaurantDetailQueryResponse findRestaurantDetailById(
            Long restaurantId,
            Long celebId
    ) {
        restaurantService.increaseViewCount(restaurantId);
        RestaurantDetailQueryResponse response = restaurantQueryService.findRestaurantDetailById(restaurantId);
        CelebQueryResponse targetCeleb = findCeleb(response.celebs(), celebId);
        List<CelebQueryResponse> relocatedCelebs = relocateCelebsByCelebId(targetCeleb, response.celebs());
        List<RestaurantImageQueryResponse> relocatedImages =
                relocateImagesByCelebId(targetCeleb.name(), response.images());
        return RestaurantDetailQueryResponse.from(response, relocatedCelebs, relocatedImages);
    }

    private CelebQueryResponse findCeleb(List<CelebQueryResponse> celebQueryResponses, Long celebId) {
        return celebQueryResponses.stream()
                .filter(celeb -> celeb.id().equals(celebId))
                .findFirst().orElseThrow();
    }

    private List<CelebQueryResponse> relocateCelebsByCelebId(
            CelebQueryResponse targetCeleb,
            List<CelebQueryResponse> celebQueryResponses
    ) {
        List<CelebQueryResponse> celebs = new ArrayList<>(celebQueryResponses);
        Collections.swap(celebs, 0, celebs.indexOf(targetCeleb));
        return celebs;
    }

    private List<RestaurantImageQueryResponse> relocateImagesByCelebId(
            String targetCelebName,
            List<RestaurantImageQueryResponse> imageQueryResponses
    ) {
        List<RestaurantImageQueryResponse> images = new ArrayList<>(imageQueryResponses);
        images.stream()
                .filter(image -> image.author().equals(targetCelebName))
                .findFirst()
                .ifPresent(imageQueryResponse -> Collections.swap(images, 0, images.indexOf(imageQueryResponse)));
        return images;
    }
}
