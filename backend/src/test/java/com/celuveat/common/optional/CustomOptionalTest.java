package com.celuveat.common.optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("CustomOptional 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class CustomOptionalTest {

    @Test
    void 값이_널일때_supplier를_실행한다() {
        // given
        CustomOptional<Long> memberIdOpt = CustomOptional.empty();
        String expectedFun = "expectedFun";
        String expectedSup = "expectedSup";

        // when
        String result = memberIdOpt.mapIfPresentOrElse(id -> expectedFun, () -> expectedSup);

        // then
        assertThat(result).isEqualTo(expectedSup);
    }

    @Test
    void 값이_널이_아니면_function을_실행한다() {
        // given
        Long memberId = 1L;
        CustomOptional<Long> memberIdOpt = CustomOptional.of(memberId);
        String expectedFun = "expectedFun";
        String expectedSup = "expectedSup";

        // when
        String result = memberIdOpt.mapIfPresentOrElse(id -> expectedFun, () -> expectedSup);

        // then
        assertThat(result).isEqualTo(expectedFun);
    }
}
