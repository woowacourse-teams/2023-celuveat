package com.celuveat.restaurant.presentation;

import static com.celuveat.restaurant.exception.RestaurantExceptionType.UNSUPPORTED_CATEGORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.common.exception.BaseException;
import com.celuveat.common.exception.BaseExceptionType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("카테고리 매퍼(Category Mapper) 은(는)")
class CategoryMapperTest {

    @Test
    void 카테고리가_null이면_빈리스트를_반환한다() {
        // when
        List<String> result = CategoryMapper.mapCategory(null);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void 카테고리가_빈문자열이면_빈리스트를_반환한다() {
        // when
        List<String> result = CategoryMapper.mapCategory("");

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void 카테고리가_등록된_카테고리면_매핑된_카테고리들을_반환한다() {
        // when
        List<String> result = CategoryMapper.mapCategory("일식");

        // then
        assertThat(result).containsExactly(
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
        );
    }

    @Test
    void 카테고리가_등록되지_않은_카테고리면_예외가_발생한다() {
        // when
        BaseExceptionType result = assertThrows(BaseException.class,
                () -> CategoryMapper.mapCategory("없는카테고리")
        ).exceptionType();

        // then
        assertThat(result).isEqualTo(UNSUPPORTED_CATEGORY);
    }
}
