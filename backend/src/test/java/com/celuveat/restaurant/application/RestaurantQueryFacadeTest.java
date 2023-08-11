package com.celuveat.restaurant.application;

import static com.celuveat.celeb.fixture.CelebFixture.셀럽;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.음식점사진;
import static com.celuveat.video.fixture.VideoFixture.영상;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.celeb.domain.CelebRepository;
import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.application.dto.RestaurantWithCelebAndImagesDetailResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.video.domain.VideoRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("RestaurantQueryFacadeTest(음식점 좋아요 서비스) 은(는)")
class RestaurantQueryFacadeTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CelebRepository celebRepository;

    @Autowired
    private RestaurantImageRepository restaurantImageRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private RestaurantQueryFacade restaurantQueryFacade;

    @Test
    void 셀럽ID로_음식점_상세_조회_정렬_테스트() {
        // given
        Restaurant 로이스2호점 = restaurantRepository.save(음식점("로이스2호점"));
        List<Celeb> celebs = celebRepository.saveAll(List.of(셀럽("로이스"), 셀럽("말랑")));
        restaurantImageRepository.saveAll(List.of(
                        음식점사진("이미지1", 로이스2호점, "로이스"),
                        음식점사진("이미지2", 로이스2호점, "말랑")
                )
        );
        videoRepository.saveAll(List.of(
                        영상("youtube1.com", 로이스2호점, celebs.get(0)),
                        영상("youtube2.com", 로이스2호점, celebs.get(1))
                )
        );
        Celeb targetCeleb = celebs.get(1);

        // when
        RestaurantWithCelebAndImagesDetailResponse result =
                restaurantQueryFacade.findRestaurantDetailById(로이스2호점.id(), targetCeleb.id());

        // then
        assertThat(result.celebs().get(0))
                .usingRecursiveComparison()
                .isEqualTo(targetCeleb);
        assertThat(result.images().get(0).author())
                .isEqualTo(targetCeleb.name());
    }
}
