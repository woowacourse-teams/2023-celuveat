package com.celuveat.common.util;

public class RatingUtils {

    /**
     * 기본적으로 소숫점 1자리까지 출력
     */
    public static double averageRating(double totalRating, int totalCount) {
        return averageRating(totalRating, totalCount, 1);
    }

    /**
     * decimalPlace 가 0 인 경우 -> 1 decimalPlace 가 1 인 경우 -> 1.1 ...
     * <p>
     * double pie = 3.14159265358979; System.out.println(Math.round(pie)); //결과 : 3
     * System.out.println(Math.round(pie*100)/100.0); //결과 : 3.14 System.out.println(Math.round(pie*1000)/1000.0); //결과
     * : 3.142
     *
     * @param decimalPlace 소숫점 몇 째 자리까지 출력할 것인가
     */
    public static double averageRating(double totalRating, int totalCount, int decimalPlace) {
        if (totalRating == 0) {
            return 0.0;
        }
        double pow = Math.pow(10, decimalPlace);
        return (double) Math.round((totalRating / (double) totalCount) * pow) / pow;
    }
}
