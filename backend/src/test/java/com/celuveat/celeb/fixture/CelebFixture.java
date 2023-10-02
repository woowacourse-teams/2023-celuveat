package com.celuveat.celeb.fixture;

import com.celuveat.celeb.command.domain.Celeb;
import java.util.HashMap;
import java.util.Map;

public class CelebFixture {

    public static Celeb 성시경() {
        return Celeb.builder()
                .name("성시경")
                .profileImageUrl(
                        "https://yt3.googleusercontent.com/vQrdlCaT4Tx1axJtSUa1oxp2zlnRxH-oMreTwWqB-2tdNFStIOrWWw-0jwPvVCUEjm_MywltBFY=s176-c-k-c0x00ffffff-no-rj"
                )
                .youtubeChannelName("@sungsikyung")
                .build();
    }

    public static Celeb 핫둘제주() {
        return Celeb.builder()
                .name("핫둘제주")
                .profileImageUrl(
                        "https://yt3.googleusercontent.com/1dfMVQ10pcdZrxVexr_xkfT2mWhpq-7Cy-u48auoE1Vc0cuGvq4jKjqPLB3eFIEO1WtoUvDaxQ=s176-c-k-c0x00ffffff-no-rj"
                )
                .youtubeChannelName("@hot2_jeju")
                .build();
    }

    public static Celeb 백종원() {
        return Celeb.builder()
                .name("백종원")
                .profileImageUrl(
                        "https://yt3.googleusercontent.com/J3OI66Bc7T3nheyKJKAkXR6tb-_bpCsoRMTFoslOBCXI3DpVY8eFY4LZWzww3BEgkRjMOEoI=s176-c-k-c0x00ffffff-no-rj"
                )
                .youtubeChannelName("@paik_jongwon")
                .build();
    }

    public static Map<String, Celeb> 셀럽들(Celeb... celebs) {
        Map<String, Celeb> celebMap = new HashMap<>();
        for (Celeb celeb : celebs) {
            celebMap.put(celeb.name(), celeb);
        }
        return celebMap;
    }

    public static Celeb 셀럽(String name) {
        return Celeb.builder()
                .name(name)
                .profileImageUrl(name)
                .youtubeChannelName("@" + name)
                .build();
    }
}
