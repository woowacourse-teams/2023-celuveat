package com.celuveat.video.presentation;

import com.celuveat.common.PageResponse;
import com.celuveat.video.presentation.dto.VideoSearchCondRequest;
import com.celuveat.video.query.VideoQueryService;
import com.celuveat.video.query.dto.VideoWithCelebQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private static final int VIDEO_WITH_CELEB_SIZE = 6;

    private final VideoQueryService videoQueryService;

    @GetMapping
    ResponseEntity<PageResponse<VideoWithCelebQueryResponse>> getVideosByRestaurantId(
            @PageableDefault(size = VIDEO_WITH_CELEB_SIZE) Pageable pageable,
            @ModelAttribute VideoSearchCondRequest searchCondRequest
    ) {
        Page<VideoWithCelebQueryResponse> result =
                videoQueryService.findAllVideoWithCeleb(searchCondRequest.toCondition(), pageable);
        return ResponseEntity.ok(PageResponse.from(result));
    }
}
