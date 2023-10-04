package com.celuveat.restaurant.query.dao;

import static com.celuveat.administrativedistrict.domain.QAdministrativeDistrict.administrativeDistrict;
import static com.celuveat.celeb.command.domain.QCeleb.celeb;
import static com.celuveat.restaurant.command.domain.QRestaurant.restaurant;
import static com.celuveat.restaurant.command.domain.QRestaurantImage.restaurantImage;
import static com.celuveat.restaurant.command.domain.QRestaurantLike.restaurantLike;
import static com.celuveat.video.command.domain.QVideo.video;
import static java.lang.Boolean.FALSE;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import com.celuveat.restaurant.query.dto.RestaurantByAddressResponse;
import com.celuveat.restaurant.query.dto.RestaurantByAddressResponse.CelebInfo;
import com.celuveat.restaurant.query.dto.RestaurantByAddressResponse.RestaurantImageInfo;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantByAddressResponseDao {

    private final JPAQueryFactory query;

    private final BooleanExpression stContainsCondition = Expressions.booleanTemplate(
            "ST_CONTAINS({0}, {1})",
            administrativeDistrict.polygon,
            restaurant.point
    );

    public Page<RestaurantByAddressResponse> find(
            DistrictCodeCond cond,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        List<RestaurantByAddressResponse> resultList = findRestaurants(cond, pageable);
        List<Long> ids = extractIds(resultList);
        Map<Long, List<CelebInfo>> celebs = findCelebs(ids);
        Map<Long, List<RestaurantImageInfo>> images = findImages(ids);
        Map<Long, Boolean> memberIsLikedRestaurants = findMemberIsLikedRestaurants(ids, memberId);
        for (RestaurantByAddressResponse restaurant : resultList) {
            restaurant.setCelebs(celebs.get(restaurant.getId()));
            restaurant.setImages(images.get(restaurant.getId()));
            restaurant.setLiked(memberIsLikedRestaurants.get(restaurant.getId()));
        }
        return PageableExecutionUtils.getPage(resultList, pageable, totalCountSupplier(cond));
    }

    private List<RestaurantByAddressResponse> findRestaurants(DistrictCodeCond cond, Pageable pageable) {
        return query.selectDistinct(Projections.constructor(
                        RestaurantByAddressResponse.class,
                        restaurant.id,
                        restaurant.name,
                        restaurant.category,
                        restaurant.superCategory,
                        restaurant.roadAddress,
                        restaurant.latitude,
                        restaurant.longitude,
                        restaurant.phoneNumber,
                        restaurant.naverMapUrl,
                        restaurant.viewCount,
                        restaurant.likeCount
                ))
                .from(restaurant)
                .join(administrativeDistrict).on(administrativeDistrict.code.in(cond.codes))
                .where(stContainsCondition)
                //.where(GeometryExpressions.asGeometry(administrativeDistrict.polygon).contains(restaurant.point)) TODO 이거 살려봤으면 좋겠어.. 이대로 쓰면 =1 이 생성돼서 너무 느려져서 버림..
                .orderBy(restaurant.likeCount.desc(), restaurant.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private List<Long> extractIds(List<RestaurantByAddressResponse> resultList) {
        return resultList.stream()
                .map(RestaurantByAddressResponse::getId)
                .toList();
    }

    private Map<Long, List<CelebInfo>> findCelebs(List<Long> restaurantIds) {
        return query.select(Projections.constructor(
                        CelebInfo.class,
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
                .collect(groupingBy(CelebInfo::restaurantId));
    }

    private Map<Long, List<RestaurantImageInfo>> findImages(List<Long> restaurantIds) {
        return query.select(Projections.constructor(
                        RestaurantImageInfo.class,
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
                .collect(groupingBy(RestaurantImageInfo::restaurantId));
    }

    private Map<Long, Boolean> findMemberIsLikedRestaurants(List<Long> ids, @Nullable Long memberId) {
        if (memberId == null) {
            return ids.stream()
                    .collect(toMap(identity(), (ignored) -> FALSE));
        }
        List<Long> memberLikedRestaurantIds = query.select(restaurantLike.restaurant.id)
                .where(
                        restaurantLike.member.id.eq(memberId),
                        restaurantLike.restaurant.id.in(ids)
                ).fetch();
        return ids.stream()
                .collect(toMap(identity(), memberLikedRestaurantIds::contains));
    }

    private LongSupplier totalCountSupplier(DistrictCodeCond cond) {
        JPAQuery<Long> countQuery = query.select(restaurant.countDistinct())
                .from(restaurant)
                .join(administrativeDistrict).on(administrativeDistrict.code.in(cond.codes))
                .where(stContainsCondition);
        return countQuery::fetchOne;
    }

    public record DistrictCodeCond(
            List<String> codes
    ) {
    }
}
