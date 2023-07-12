package com.celuveat.celuveat.admin.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.celuveat.celuveat.admin.presentation.dto.RegisterCelebRequest;
import com.celuveat.celuveat.celeb.application.CelebService;
import com.celuveat.celuveat.celeb.application.dto.RegisterCelebCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/celebs")
public class AdminCelebController {

    private final CelebService celebService;

    @PostMapping
    ResponseEntity<Void> register(@RequestBody RegisterCelebRequest request) {
        RegisterCelebCommand command = request.toCommand();
        celebService.register(command);
        return ResponseEntity.status(CREATED).build();
    }
}
