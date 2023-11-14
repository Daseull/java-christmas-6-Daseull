package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {
    Menus menus = new Menus();

    @DisplayName("주문 도메인 생성 테스트")
    @Test
    void create() {
        menus.add("타파스", 3);
        menus.add("초코케이크", 7);
        menus.add("레드와인", 1);

        assertDoesNotThrow(() -> new Order(menus));
    }

    @DisplayName("음료만 주문 시 예외 발생")
    @Test
    void onlyDessert() {
        menus.add(("샴페인"), 3);
        menus.add(("레드와인"), 7);

        assertThatThrownBy(() -> new Order(menus))
                .isInstanceOf(PlannerException.class)
                .hasMessage(ErrorMessage.INVALID_ORDER_MESSAGE.message());
    }
}
