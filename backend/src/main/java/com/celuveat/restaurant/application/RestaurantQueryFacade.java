package com.celuveat.restaurant.application;

import com.celuveat.restaurant.application.dto.CelebQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantImageQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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

    @Transactional(readOnly = true)
    public Page<RestaurantQueryResponse> findAll(
            RestaurantSearchCond restaurantSearchCond,
            LocationSearchCond locationSearchCond,
            Pageable pageable,
            Optional<Long> memberId
    ) {
        Page<RestaurantQueryResponse> restaurantQueryResponse = memberId.map(
                id -> restaurantQueryService.findAllWithMemberId(
                        restaurantSearchCond,
                        locationSearchCond,
                        pageable,
                        id
                )).orElseGet(() -> restaurantQueryService.findAll(restaurantSearchCond, locationSearchCond, pageable));

        return Optional.ofNullable(restaurantSearchCond.celebId())
                .map(celebId -> relocateByCelebId(restaurantQueryResponse, celebId))
                .orElse(restaurantQueryResponse);
    }

    private Page<RestaurantQueryResponse> relocateByCelebId(Page<RestaurantQueryResponse> result, Long celebId) {
        List<RestaurantQueryResponse> content = new ArrayList<>(result.getContent());
        List<RestaurantQueryResponse> orderedContent = content.stream()
                .map(response -> relocateResponseByCelebId(response, celebId))
                .toList();
        return PageableExecutionUtils.getPage(orderedContent, result.getPageable(), result::getTotalElements);
    }

    private RestaurantQueryResponse relocateResponseByCelebId(RestaurantQueryResponse response, Long celebId) {
        List<CelebQueryResponse> celebs = response.celebs();
        CelebQueryResponse targetCeleb = findCeleb(celebs, celebId);
        List<CelebQueryResponse> relocatedCelebs = relocateCelebsByCelebId(targetCeleb, celebs);
        List<RestaurantImageQueryResponse> relocatedImages =
                relocateImagesByCelebId(targetCeleb.name(), response.images());
        return RestaurantQueryResponse.from(response, relocatedCelebs, relocatedImages);
    }
}
