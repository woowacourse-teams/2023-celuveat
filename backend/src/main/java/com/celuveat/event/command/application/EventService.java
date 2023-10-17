package com.celuveat.event.command.application;

import com.celuveat.event.command.application.dto.SubmitCommand;
import com.celuveat.event.command.domain.EventImage;
import com.celuveat.event.command.domain.EventImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventImageRepository eventImageRepository;

    public void submit(SubmitCommand command) {
        List<EventImage> domains = command.toDomains();
        eventImageRepository.saveAll(domains);
    }
}
