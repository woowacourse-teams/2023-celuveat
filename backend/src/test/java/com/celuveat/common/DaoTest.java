package com.celuveat.common;

import com.celuveat.celeb.command.domain.CelebRepository;
import com.celuveat.restaurant.command.domain.RestaurantImageRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.video.command.domain.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

/**
 * Dao 테스트는 일케 쓰는거 어떻게 생각하는지 궁금합니다
 */
@DataJpaTest(
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SeedData.class)
        }
)
@Import(TestConfig.class)
public abstract class DaoTest {

    @Autowired
    protected RestaurantRepository restaurantRepository;
    @Autowired
    protected CelebRepository celebRepository;
    @Autowired
    protected RestaurantImageRepository restaurantImageRepository;
    @Autowired
    protected VideoRepository videoRepository;
}
