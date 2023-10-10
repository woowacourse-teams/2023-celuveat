package com.celuveat.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("평점 유틸리티(RatingUtils) 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RatingUtilsTest {

    @ParameterizedTest
    @CsvSource(value = {
            "10,3,0 -> 3",
            "10,3,1 -> 3.3",
            "10,3,2 -> 3.33",
            "10,3,3 -> 3.333",
            "1.5,1,0 -> 2",
            "1.5,1,1 -> 1.5",
            "1.5,1,2 -> 1.5",
    }, delimiterString = "->")
    void 원하는_소숫점에서_반올림하여_평균_평점을_구할_수_있다(String parameters, double result) {
        // given
        String[] split = parameters.split(",");
        double totalRating = Double.parseDouble(split[0]);
        int totalCount = Integer.parseInt(split[1]);
        int decimalPlace = Integer.parseInt(split[2]);

        // when
        double actual = RatingUtils.averageRating(totalRating, totalCount, decimalPlace);

        // then
        assertThat(actual).isEqualTo(result);
    }

    @Test
    void 기본적으로는_소숫점_한자리까지_출력한다() {
        // when
        double actual = RatingUtils.averageRating(10, 3);

        // then
        assertThat(actual).isEqualTo(3.3);
    }
}
