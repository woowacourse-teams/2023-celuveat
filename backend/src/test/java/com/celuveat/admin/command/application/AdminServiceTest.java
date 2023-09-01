package com.celuveat.admin.command.application;

import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.TAB;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.데이터_입력_생성;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.데이터_저장_요청_생성;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.셀럽_입력_생성;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.셀럽_저장_요청_생성;
import static com.celuveat.admin.exception.AdminExceptionType.ILLEGAL_DATE_FORMAT;
import static com.celuveat.admin.exception.AdminExceptionType.MISMATCH_COUNT_YOUTUBE_VIDEO_LINK_AND_UPLOAD_DATE;
import static com.celuveat.admin.exception.AdminExceptionType.NOT_EXISTS_CELEB;
import static com.celuveat.celeb.fixture.CelebFixture.셀럽;
import static com.celuveat.restaurant.command.domain.SocialMedia.INSTAGRAM;
import static com.celuveat.restaurant.command.domain.SocialMedia.YOUTUBE;
import static com.celuveat.restaurant.fixture.RestaurantFixture.국민연금_구내식당;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.admin.exception.AdminException;
import com.celuveat.admin.presentation.dto.SaveCelebRequest;
import com.celuveat.admin.presentation.dto.SaveDataRequest;
import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.celeb.command.domain.CelebRepository;
import com.celuveat.common.IntegrationTest;
import com.celuveat.common.exception.BaseExceptionType;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantImageRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.video.command.domain.VideoRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@DisplayName("어드민 서비스(AdminService) 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CelebRepository celebRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantImageRepository restaurantImageRepository;

    @Autowired
    private VideoRepository videoRepository;

    private Celeb 셀럽_저장(Celeb 셀럽) {
        return celebRepository.save(셀럽);
    }

    private Restaurant 음식점_저장(Restaurant 음식점) {
        return restaurantRepository.save(음식점);
    }

    private String 영상_업로드_날짜가_잘못된_입력_생성(String 셀럽_이름, String 음식점_이름, String 영상_업로드_날짜) {
        return 음식점_이름 +
                TAB + "@" + 셀럽_이름 +
                TAB + "유튜브 영상 링크" +
                TAB + 영상_업로드_날짜 +
                TAB + 음식점_이름 + " 주소" +
                TAB + "전화번호" +
                TAB + "카테고리" +
                TAB + "12.3456" +
                TAB + "12.3456" +
                TAB + "음식점네이버링크" +
                TAB + 음식점_이름 + ".png" +
                TAB + "인스타 아이디";
    }

    @Nested
    class 음식점_데이터_저장 {

        @Test
        void 저장되어_있지_않은_셀럽으로_데이터를_저장하면_예외가_발생한다() {
            // given
            String input = 데이터_입력_생성("도기", "국민연금");

            List<SaveDataRequest> 요청 = 데이터_저장_요청_생성(input);

            // then
            BaseExceptionType exceptionType = assertThrows(AdminException.class,
                    () -> adminService.saveData(요청)).exceptionType();
            assertThat(exceptionType).isEqualTo(NOT_EXISTS_CELEB);
        }

        @ParameterizedTest
        @ValueSource(strings = {"23. 7. 23.", "2023.7.23", "2023. 7.8."})
        void 날짜_형식이_다른_경우_예외가_발생한다(String 영상_업로드_날짜) {
            // given
            셀럽_저장(셀럽("도기"));

            // when
            String input = 영상_업로드_날짜가_잘못된_입력_생성("도기", "농민백암순대", 영상_업로드_날짜);
            List<SaveDataRequest> 요청 = 데이터_저장_요청_생성(input);

            // then
            BaseExceptionType exceptionType = assertThrows(AdminException.class,
                    () -> adminService.saveData(요청)).exceptionType();
            assertThat(exceptionType).isEqualTo(ILLEGAL_DATE_FORMAT);
        }

        @Test
        void 셀럽과_음식점이_저장되어_있고_데이터_두_개를_저장한다_한_개는_존재하는_음식점이고_나머지_하나는_존재하지_않는다() {
            // given
            셀럽_저장(셀럽("도기"));
            음식점_저장(국민연금_구내식당);

            String input = 데이터_입력_생성("도기", "국민연금")
                    + System.lineSeparator()
                    + 데이터_입력_생성("도기", "농민백암순대");
            List<SaveDataRequest> 요청 = 데이터_저장_요청_생성(input);

            // when
            adminService.saveData(요청);

            // then
            assertThat(celebRepository.count()).isEqualTo(1);
            assertThat(restaurantRepository.count()).isEqualTo(2);
            assertThat(restaurantImageRepository.count()).isEqualTo(2);
            assertThat(videoRepository.count()).isEqualTo(2);
        }

        @Test
        void 음식점이_이미_저장되어_있고_데이터_두_개를_저장한다_둘_다_같은_음식점_데이터이지만_다른_셀럽이다() {
            // given
            셀럽_저장(셀럽("도기"));
            셀럽_저장(셀럽("로이스"));
            음식점_저장(국민연금_구내식당);

            String input = 데이터_입력_생성("도기", "국민연금")
                    + System.lineSeparator()
                    + 데이터_입력_생성("로이스", "국민연금");
            List<SaveDataRequest> 요청 = 데이터_저장_요청_생성(input);

            // when
            adminService.saveData(요청);

            // then
            assertThat(celebRepository.count()).isEqualTo(2);
            assertThat(restaurantRepository.count()).isEqualTo(1);
            assertThat(restaurantImageRepository.count()).isEqualTo(2);
            assertThat(videoRepository.count()).isEqualTo(2);
        }

        @Test
        void 셀럽만_저장되어_있고_데이터_한_개를_저장한다() {
            // given
            셀럽_저장(셀럽("도기"));

            String input = 데이터_입력_생성("도기", "국민연금");
            List<SaveDataRequest> 요청 = 데이터_저장_요청_생성(input);

            // when
            adminService.saveData(요청);

            // then
            assertThat(celebRepository.count()).isEqualTo(1);
            assertThat(restaurantRepository.count()).isEqualTo(1);
            assertThat(restaurantImageRepository.count()).isEqualTo(1);
            assertThat(videoRepository.count()).isEqualTo(1);
        }

        @Test
        void 인스타_아이디가_입력되지_않으면_socialMedia는_YOUTUBE로_저장된다() {
            // given
            셀럽_저장(셀럽("도기"));

            String input = 데이터_입력_생성("도기", "국민연금");
            List<SaveDataRequest> 요청 = 데이터_저장_요청_생성(input);

            // when
            adminService.saveData(요청);

            // then
            assertThat(restaurantImageRepository.getReferenceById(1L).socialMedia()).isEqualTo(YOUTUBE);
        }

        @Test
        void 인스타_아이디가_입력되면_해당_정보가_저장된다() {
            // given
            String instagramName = "doggy";
            셀럽_저장(셀럽("도기"));
            String input = 데이터_입력_생성("도기", "국민연금", instagramName);
            List<SaveDataRequest> 요청 = 데이터_저장_요청_생성(input);

            // when
            adminService.saveData(요청);

            // then
            RestaurantImage image = restaurantImageRepository.getReferenceById(1L);
            assertThat(image.socialMedia()).isEqualTo(INSTAGRAM);
            assertThat(image.author()).isEqualTo(instagramName);
        }

        @Test
        void 둘_이상의_유튜브_영상_링크를_한번에_저장할_수_있다() {
            // given
            셀럽_저장(셀럽("도기"));

            String input = 데이터_입력_생성(
                    "도기",
                    "국민연금",
                    "영상링크1, 영상링크2",
                    "1987. 6. 8., 1945. 4. 15.",
                    "doggy"
            );
            List<SaveDataRequest> 요청 = 데이터_저장_요청_생성(input);

            // when
            adminService.saveData(요청);

            // then
            assertThat(celebRepository.count()).isEqualTo(1);
            assertThat(restaurantRepository.count()).isEqualTo(1);
            assertThat(restaurantImageRepository.count()).isEqualTo(1);
            assertThat(videoRepository.count()).isEqualTo(2);
        }

        @Test
        void 영상_링크와_업로드_일의_수가_다르면_예외가_발생한다() {
            // given
            셀럽_저장(셀럽("도기"));

            String input = 데이터_입력_생성(
                    "도기",
                    "국민연금",
                    "영상링크1, 영상링크2",
                    "1987. 6. 8., 1945. 4. 15., 2023. 8. 31.",
                    "doggy"
            );
            List<SaveDataRequest> 요청 = 데이터_저장_요청_생성(input);

            // when
            BaseExceptionType exceptionType = assertThrows(AdminException.class,
                    () -> adminService.saveData(요청)
            ).exceptionType();
            assertThat(exceptionType).isEqualTo(MISMATCH_COUNT_YOUTUBE_VIDEO_LINK_AND_UPLOAD_DATE);
        }
    }

    @Nested
    class 셀럽_저장 {

        @Test
        void 셀럽을_저장한다() {
            // given
            List<Celeb> expected = List.of(셀럽("도기"), 셀럽("로이스"));
            String input = 셀럽_입력_생성("도기")
                    + System.lineSeparator()
                    + 셀럽_입력_생성("로이스");
            List<SaveCelebRequest> 요청 = 셀럽_저장_요청_생성(input);

            // when
            adminService.saveCelebs(요청);

            // then
            assertThat(celebRepository.count()).isEqualTo(2);
            assertThat(celebRepository.findAll()).usingRecursiveComparison()
                    .comparingOnlyFields("name", "youtubeChannelName")
                    .isEqualTo(expected);
        }
    }
}
