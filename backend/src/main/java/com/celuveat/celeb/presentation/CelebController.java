package com.celuveat.celeb.presentation;

import com.celuveat.celeb.query.CelebQueryService;
import com.celuveat.celeb.query.dto.CelebQueryResponse;
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
    ResponseEntity<List<CelebQueryResponse>> find() {
        List<CelebQueryResponse> result = celebQueryService.find();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/test")
    ResponseEntity<Void> test() throws InterruptedException {
        Thread.sleep(10000);
        return ResponseEntity.ok().build();
    }
}
