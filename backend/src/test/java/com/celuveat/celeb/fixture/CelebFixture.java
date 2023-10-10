package com.celuveat.celeb.fixture;

import com.celuveat.celeb.command.domain.Celeb;
import org.springframework.test.util.ReflectionTestUtils;

public class CelebFixture {

    public static Celeb 회사랑(Long id) {
        Celeb 회사랑 = 회사랑();
        ReflectionTestUtils.setField(회사랑, "id", id);
        return 회사랑;
    }

    public static Celeb 회사랑() {
        return Celeb.builder()
                .name("회사랑")
                .profileImageUrl(
                        "https://yt3.googleusercontent.com/ytc/AOPolaSzgHdMFMDyvhiU2qtaYULlZx6pdw4Fz_vsW6J5Qw=s176-c-k-c0x00ffffff-no-rj"
                ).youtubeChannelName("@RawFishEater")
                .build();
    }

    public static Celeb 맛객리우(Long id) {
        Celeb 맛객리우 = 맛객리우();
        ReflectionTestUtils.setField(맛객리우, "id", id);
        return 맛객리우;
    }

    public static Celeb 맛객리우() {
        return Celeb.builder()
                .name("맛객리우")
                .profileImageUrl(
                        "https://yt3.googleusercontent.com/KhKylYQTqpR3QQ1RuiYZ5xiM2fNsOJ_0jLFYbBBhk9Gh-zjpGTMUUSyPVGOHq4VZHzl6DN6qXQ=s176-c-k-c0x00ffffff-no-rj"
                ).youtubeChannelName("@Liwoo_foodie")
                .build();
    }

    public static Celeb 성시경(Long id) {
        Celeb 성시경 = 성시경();
        ReflectionTestUtils.setField(성시경, "id", id);
        return 성시경;
    }

    public static Celeb 성시경() {
        return Celeb.builder()
                .name("성시경")
                .profileImageUrl(
                        "https://yt3.googleusercontent.com/vQrdlCaT4Tx1axJtSUa1oxp2zlnRxH-oMreTwWqB-2tdNFStIOrWWw-0jwPvVCUEjm_MywltBFY=s176-c-k-c0x00ffffff-no-rj"
                ).youtubeChannelName("@sungsikyung")
                .build();
    }

    public static Celeb 쯔양(Long id) {
        Celeb 쯔양 = 쯔양();
        ReflectionTestUtils.setField(쯔양, "id", id);
        return 쯔양;
    }

    public static Celeb 쯔양() {
        return Celeb.builder()
                .name("쯔양")
                .profileImageUrl(
                        "https://yt3.googleusercontent.com/_s3C7XpwVKVr_5gDrmYJh9AOkU3wFlY9FWyZBVGVP_v7B09P5O4bbEZaWGpiuyT78Dk-aElE=s176-c-k-c0x00ffffff-no-rj"
                ).youtubeChannelName("@tzuyang6145")
                .build();
    }

    public static Celeb 셀럽(String name) {
        return Celeb.builder()
                .name(name)
                .profileImageUrl(name)
                .youtubeChannelName("@" + name)
                .build();
    }
}
