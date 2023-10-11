package com.celuveat.event.command.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("이벤트 이미지 생성기(EventImageNameGenerator) 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class EventImageNameGeneratorTest {

    @ParameterizedTest
    @CsvSource(value = {
            "mallang._.,또간집,1 -> mallang._._또간집_1",
            "mallang._.,또간집,2 -> mallang._._또간집_2",
            "mallang._.,또간집,3 -> mallang._._또간집_3"
    }, delimiterString = " -> ")
    void 이미지_이름을_생성한다(String param, String expected) {
        // given
        String[] split = param.split(",");

        // when
        String generate =
                EventImageNameGenerator.generate(split[0], split[1], Integer.parseInt(split[2]));

        // then
        assertThat(generate).isEqualTo(expected);
    }
}
