package com.celuveat.celuveat.celeb.presentation;

import com.celuveat.celuveat.celeb.application.CelebQueryService;
import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/celebs")
public class CelebController {

    private final CelebQueryService celebQueryService;

    @GetMapping
    ResponseEntity<List<FindAllCelebResponse>> findAll() {
        List<FindAllCelebResponse> result = celebQueryService.findAll();
        return ResponseEntity.ok(result);
    }
}
