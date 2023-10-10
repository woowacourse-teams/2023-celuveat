package com.celuveat.restaurant.presentation.dto;

import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest.LatLngAllOrNoting;
import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest.LatLngAllOrNoting.LatLngAllOrNotingValidator;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.LocationSearchCond;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@LatLngAllOrNoting
public record LocationSearchCondRequest(
        Double lowLatitude,
        Double highLatitude,
        Double lowLongitude,
        Double highLongitude
) {

    private static final double KOREAN_LOW_LATITUDE = 32.47152281030587;
    private static final double KOREAN_HIGH_LATITUDE = 39.566516733752664;
    private static final double KOREAN_LOW_LONGITUDE = 125.60143271619597;
    private static final double KOREAN_HIGH_LONGITUDE = 130.16625205213347;

    private static final LocationSearchCond KOREAN_SEARCH_COND = new LocationSearchCond(
            KOREAN_LOW_LATITUDE,
            KOREAN_HIGH_LATITUDE,
            KOREAN_LOW_LONGITUDE,
            KOREAN_HIGH_LONGITUDE
    );

    public LocationSearchCond toCondition() {
        if (isNull(lowLatitude, highLatitude, lowLongitude, highLongitude)) {
            return KOREAN_SEARCH_COND;
        }
        return new LocationSearchCond(
                lowLatitude,
                highLatitude,
                lowLongitude,
                highLongitude
        );
    }

    private static boolean isNull(Double lowLatitude, Double lowLongitude, Double highLatitude,
                                  Double highLongitude) {
        return (lowLatitude == null && lowLongitude == null
                && highLatitude == null & highLongitude == null);
    }

    private static boolean isExist(Double lowLatitude, Double lowLongitude, Double highLatitude,
                                   Double highLongitude) {
        return (lowLatitude != null && lowLongitude != null
                && highLatitude != null & highLongitude != null);
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = LatLngAllOrNotingValidator.class)
    public @interface LatLngAllOrNoting {

        String message() default "위경도 조건들은 모두 설정되지 않거나, 모두 설정되어야 합니다.";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        class LatLngAllOrNotingValidator implements
                ConstraintValidator<LatLngAllOrNoting, LocationSearchCondRequest> {

            @Override
            public boolean isValid(LocationSearchCondRequest value, ConstraintValidatorContext context) {
                Double lowLatitude = value.lowLatitude();
                Double lowLongitude = value.lowLongitude();
                Double highLatitude = value.highLatitude();
                Double highLongitude = value.highLongitude();
                return isInvalid(lowLatitude, lowLongitude, highLatitude, highLongitude);
            }

            private static boolean isInvalid(
                    Double lowLatitude,
                    Double lowLongitude,
                    Double highLatitude,
                    Double highLongitude
            ) {
                return (isNull(lowLatitude, lowLongitude, highLatitude, highLongitude)
                        || isExist(lowLatitude, lowLongitude, highLatitude, highLongitude));
            }
        }
    }
}
