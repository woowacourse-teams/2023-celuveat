package com.celuveat.video.query.dao;

import static com.celuveat.celeb.command.domain.QCeleb.celeb;
import static com.celuveat.restaurant.command.domain.QRestaurant.restaurant;
import static com.celuveat.video.command.domain.QVideo.video;

import com.celuveat.video.query.dto.VideoQueryResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
public class VideoQueryResponseDao {

    private final JPAQueryFactory query;

    public Page<VideoQueryResponse> find(
            VideoSearchCond videoSearchCond,
            Pageable pageable
    ) {
        List<VideoQueryResponse> resultList = query.selectDistinct(
                        Projections.constructor(VideoQueryResponse.class,
                                video.id,
                                video.youtubeUrl,
                                video.uploadDate,
                                celeb.id,
                                celeb.name,
                                celeb.youtubeChannelName,
                                celeb.profileImageUrl
                        ))
                .from(video)
                .join(celeb).on(celeb.eq(video.celeb))
                .where(
                        celebIdEqual(videoSearchCond.celebId),
                        restaurantIdEqual(videoSearchCond.restaurantId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query.select(video.countDistinct())
                .from(video)
                .join(celeb).on(celeb.eq(video.celeb))
                .where(
                        celebIdEqual(videoSearchCond.celebId),
                        restaurantIdEqual(videoSearchCond.restaurantId)
                );

        return PageableExecutionUtils.getPage(resultList, pageable, countQuery::fetchOne);
    }

    private BooleanExpression celebIdEqual(Long celebId) {
        if (celebId == null) {
            return null;
        }
        return celeb.id.eq(celebId);
    }

    private BooleanExpression restaurantIdEqual(Long restaurantId) {
        if (restaurantId == null) {
            return null;
        }
        return restaurant.id.eq(restaurantId);
    }

    public record VideoSearchCond(
            Long celebId,
            Long restaurantId
    ) {
    }
}
