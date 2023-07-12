package com.celuveat.celuveat.video.fixture;

import com.celuveat.celuveat.video.domain.VideoHistory;
import java.time.LocalDateTime;

@SuppressWarnings("NonAsciiCharacters")
public class VideoHistoryFixture {

    public static VideoHistory 영상_이력(Long 셀럽_ID) {
        return VideoHistory.builder()
                .celebId(셀럽_ID)
                .title("맛있는 음식점 다녀옴. 말랑 잘하네")
                .viewCount(3)
                .videoId("8RdkFuFK1DY")
                .uploadDate(LocalDateTime.of(2000, 10, 4, 10, 21, 22))
                .build();
    }

    public static VideoHistory 쯔양_001(Long 셀럽_ID) {
        return VideoHistory.builder()
                .celebId(셀럽_ID)
                .title("이만큼 시켰더니 단체손님인줄 아셨대요🤣 방이동 미친비주얼 간장게장 먹방")
                .videoId("8RdkFuFK1DY")
                .viewCount(1_505_107)
                .uploadDate(LocalDateTime.of(2023, 7, 8, 21, 0, 6))
                .ads(false)
                .build();
    }

    public static VideoHistory 쯔양_002(Long 셀럽_ID) {
        return VideoHistory.builder()
                .celebId(셀럽_ID)
                .title("200만원으로 시장 털었습니다?😳 순천재래시장 먹방")
                .videoId("NrLPC4raEh4")
                .viewCount(1_528_713)
                .uploadDate(LocalDateTime.of(2023, 7, 6, 21, 0, 31))
                .ads(false)
                .build();
    }

    public static VideoHistory 쯔양_003(Long 셀럽_ID) {
        return VideoHistory.builder()
                .celebId(셀럽_ID)
                .title("수영장에서 먹방했더니 강사님이..🤣 서핑장에서 정원분식 떡볶이 먹방")
                .videoId("G3pQQeL47wI")
                .viewCount(1_083_591)
                .uploadDate(LocalDateTime.of(2023, 7, 4, 21, 2, 27))
                .ads(false)
                .build();
    }

    public static VideoHistory 쯔양_004(Long 셀럽_ID) {
        return VideoHistory.builder()
                .celebId(셀럽_ID)
                .title("미국 3대 버거🔥 전날밤 11시부터 줄섰다는 파이브가이즈? 외국인 직원분들 다 놀라신 햄버거 먹방")
                .videoId("Y8KeqoGtSNA")
                .viewCount(4_216_500)
                .uploadDate(LocalDateTime.of(2023, 7, 2, 21, 0, 23))
                .ads(false)
                .build();
    }

    public static VideoHistory 쯔양_005(Long 셀럽_ID) {
        return VideoHistory.builder()
                .celebId(셀럽_ID)
                .title("가볍게 15인분 먹고 대표님 놀래키기🤣 부산음식 낙곱새 먹방")
                .videoId("bt2kahtnrzE")
                .viewCount(1_301_676)
                .uploadDate(LocalDateTime.of(2023, 6, 30, 21, 0, 35))
                .ads(false)
                .build();
    }

    public static VideoHistory 쯔양_006(Long 셀럽_ID) {
        return VideoHistory.builder()
                .celebId(셀럽_ID)
                .title("평양냉면 3탄!😎 50년 전통 미슐랭만 5년 받은 정인면옥 먹방")
                .videoId("1DnKb2BrsTg")
                .viewCount(1_136_113)
                .uploadDate(LocalDateTime.of(2023, 6, 28, 21, 0, 1))
                .ads(false)
                .build();
    }

    public static VideoHistory 쯔양_007(Long 셀럽_ID) {
        return VideoHistory.builder()
                .celebId(셀럽_ID)
                .title("할머님들께 단체로 박수받았습니다🤣 짜장면이 무료라고 해서 가본 50년 전통 중국집 먹방")
                .videoId("0FACIBa9Jfg")
                .viewCount(2_729_655)
                .uploadDate(LocalDateTime.of(2023, 6, 26, 21, 0, 1))
                .ads(false)
                .build();
    }

    public static VideoHistory 쯔양_008(Long 셀럽_ID) {
        return VideoHistory.builder()
                .celebId(셀럽_ID)
                .title("전국민이 다안다는 그 보쌈집..😳 원할머니 보쌈 낙지볶음 4.4kg먹방")
                .videoId("h_PBOLpfoCk")
                .viewCount(1_411_408)
                .uploadDate(LocalDateTime.of(2023, 6, 24, 21, 0, 38))
                .ads(false)
                .build();
    }

    public static VideoHistory 쯔양_009(Long 셀럽_ID) {
        return VideoHistory.builder()
                .celebId(셀럽_ID)
                .title("이거 먹으려고 아침7시에 찾아갔습니다🥺 종로 숨은 골목맛집 승우네식당 백반먹방")
                .videoId("HyWn4MjoxXU")
                .viewCount(2_722_726)
                .uploadDate(LocalDateTime.of(2023, 6, 22, 21, 0, 16))
                .ads(false)
                .build();
    }

    public static VideoHistory 쯔양_010(Long 셀럽_ID) {
        return VideoHistory.builder()
                .celebId(셀럽_ID)
                .title("살면서 이렇게 많이주는곳은 처음이에요😂 1인분 시키면 2인분 주는 킹왕짱 떡볶이 먹방")
                .videoId("jZLnTb5AviA")
                .viewCount(1_946_817)
                .uploadDate(LocalDateTime.of(2023, 6, 20, 21, 0, 36))
                .ads(false)
                .build();
    }
}
