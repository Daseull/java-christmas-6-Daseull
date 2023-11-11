package christmas.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OrderMenuTest {

    OrderMenu orderMenu = new OrderMenu();

    @DisplayName("메뉴 추가 테스트")
    @Test
    void add() {
        List<String> menus = List.of("양송이수프", "초코케이크", "레드와인");
        List<Integer> counts = List.of(1, 2, 2);

        IntStream.range(0, menus.size())
                .forEach(i -> assertDoesNotThrow(() -> orderMenu.add(menus.get(i), counts.get(i))));

    }

    @DisplayName("메뉴 중복")
    @ParameterizedTest
    @CsvSource(value = {"양송이수프,3", "없는 메뉴,7", "크리스마스파스타,0"})
    void invalidOrder(String menu, int count) {
        orderMenu.add("양송이수프", 1);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> orderMenu.add(menu, count))
                .isInstanceOf(PlannerException.class)
                .hasMessage(ErrorMessage.INVALID_ORDER_MESSAGE.message());
    }
}
