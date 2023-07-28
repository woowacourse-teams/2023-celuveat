package com.celuveat.restaurant.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.withinPercentage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("Coordinate(극좌표계) 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class CoordinateTest {

    @Test
    void 거리를_계산한다() {
        // given
        Coordinate coordinateA = new Coordinate(37.5132612, 127.1001336);
        Coordinate coordinateB = new Coordinate(37.511632, 127.085602);

        // when
        double result = coordinateA.calculateDistance(coordinateB);

        // then
        assertThat(result).isCloseTo(1290, withinPercentage(1));
    }
}
