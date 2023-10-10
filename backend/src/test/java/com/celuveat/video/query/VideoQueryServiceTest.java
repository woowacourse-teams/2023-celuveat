package com.celuveat.video.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.celuveat.video.query.dao.VideoQueryResponseDao;
import com.celuveat.video.query.dao.VideoQueryResponseDao.VideoSearchCond;
import com.celuveat.video.query.dto.VideoQueryResponse;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("영상 조회용 서비스(VideoQueryService) 은(는)")
class VideoQueryServiceTest {

    private final VideoQueryResponseDao videoQueryResponseDao = mock(VideoQueryResponseDao.class);
    private final VideoQueryService videoQueryService = new VideoQueryService(videoQueryResponseDao);

    @Test
    void 음식점_영상들을_조건에_따라_조회한다() {
        // given
        VideoSearchCond videoSearchCond = new VideoSearchCond(null, null);
        PageRequest pageRequest = PageRequest.of(0, 18);
        List<VideoQueryResponse> response = List.of(
                new VideoQueryResponse(
                        1L,
                        "key1",
                        LocalDate.now(),
                        1L,
                        "히밥",
                        "@히밥",
                        "https://히밥.profile.com"
                ),
                new VideoQueryResponse(
                        2L,
                        "key2",
                        LocalDate.now(),
                        2L,
                        "성시경",
                        "@성시경",
                        "https://성시경.profile.com"
                )
        );
        given(videoQueryService.findAllVideoWithCeleb(videoSearchCond, pageRequest))
                .willReturn(PageableExecutionUtils.getPage(response, pageRequest, () -> 2));

        // when
        List<VideoQueryResponse> result = videoQueryService
                .findAllVideoWithCeleb(videoSearchCond, pageRequest)
                .getContent();

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(response);
    }
}
