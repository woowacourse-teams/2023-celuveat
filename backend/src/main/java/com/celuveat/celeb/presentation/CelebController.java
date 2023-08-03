package com.celuveat.celeb.presentation;

import com.celuveat.celeb.domain.CelebRepository;
import com.celuveat.celeb.presentation.response.FindAllCelebResponse;
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

    private final CelebRepository celebRepository;

    @GetMapping
    ResponseEntity<List<FindAllCelebResponse>> findAll() {
        List<FindAllCelebResponse> result = celebRepository.findAll()
                .stream()
                .map(FindAllCelebResponse::from)
                .toList();
        return ResponseEntity.ok(result);
    }
}
