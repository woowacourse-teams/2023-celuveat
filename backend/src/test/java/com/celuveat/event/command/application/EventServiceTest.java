package com.celuveat.event.command.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.IntegrationTest;
import com.celuveat.event.command.application.dto.SubmitCommand;
import com.celuveat.event.command.domain.EventImage;
import com.celuveat.event.command.domain.EventImageRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("이미지 이벤트 서비스(EventService) 은(는)")
class EventServiceTest extends IntegrationTest {

    @Autowired
    private EventService eventService;
    @Autowired
    private EventImageRepository eventImageRepository;


    @Test
    void 이미지들을_저장한다() {
        // given
        SubmitCommand command = new SubmitCommand("mallang", "또간집", List.of(
                "mallang_또간집_1",
                "mallang_또간집_2",
                "mallang_또간집_3"
        ));

        // when
        eventService.submit(command);

        // then
        List<EventImage> all = eventImageRepository.findAll();
        assertThat(all)
                .extracting(EventImage::instagramId)
                .containsExactly("mallang", "mallang", "mallang");
        assertThat(all)
                .extracting(EventImage::restaurantName)
                .containsExactly("또간집", "또간집", "또간집");
        assertThat(all)
                .extracting(EventImage::imageName)
                .containsExactly("mallang_또간집_1", "mallang_또간집_2", "mallang_또간집_3");
    }
}
