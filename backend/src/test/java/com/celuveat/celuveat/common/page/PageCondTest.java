package com.celuveat.celuveat.common.page;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("PageCond 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class PageCondTest {

    @Test
    void limit_는_size_와_같다() {
        // when
        PageCond pageCond = new PageCond(1, 14);

        // then
        assertThat(pageCond.limit()).isEqualTo(14);
    }

    @Test
    @DisplayName("offset = (page - 1) * size")
    void offset_test() {
        // when
        PageCond pageCond1 = new PageCond(1, 14);
        PageCond pageCond2 = new PageCond(2, 14);

        // then
        assertThat(pageCond1.offset()).isEqualTo(0);
        assertThat(pageCond2.offset()).isEqualTo(14);
    }
}
