package com.celuveat.celeb.presentation;

import com.celuveat.celeb.query.dao.FindAllCelebResponseDao;
import com.celuveat.celeb.query.dto.FindAllCelebResponse;
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

    private final FindAllCelebResponseDao findAllCelebResponseDao;

    @GetMapping
    ResponseEntity<List<FindAllCelebResponse>> findAll() {
        List<FindAllCelebResponse> result = findAllCelebResponseDao.findAll();
        return ResponseEntity.ok(result);
    }
}
