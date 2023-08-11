package com.celuveat.video.domain;

import static com.celuveat.common.query.DynamicQueryCondition.notNull;

import com.celuveat.common.query.DynamicQuery;
import com.celuveat.common.query.DynamicQueryAssembler;
import com.celuveat.video.application.dto.VideoWithCelebQueryResponse;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoQueryRepository {

    private static final String SELECT_VIDEO = """
            SELECT new com.celuveat.video.application.dto.VideoWithCelebQueryResponse(
                v.id,
                v.youtubeUrl,
                v.uploadDate,
                c.id,
                c.name,
                c.youtubeChannelName,
                c.profileImageUrl
            )
            FROM Video v
            JOIN Celeb c ON c = v.celeb
            JOIN Restaurant r ON r = v.restaurant
            """;
    private static final String COUNT_QUERY = """
            SELECT COUNT(DISTINCT v.id)
            FROM Video v
            JOIN Celeb c ON c = v.celeb
            JOIN Restaurant r ON r = v.restaurant
            """;
    private static final String CELEB_ID_EQUAL = "c.id = %d";
    private static final String RESTAURANT_ID_EQUAL = "r.id = %d";

    private final EntityManager em;

    public Page<VideoWithCelebQueryResponse> getVideosWithCeleb(
            VideoSearchCond videoSearchCond,
            Pageable pageable
    ) {
        String whereQuery = DynamicQueryAssembler.assemble(
                celebIdEqual(videoSearchCond),
                restaurantIdEqual(videoSearchCond)
        );
        List<VideoWithCelebQueryResponse> resultList = em.createQuery(
                        SELECT_VIDEO + whereQuery,
                        VideoWithCelebQueryResponse.class
                )
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return PageableExecutionUtils.getPage(
                resultList,
                pageable,
                () -> (Long) em.createQuery(COUNT_QUERY + whereQuery).getSingleResult()
        );
    }

    private DynamicQuery celebIdEqual(VideoSearchCond videoSearchCond) {
        return DynamicQuery.builder()
                .query(CELEB_ID_EQUAL)
                .params(videoSearchCond.celebId())
                .condition(notNull(videoSearchCond.celebId()))
                .build();
    }

    private DynamicQuery restaurantIdEqual(VideoSearchCond videoSearchCond) {
        return DynamicQuery.builder()
                .query(RESTAURANT_ID_EQUAL)
                .params(videoSearchCond.restaurantId())
                .condition(notNull(videoSearchCond.restaurantId()))
                .build();
    }

    public record VideoSearchCond(
            Long celebId,
            Long restaurantId
    ) {
    }
}
