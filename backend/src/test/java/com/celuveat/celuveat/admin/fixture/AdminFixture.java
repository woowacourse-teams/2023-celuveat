package com.celuveat.celuveat.admin.fixture;

import com.celuveat.celuveat.admin.domain.Admin;

@SuppressWarnings("NonAsciiCharacters")
public class AdminFixture {

    public static Admin 관리자_도기() {
        return Admin.builder()
                .name("도기")
                .username("도기")
                .password("$2a$10$BqX8xbXUFGXyxHRzrUCVvOZBrPD4XY1QiajgA9IYrMpeumfxUmme.")
                .build();
    }

    public static Admin 관리자_오도() {
        return Admin.builder()
                .name("오도")
                .username("오도")
                .password("$2a$10$BqX8xbXUFGXyxHRzrUCVvOZBrPD4XY1QiajgA9IYrMpeumfxUmme.")
                .build();
    }
}
