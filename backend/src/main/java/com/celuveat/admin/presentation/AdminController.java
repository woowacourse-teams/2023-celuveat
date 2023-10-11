package com.celuveat.admin.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.celuveat.admin.command.application.AdminService;
import com.celuveat.admin.presentation.dto.SaveCelebRequest;
import com.celuveat.admin.presentation.dto.SaveDataRequest;
import com.celuveat.admin.presentation.dto.SaveImageRequest;
import com.celuveat.common.client.ImageUploadClient;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("!prod")
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    static final String TAB = "\t";

    private final ImageUploadClient imageUploadClient;
    private final AdminService adminService;

    @PostMapping("/data")
    ResponseEntity<Void> saveData(@RequestBody String rawData) {
        List<SaveDataRequest> request = toRequest(rawData, SaveDataRequest::from);
        adminService.saveData(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/celebs")
    ResponseEntity<Void> saveCelebs(@RequestBody String rawData) {
        List<SaveCelebRequest> request = toRequest(rawData, SaveCelebRequest::new);
        adminService.saveCelebs(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restaurant-images")
    ResponseEntity<Void> uploadImage(@ModelAttribute SaveImageRequest request) {
        imageUploadClient.upload(request.image());
        adminService.saveImage(request.toCommand());
        return ResponseEntity.status(CREATED).build();
    }

    private <T> List<T> toRequest(String rawData, Function<String[], T> function) {
        String[] rows = rawData.split(System.lineSeparator());
        return Arrays.stream(rows)
                .map(row -> row.split(TAB, -1))
                .map(function)
                .toList();
    }
}
