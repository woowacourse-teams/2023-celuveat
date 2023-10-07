package com.celuveat.restaurant.query.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.geolatte.geom.builder.DSL.g;
import static org.geolatte.geom.builder.DSL.polygon;
import static org.geolatte.geom.builder.DSL.ring;
import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

import com.celuveat.administrativedistrict.domain.AdministrativeDistrict;
import com.celuveat.common.DaoTest;
import com.celuveat.common.TestData;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.query.dao.RestaurantByAddressResponseDao.DistrictCodeCond;
import com.celuveat.restaurant.query.dto.RestaurantByAddressResponse;
import java.util.List;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Polygon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/h2-spatial.sql")
@DisplayName("지역으로 음식점 조회 DAO(RestaurantByAddressResponseDao) 은(는)")
class RestaurantByAddressResponseDaoTest extends DaoTest {

    @Autowired
    private RestaurantByAddressResponseDao restaurantByAddressResponseDao;

    private AdministrativeDistrict 말랑1동;
    private AdministrativeDistrict 말랑2동;
    private AdministrativeDistrict 말랑특별시;
    private List<Restaurant> 말랑1동에_속한_음식점들;
    private List<Restaurant> 말랑2동에_속한_음식점들;
    private List<Restaurant> 말랑특별시에만_속하는_음식점들;

    @Override
    protected TestData prepareTestData() {
        Polygon<G2D> 영역1 = polygon(WGS84, ring(
                g(4.0, 4.0),
                g(5.0, 4.0),
                g(5.0, 5.0),
                g(4.0, 5.0),
                g(4.0, 4.0)
        ));
        Polygon<G2D> 영역2 = polygon(WGS84, ring(
                g(2.0, 2.0),
                g(3.0, 2.0),
                g(3.0, 3.0),
                g(2.0, 3.0),
                g(2.0, 2.0)
        ));
        Polygon<G2D> 전체를_포함한_영역3 = polygon(WGS84, ring(
                g(0.0, 0.0),
                g(10.0, 0.0),
                g(10.0, 10.0),
                g(0.0, 10.0),
                g(0.0, 0.0)
        ));
        말랑특별시 = new AdministrativeDistrict(3L, 전체를_포함한_영역3, "1", "mallang special si", "말랑특별시");
        말랑1동 = new AdministrativeDistrict(1L, 영역1, "11", "mallang 1", "말랑1동");
        말랑2동 = new AdministrativeDistrict(2L, 영역2, "12", "mallang 2", "말랑2동");
        testData.addAdministrativeDistricts(말랑1동, 말랑2동, 말랑특별시);
        말랑1동에_속한_음식점들 = List.of(
                Restaurant.builder()
                        .name("말랑1동 대표 한식집")
                        .roadAddress("말랑특별시 말랑1동 한식집")
                        .phoneNumber("000-0000-0000")
                        .naverMapUrl("https://naver.com/0")
                        .longitude(4.5)
                        .latitude(4.2)
                        .category("한식")
                        .superCategory("한식")
                        .build(),
                Restaurant.builder()
                        .name("말랑1동 대표 일식집")
                        .roadAddress("말랑특별시 말랑1동 일식집")
                        .phoneNumber("000-0000-0001")
                        .naverMapUrl("https://naver.com/1")
                        .longitude(4.9)
                        .latitude(4.1)
                        .category("일식")
                        .superCategory("일식")
                        .build()
        );
        말랑2동에_속한_음식점들 = List.of(
                Restaurant.builder()
                        .name("말랑2동 대표 한식집")
                        .roadAddress("말랑특별시 말랑2동 한식집")
                        .phoneNumber("000-0000-0002")
                        .naverMapUrl("https://naver.com/2")
                        .longitude(2.5)
                        .latitude(2.2)
                        .category("한식")
                        .superCategory("한식")
                        .build(),
                Restaurant.builder()
                        .name("말랑2동 대표 일식집")
                        .roadAddress("말랑특별시 말랑2동 일식집")
                        .phoneNumber("000-0000-0003")
                        .naverMapUrl("https://naver.com/3")
                        .longitude(2.7)
                        .latitude(2.1)
                        .category("일식")
                        .superCategory("일식")
                        .build()
        );
        말랑특별시에만_속하는_음식점들 = List.of(
                Restaurant.builder()
                        .name("말랑특별시 대표 한식집")
                        .roadAddress("말랑특별시 한식집")
                        .phoneNumber("000-0000-0004")
                        .naverMapUrl("https://naver.com/4")
                        .longitude(7.5)
                        .latitude(7.2)
                        .category("한식")
                        .superCategory("한식")
                        .build(),
                Restaurant.builder()
                        .name("말랑특별시 대표 일식집")
                        .roadAddress("말랑특별시 일식집")
                        .phoneNumber("000-0000-0005")
                        .naverMapUrl("https://naver.com/5")
                        .longitude(8.7)
                        .latitude(8.1)
                        .category("일식")
                        .superCategory("일식")
                        .build()
        );
        testData.addRestaurants(말랑1동에_속한_음식점들);
        testData.addRestaurants(말랑2동에_속한_음식점들);
        testData.addRestaurants(말랑특별시에만_속하는_음식점들);
        return testData;
    }

    @Test
    void 법정동코드로_해당_지역에_속한_음식점을_조회한다() {
        // when
        Page<RestaurantByAddressResponse> 말랑1동에_속한_음식점들 = restaurantByAddressResponseDao.find(
                new DistrictCodeCond(List.of(말랑1동.code())),
                PageRequest.of(0, 10),
                null
        );

        Page<RestaurantByAddressResponse> 말랑1동과_말랑2동에_속한_음식점들 = restaurantByAddressResponseDao.find(
                new DistrictCodeCond(List.of(말랑1동.code(), 말랑2동.code())),
                PageRequest.of(0, 10),
                null
        );

        Page<RestaurantByAddressResponse> 말랑특별시에_속한_음식점들 = restaurantByAddressResponseDao.find(
                new DistrictCodeCond(List.of(말랑특별시.code()))
                , PageRequest.of(0, 10),
                null
        );

        // 말랑특별시 안에 말랑1동이 존재함. 이때 포함관계의 두 지역을 주었을 때, 음식점을 중복으로 가져오지 않는것 검증을 위한 테스트
        Page<RestaurantByAddressResponse> 말랑특별시와_말랑1동에_속한_음식점들 = restaurantByAddressResponseDao.find(
                new DistrictCodeCond(List.of(말랑특별시.code(), 말랑1동.code()))
                , PageRequest.of(0, 10),
                null
        );

        // then
        assertThat(말랑1동에_속한_음식점들)
                .extracting(RestaurantByAddressResponse::getName)
                .containsExactly("말랑1동 대표 한식집", "말랑1동 대표 일식집");
        assertThat(말랑1동과_말랑2동에_속한_음식점들)
                .extracting(RestaurantByAddressResponse::getName)
                .containsExactly("말랑1동 대표 한식집", "말랑1동 대표 일식집",
                        "말랑2동 대표 한식집", "말랑2동 대표 일식집");
        assertThat(말랑특별시에_속한_음식점들)
                .extracting(RestaurantByAddressResponse::getName)
                .containsExactly("말랑1동 대표 한식집", "말랑1동 대표 일식집",
                        "말랑2동 대표 한식집", "말랑2동 대표 일식집",
                        "말랑특별시 대표 한식집", "말랑특별시 대표 일식집");
        assertThat(말랑특별시와_말랑1동에_속한_음식점들)
                .extracting(RestaurantByAddressResponse::getName)
                .containsExactly("말랑1동 대표 한식집", "말랑1동 대표 일식집",
                        "말랑2동 대표 한식집", "말랑2동 대표 일식집",
                        "말랑특별시 대표 한식집", "말랑특별시 대표 일식집");
    }
}
