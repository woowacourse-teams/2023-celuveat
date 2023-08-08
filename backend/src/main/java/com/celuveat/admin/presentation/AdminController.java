package com.celuveat.admin.presentation;

import com.celuveat.admin.application.AdminService;
import com.celuveat.admin.presentation.dto.SaveCelebRequest;
import com.celuveat.admin.presentation.dto.SaveDataRequest;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    static final String TAB = "\t";

    private final AdminService adminService;

    @PostMapping("/data")
    ResponseEntity<Void> saveData(@RequestBody String rawData) {
        String[] rows = rawData.split(System.lineSeparator());
        List<SaveDataRequest> requests = Arrays.stream(rows)
                .map(row -> row.split(TAB))
                .map(SaveDataRequest::from)
                .toList();
        adminService.saveData(requests);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/celebs")
    ResponseEntity<Void> saveCelebs(@RequestBody String rawData) {
        String[] rows = rawData.split(System.lineSeparator());
        List<SaveCelebRequest> requests = Arrays.stream(rows)
                .map(row -> row.split(TAB, -1))
                .map(SaveCelebRequest::new)
                .toList();
        adminService.saveCelebs(requests);
        return ResponseEntity.ok().build();
    }
}
