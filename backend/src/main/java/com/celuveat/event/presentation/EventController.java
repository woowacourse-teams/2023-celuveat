package com.celuveat.event.presentation;

import com.celuveat.event.command.application.EventImageNameGenerator;
import com.celuveat.event.command.application.EventImageUploadClient;
import com.celuveat.event.command.application.EventService;
import com.celuveat.event.command.application.dto.SubmitCommand;
import com.celuveat.event.presentation.dto.SubmitEventImageRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventImageUploadClient eventImageUploadClient;

    @PostMapping("/event")
    ResponseEntity<Long> submitImage(
            @ModelAttribute SubmitEventImageRequest request
    ) {
        List<MultipartFile> images = request.images();
        List<String> imageNames = new ArrayList<>();
        for (int i = 1; i <= images.size(); i++) {
            String name = EventImageNameGenerator.generate(request.instagramId(), request.restaurantName(), i);
            imageNames.add(name);
            eventImageUploadClient.upload(name, images.get(i - 1));
        }
        eventService.submit(new SubmitCommand(request.instagramId(), request.restaurantName(), imageNames));
        return ResponseEntity.ok().build();
    }
}
