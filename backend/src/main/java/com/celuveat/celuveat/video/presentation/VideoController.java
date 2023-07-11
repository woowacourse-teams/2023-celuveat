package com.celuveat.celuveat.video.presentation;

import com.celuveat.celuveat.video.application.VideoQueryService;
import com.celuveat.celuveat.video.application.dto.FindAllVideoByRestaurantIdResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoQueryService videoQueryService;

    @GetMapping
    ResponseEntity<List<FindAllVideoByRestaurantIdResponse>> findAllVideoByRestaurantIdResponse(
            @RequestParam(name = "restaurantId", required = true) Long restaurantId
    ) {
        List<FindAllVideoByRestaurantIdResponse> result = videoQueryService.findAllByRestaurantId(restaurantId);
        return ResponseEntity.ok(result);
    }
}
