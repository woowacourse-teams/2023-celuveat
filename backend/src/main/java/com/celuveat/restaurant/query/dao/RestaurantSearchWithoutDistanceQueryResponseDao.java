package com.celuveat.restaurant.query.dao;

import static com.celuveat.administrativedistrict.domain.QAdministrativeDistrict.administrativeDistrict;
import static com.celuveat.celeb.command.domain.QCeleb.celeb;
import static com.celuveat.restaurant.command.domain.QRestaurant.restaurant;
import static com.celuveat.restaurant.command.domain.QRestaurantImage.restaurantImage;
import static com.celuveat.restaurant.command.domain.QRestaurantLike.restaurantLike;
import static com.celuveat.restaurant.command.domain.QRestaurantRecommendation.restaurantRecommendation;
import static com.celuveat.video.command.domain.QVideo.video;
import static java.lang.Boolean.FALSE;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import com.celuveat.common.dao.Dao;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantImageQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchWithoutDistanceResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.LongSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

@Dao
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantSearchWithoutDistanceQueryResponseDao {

    private final JPAQueryFactory query;

    private final BooleanExpression stContainsCondition = Expressions.booleanTemplate(
            "ST_CONTAINS({0}, {1})",
            administrativeDistrict.polygon,
            restaurant.restaurantPoint.point
    );

    public List<RestaurantSearchWithoutDistanceResponse> findLatest(
            @Nullable Long memberId
    ) {
        List<RestaurantSearchWithoutDistanceResponse> latestRestaurants =
                selectRestaurantSearchWithoutDistanceResponse()
                        .from(restaurant)
                        .orderBy(restaurant.id.desc())
                        .limit(10)
                        .fetch();
        settingCelebAndImageAndLiked(memberId, latestRestaurants);
        return latestRestaurants;
    }

    public Page<RestaurantSearchWithoutDistanceResponse> findByRegionCode(
            RegionCodeCond cond,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        List<RestaurantSearchWithoutDistanceResponse> restaurants = findRestaurantsByRegionCode(cond, pageable);
        settingCelebAndImageAndLiked(memberId, restaurants);
        return PageableExecutionUtils.getPage(restaurants, pageable, totalCountSupplier(cond));
    }

    private List<RestaurantSearchWithoutDistanceResponse> findRestaurantsByRegionCode(
            RegionCodeCond cond,
            Pageable pageable
    ) {
        return selectRestaurantSearchWithoutDistanceResponse()
                .from(restaurant)
                .join(administrativeDistrict).on(administrativeDistrict.code.in(cond.codes))
                .where(stContainsCondition)
                //.where(GeometryExpressions.asGeometry(administrativeDistrict.polygon).contains(restaurant.point)) TODO 이거 살려봤으면 좋겠어.. 이대로 쓰면 =1 이 생성돼서 너무 느려져서 버림..
                .orderBy(restaurant.likeCount.desc(), restaurant.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private void settingCelebAndImageAndLiked(
            Long memberId,
            List<RestaurantSearchWithoutDistanceResponse> restaurants
    ) {
        List<Long> ids = extractIds(restaurants);
        Map<Long, List<CelebQueryResponse>> celebs = findCelebs(ids);
        Map<Long, List<RestaurantImageQueryResponse>> images = findImages(ids);
        Map<Long, Boolean> memberIsLikedRestaurants = findMemberIsLikedRestaurants(ids, memberId);
        for (RestaurantSearchWithoutDistanceResponse restaurant : restaurants) {
            restaurant.setCelebs(celebs.get(restaurant.getId()));
            restaurant.setImages(images.get(restaurant.getId()));
            restaurant.setLiked(memberIsLikedRestaurants.get(restaurant.getId()));
        }
    }

    private List<Long> extractIds(List<RestaurantSearchWithoutDistanceResponse> resultList) {
        return resultList.stream()
                .map(RestaurantSearchWithoutDistanceResponse::getId)
                .toList();
    }

    private Map<Long, List<CelebQueryResponse>> findCelebs(List<Long> restaurantIds) {
        return query.select(Projections.constructor(
                        CelebQueryResponse.class,
                        video.restaurant.id,
                        celeb.id,
                        celeb.name,
                        celeb.youtubeChannelName,
                        celeb.profileImageUrl
                ))
                .from(video)
                .join(video.celeb, celeb)
                .where(video.restaurant.id.in(restaurantIds))
                .fetch()
                .stream()
                .collect(groupingBy(CelebQueryResponse::restaurantId));
    }

    private Map<Long, List<RestaurantImageQueryResponse>> findImages(List<Long> restaurantIds) {
        return query.select(Projections.constructor(
                        RestaurantImageQueryResponse.class,
                        restaurantImage.restaurant.id,
                        restaurantImage.id,
                        restaurantImage.name,
                        restaurantImage.author,
                        restaurantImage.socialMedia
                ))
                .from(restaurantImage)
                .where(restaurantImage.restaurant.id.in(restaurantIds))
                .fetch()
                .stream()
                .collect(groupingBy(RestaurantImageQueryResponse::restaurantId));
    }

    private Map<Long, Boolean> findMemberIsLikedRestaurants(List<Long> ids, @Nullable Long memberId) {
        if (memberId == null) {
            return ids.stream()
                    .collect(toMap(identity(), (ignored) -> FALSE));
        }
        List<Long> memberLikedRestaurantIds = query.select(restaurantLike.restaurant.id)
                .from(restaurantLike)
                .where(
                        restaurantLike.member.id.eq(memberId),
                        restaurantLike.restaurant.id.in(ids)
                ).fetch();
        return ids.stream()
                .collect(toMap(identity(), memberLikedRestaurantIds::contains));
    }

    private LongSupplier totalCountSupplier(RegionCodeCond cond) {
        JPAQuery<Long> countQuery = query.select(restaurant.countDistinct())
                .from(restaurant)
                .join(administrativeDistrict).on(administrativeDistrict.code.in(cond.codes))
                .where(stContainsCondition);
        return countQuery::fetchOne;
    }

    public List<RestaurantSearchWithoutDistanceResponse> findRecommendation(final Long memberId) {
        List<RestaurantSearchWithoutDistanceResponse> recommendRestaurants = selectRestaurantSearchWithoutDistanceResponse()
                .from(restaurantRecommendation)
                .join(restaurantRecommendation).on(restaurantRecommendation.restaurant.id.eq(restaurant.id))
                .fetch();

        settingCelebAndImageAndLiked(memberId, recommendRestaurants);
        return recommendRestaurants;
    }

    private JPAQuery<RestaurantSearchWithoutDistanceResponse> selectRestaurantSearchWithoutDistanceResponse() {
        return query.selectDistinct(Projections.constructor(
                RestaurantSearchWithoutDistanceResponse.class,
                restaurant.id,
                restaurant.name,
                restaurant.category,
                restaurant.roadAddress,
                restaurant.restaurantPoint.latitude,
                restaurant.restaurantPoint.longitude,
                restaurant.phoneNumber,
                restaurant.naverMapUrl,
                restaurant.viewCount,
                restaurant.likeCount,
                restaurant.reviewCount,
                restaurant.totalRating
        ));
    }

    public record RegionCodeCond(
            List<String> codes
    ) {
    }
}
