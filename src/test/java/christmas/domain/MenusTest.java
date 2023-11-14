package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.domain.menu.Category;
import christmas.exception.ErrorMessage;
import christmas.exception.PromotionException;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MenusTest {
    Menus menus = new Menus();

    @DisplayName("메뉴 추가 테스트")
    @Test
    void add() {
        List<String> menus = List.of("양송이수프", "초코케이크", "레드와인");
        List<Integer> counts = List.of(1, 2, 2);

        IntStream.range(0, menus.size())
                .forEach(i -> assertDoesNotThrow(() -> this.menus.add(menus.get(i), counts.get(i))));

    }

    @DisplayName("메뉴 추가 - 중복, 없는메뉴, 개수가 1개이상이 아닌 경우 _ 예외 발생")
    @ParameterizedTest
    @CsvSource(value = {"양송이수프,3", "없는 메뉴,7", "크리스마스파스타,0"})
    void invalidOrder(String menu, int count) {
        menus.add("양송이수프", 1);

        assertThatThrownBy(() -> menus.add(menu, count))
                .isInstanceOf(PromotionException.class)
                .hasMessage(ErrorMessage.INVALID_ORDER_MESSAGE.message());
    }

    @DisplayName("20개 초과 주문시 예외 발생")
    @Test
    void overCount() {
        menus.add("타파스", 10);
        menus.add("초코케이크", 9);

        assertThatThrownBy(() -> menus.add(("레드와인"), 8))
                .isInstanceOf(PromotionException.class)
                .hasMessage(ErrorMessage.INVALID_ORDER_MESSAGE.message());
    }

    @DisplayName("총 주문 금액 테스트")
    @Test
    void totalAmount() {
        menus.add(("티본스테이크"), 1);
        menus.add(("바비큐립"), 1);
        menus.add(("초코케이크"), 2);
        menus.add(("제로콜라"), 1);

        assertThat(menus.totalAmount())
                .isEqualTo(142000);
    }

    @DisplayName("특정 카테고리의 메뉴 개수 구하기 테스트")
    @ParameterizedTest
    @CsvSource({"APPETIZER,3", "MAIN,2", "DESSERT,1", "BEVERAGE,2"})
    void countByCategory(String categoryName, int expectedCount) {
        menus.add("타파스", 2);
        menus.add(("시저샐러드"), 1);
        menus.add(("해산물파스타"), 2);
        menus.add(("초코케이크"), 1);
        menus.add(("제로콜라"), 1);
        menus.add(("샴페인"), 1);

        assertThat(menus.countByCategory(Category.valueOf(categoryName)))
                .isEqualTo(expectedCount);
    }
}
