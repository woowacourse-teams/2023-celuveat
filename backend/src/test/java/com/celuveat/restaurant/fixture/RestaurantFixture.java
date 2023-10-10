package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantPoint;
import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.LocationSearchCond;
import java.util.Random;

public class RestaurantFixture {

    public static Boolean 좋아요_누름 = Boolean.TRUE;
    public static Boolean 좋아요_누르지_않음 = Boolean.FALSE;

    public static Restaurant 하늘초밥() {
        return Restaurant.builder()
                .name("하늘초밥")
                .category("초밥,롤")
                .roadAddress("서울 서대문구 이화여대2길 14 1층 하늘초밥")
                .latitude(37.5572713)
                .longitude(126.9466788)
                .phoneNumber("0507-1366-4573")
                .naverMapUrl("https://map.naver.com/v5/entry/place/1960457705?entry=plt&c=15,0,0,0,dh")
                .build();
    }

    public static Restaurant 모던샤브하우스() {
        return Restaurant.builder()
                .name("모던샤브하우스")
                .category("샤브샤브")
                .roadAddress("서울 종로구 종로3길 17 D타워 리플레이스 광화문 5층")
                .latitude(37.5710694)
                .longitude(126.978929)
                .phoneNumber("02-2251-8501")
                .naverMapUrl("https://map.naver.com/v5/entry/place/1838141891?entry=plt&c=15,0,0,0,dh")
                .build();
    }

    public static Restaurant 대성집() {
        return Restaurant.builder()
                .name("대성집")
                .category("곰탕,설렁탕")
                .roadAddress("서울 종로구 행촌동 209-35")
                .latitude(37.5727172)
                .longitude(126.9609577)
                .phoneNumber("02-735-4259")
                .naverMapUrl("https://map.naver.com/v5/entry/place/13517178?c=15,0,0,0,dh")
                .build();
    }

    public static Restaurant 음식점(String name, String category) {
        return 음식점(name, category, 37.5206993, 127.019975);
    }

    public static Restaurant 음식점(String name, String category, RestaurantPoint restaurantPoint) {
        return 음식점(name, category, restaurantPoint.latitude(), restaurantPoint.longitude());
    }

    public static Restaurant 음식점(String name, String category, double latitude, double longitude) {
        return Restaurant.builder()
                .name(name)
                .roadAddress(name)
                .naverMapUrl(name)
                .phoneNumber(name)
                .category(category)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public static String 상위_카테고리(String 하위_카테고리) {
        return switch (하위_카테고리) {
            case "감자탕", "게요리", "곰탕, 설렁탕",
                    "곱창, 막창, 양", "국수",
                    "기사식당", "김밥", "낙지요리",
                    "냉면", "농수산물", "닭볶음탕",
                    "대게요리", "두부요리", "막국수",
                    "백반,가정식", "백숙,삼계탕", "보리밥",
                    "보쌈", "수육", "시장", "쌈밥",
                    "아귀찜,해물찜", "전, 빈대떡",
                    "족발,보쌈", "주꾸미요리", "쭈꾸미요리",
                    "찜닭", "추어탕", "칼국수, 만두",
                    "칼국수,만두", "한식", "한정식", "해장국" -> "한식";
            case "딤섬, 중식만두",
                    "딤섬,중식만두", "양꼬치",
                    "중국집", "중식", "중식당" -> "중식";
            case "덮밥", "돈가스", "샤브샤브", "오뎅, 꼬치", "우동,소바", "이자카야", "일본식라면", "일식", "일식,초밥뷔페", "일식당", "일식튀김,꼬치", "초밥, 롤", "초밥,롤" ->
                    "일식";
            case "뷔페", "스테이크", "스테이크, 립", "스파게티,파스타전문", "양식", "이탈리아음식", "패밀리레스토랑", "프랑스음식" -> "양식";
            case "치킨,닭강정", "피자", "햄버거" -> "치킨, 피자, 햄버거";
            case "바닷가재요리", "해물, 생선요리", "생선회", "수산물", "오징어요리", "장어, 먹장어요리", "장어,먹장어요리", "조개요리", "해물", "해물,생선요리", "해산물", "해산물뷔페", "회" ->
                    "회, 수산물";
            case "고기뷔페", "곱창", "닭요리", "돼지고기", "돼지고기구이", "소고기", "소고기구이",
                    "양갈비", "양고기", "오리고기", "오리요리", "육류, 고기요리", "육류,고기요리", "정육점" -> "고기";
            case "국밥", "곰탕,설렁탕", "순대,순댓국", "순대, 순댓국", "순댓국, 순대" -> "국, 국밥";
            case "베이커리", "카페", "카페,디저트" -> "카페, 디저트";
            case "가공식품", "떡볶이", "라면", "만두", "분식", "종합분식", "포장마차" -> "분식";
            case "독일음식", "멕시코, 남미음식", "멕시코,남미음식", "베트남음식", "아시아음식", "태국음식" -> "세계요리";
            case "찌개, 전골", "찌개,전골" -> "탕, 찌개";
            case "맥주, 호프", "맥주,호프", "바(BAR)", "술집", "와인", "요리주점", "야식", "전통,민속주점" -> "주점";
            default -> "기타";
        };
    }

    public static class RestaurantPointFixture {

        public static LocationSearchCond 대성집만_포함한_검색_영역() {
            return 대성집만_포함한_검색_영역_요청().toCondition();
        }

        public static LocationSearchCond 하늘초밥만_포함한_검색_영역() {
            return 하늘초밥만_포함한_검색_영역_요청().toCondition();
        }

        public static LocationSearchCond 모던샤브하우스만_포함한_검색_영역() {
            return 모던샤브하우스만_포함한_검색_영역_요청().toCondition();
        }

        public static LocationSearchCond 대한민국_전체를_포함한_검색_영역() {
            return 대한민국_전체를_포함한_검색_영역_요청().toCondition();
        }

        public static LocationSearchCondRequest 대성집만_포함한_검색_영역_요청() {
            return new LocationSearchCondRequest(
                    37.570945043671536, 37.57528178613561, 126.9600811054725, 126.96204984688943
            );
        }

        public static LocationSearchCondRequest 하늘초밥만_포함한_검색_영역_요청() {
            return new LocationSearchCondRequest(
                    37.55513973710626, 37.55947739976768, 126.94575072929152, 126.94771947070845
            );
        }

        public static LocationSearchCondRequest 모던샤브하우스만_포함한_검색_영역_요청() {
            return new LocationSearchCondRequest(
                    37.56999357685741, 37.57216200735907, 126.97851187149867, 126.97949624220713
            );
        }

        public static LocationSearchCondRequest 대한민국_전체를_포함한_검색_영역_요청() {
            return new LocationSearchCondRequest(
                    31.555663574665545, 40.59357169075871, 125.66602297769718, 129.7199780558222
            );
        }

        public static RestaurantPoint 영역에_포함된_임의의_지점(
                double lowLatitude,
                double highLatitude,
                double lowLongitude,
                double highLongitude
        ) {
            Random rand = new Random();
            double randomLatitude = lowLatitude + (highLatitude - lowLatitude) * rand.nextDouble();
            double randomLongitude = lowLongitude + (highLongitude - lowLongitude) * rand.nextDouble();
            return new RestaurantPoint(randomLatitude, randomLongitude);
        }
    }
}
