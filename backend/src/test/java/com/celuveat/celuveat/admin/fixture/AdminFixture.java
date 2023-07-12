package com.celuveat.celuveat.admin.fixture;

import com.celuveat.celuveat.admin.domain.Admin;

public class AdminFixture {

    public static Admin 관리자_도기() {
        return Admin.builder()
                .name("도기")
                .username("도기")
                .password("도기1234")
                .build();
    }

    public static Admin 관리자_오도() {
        return Admin.builder()
                .name("오도")
                .username("오도")
                .password("오도1234")
                .build();
    }
}
