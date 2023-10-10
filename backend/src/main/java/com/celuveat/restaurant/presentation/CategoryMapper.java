package com.celuveat.restaurant.presentation;

import static com.celuveat.restaurant.exception.RestaurantExceptionType.UNSUPPORTED_CATEGORY;

import com.celuveat.restaurant.exception.RestaurantException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CategoryMapper {

    private static Map<String, List<String>> mapper = Map.ofEntries(
            Map.entry("한식", List.of(
                    "감자탕",
                    "게요리",
                    "곰탕, 설렁탕",
                    "곱창, 막창, 양",
                    "국수",
                    "기사식당",
                    "김밥",
                    "낙지요리",
                    "냉면",
                    "농수산물",
                    "닭볶음탕",
                    "대게요리",
                    "두부요리",
                    "막국수",
                    "백반,가정식",
                    "백숙,삼계탕",
                    "보리밥",
                    "보쌈",
                    "수육",
                    "시장",
                    "쌈밥",
                    "아귀찜,해물찜",
                    "전, 빈대떡",
                    "족발,보쌈",
                    "주꾸미요리",
                    "쭈꾸미요리",
                    "찜닭",
                    "추어탕",
                    "칼국수, 만두",
                    "칼국수,만두",
                    "한식",
                    "한정식",
                    "해장국"
            )),
            Map.entry("중식", List.of(
                    "딤섬, 중식만두",
                    "딤섬,중식만두",
                    "양꼬치",
                    "중국집",
                    "중식",
                    "중식당"
            )),
            Map.entry("일식", List.of(
                    "덮밥",
                    "돈가스",
                    "샤브샤브",
                    "오뎅, 꼬치",
                    "우동,소바",
                    "이자카야",
                    "일본식라면",
                    "일식",
                    "일식,초밥뷔페",
                    "일식당",
                    "일식튀김,꼬치",
                    "초밥, 롤",
                    "초밥,롤"
            )),
            Map.entry("양식", List.of(
                    "뷔페",
                    "스테이크",
                    "스테이크, 립",
                    "스파게티,파스타전문",
                    "양식",
                    "이탈리아음식",
                    "패밀리레스토랑",
                    "프랑스음식",
                    "피자"
            )),
            Map.entry("치킨, 피자, 햄버거", List.of(
                    "치킨,닭강정",
                    "피자",
                    "햄버거"
            )),
            Map.entry("회, 수산물", List.of(
                    "게요리",
                    "대게요리",
                    "바닷가재요리",
                    "해물, 생선요리",
                    "생선회",
                    "수산물",
                    "아귀찜,해물찜",
                    "오징어요리",
                    "장어, 먹장어요리",
                    "장어,먹장어요리",
                    "조개요리",
                    "해물",
                    "해물,생선요리",
                    "해산물",
                    "해산물뷔페",
                    "회"
            )),
            Map.entry("고기", List.of(
                    "고기뷔페",
                    "곱창",
                    "곱창, 막창, 양",
                    "곱창,막창,양",
                    "닭요리",
                    "돼지고기",
                    "돼지고기구이",
                    "소고기",
                    "소고기구이",
                    "수육",
                    "스테이크",
                    "스테이크, 립",
                    "양갈비",
                    "양고기",
                    "양꼬치",
                    "오리고기",
                    "오리요리",
                    "육류, 고기요리",
                    "육류,고기요리",
                    "정육점",
                    "족발,보쌈"
            )),
            Map.entry("아시안", List.of(
                    "베트남음식",
                    "아시아음식"
            )),
            Map.entry("국, 국밥", List.of(
                    "국밥",
                    "곰탕, 설렁탕",
                    "곰탕,설렁탕",
                    "순대,순댓국",
                    "순대, 순댓국",
                    "순댓국, 순대"
            )),
            Map.entry("카페, 디저트", List.of(
                    "베이커리",
                    "카페",
                    "카페,디저트"
            )),
            Map.entry("면류", List.of(
                    "국수",
                    "냉면",
                    "라면",
                    "막국수",
                    "우동,소바",
                    "일본식라면"
            )),
            Map.entry("분식", List.of(
                    "가공식품",
                    "김밥",
                    "떡볶이",
                    "라면",
                    "만두",
                    "분식",
                    "오뎅, 꼬치",
                    "종합분식",
                    "포장마차"
            )),
            Map.entry("세계요리", List.of(
                    "독일음식",
                    "멕시코, 남미음식",
                    "멕시코,남미음식",
                    "베트남음식",
                    "아시아음식",
                    "태국음식"
            )),
            Map.entry("탕, 찌개", List.of(
                    "닭볶음탕",
                    "백숙,삼계탕",
                    "아귀찜,해물찜",
                    "찌개, 전골",
                    "찌개,전골",
                    "찜닭",
                    "추어탕",
                    "곰탕, 설렁탕",
                    "해장국"
            )),
            Map.entry("주점", List.of(
                    "맥주, 호프",
                    "맥주,호프",
                    "바(BAR)",
                    "술집",
                    "와인",
                    "요리주점",
                    "이자카야",
                    "야식",
                    "전통,민속주점"
            ))
    );

    private CategoryMapper() {
    }

    public static List<String> mapCategory(String category) {
        if (Objects.isNull(category) || category.isEmpty()) {
            return Collections.emptyList();
        }
        if (mapper.containsKey(category)) {
            return mapper.get(category);
        }
        throw new RestaurantException(UNSUPPORTED_CATEGORY);
    }
}
