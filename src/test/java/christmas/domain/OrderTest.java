package christmas.domain;

import christmas.domain.menu.Menu;
import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {
    Map<Menu, Integer> menus = new EnumMap<Menu, Integer>(Menu.class);

    @DisplayName("주문 도메인 생성 테스트")
    @Test
    void create() {
        menus.put(Menu.fromDescription("타파스"), 3);
        menus.put(Menu.fromDescription("초코케이크"), 7);
        menus.put(Menu.fromDescription("레드와인"), 1);

        Assertions.assertDoesNotThrow(() -> new Order(menus));
    }

    @DisplayName("디저트만 주문 시 예외 발생")
    @Test
    void onlyDessert() {
        menus.put(Menu.fromDescription("아이스크림"), 3);
        menus.put(Menu.fromDescription("초코케이크"), 7);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> new Order(menus))
                .isInstanceOf(PlannerException.class)
                .hasMessage(ErrorMessage.INVALID_ORDER_MESSAGE.message());
    }

    @DisplayName("디저트만 주문 시 예외 발생")
    @Test
    void overCount() {
        menus.put(Menu.fromDescription("타파스"), 10);
        menus.put(Menu.fromDescription("초코케이크"), 9);
        menus.put(Menu.fromDescription("레드와인"), 8);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> new Order(menus))
                .isInstanceOf(PlannerException.class)
                .hasMessage(ErrorMessage.INVALID_ORDER_MESSAGE.message());
    }
}
