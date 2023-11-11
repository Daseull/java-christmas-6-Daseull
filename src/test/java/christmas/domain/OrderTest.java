package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.domain.menu.Menu;
import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;
import java.util.EnumMap;
import java.util.Map;
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

        assertDoesNotThrow(() -> new Order(menus));
    }

    @DisplayName("디저트만 주문 시 예외 발생")
    @Test
    void onlyDessert() {
        menus.put(Menu.fromDescription("아이스크림"), 3);
        menus.put(Menu.fromDescription("초코케이크"), 7);

        assertThatThrownBy(() -> new Order(menus))
                .isInstanceOf(PlannerException.class)
                .hasMessage(ErrorMessage.INVALID_ORDER_MESSAGE.message());
    }

    @DisplayName("디저트만 주문 시 예외 발생")
    @Test
    void overCount() {
        menus.put(Menu.fromDescription("타파스"), 10);
        menus.put(Menu.fromDescription("초코케이크"), 9);
        menus.put(Menu.fromDescription("레드와인"), 8);

        assertThatThrownBy(() -> new Order(menus))
                .isInstanceOf(PlannerException.class)
                .hasMessage(ErrorMessage.INVALID_ORDER_MESSAGE.message());
    }

    @DisplayName("총 주문 금액 테스트")
    @Test
    void totalPrice() {
        menus.put(Menu.fromDescription("티본스테이크"), 1);
        menus.put(Menu.fromDescription("바비큐립"), 1);
        menus.put(Menu.fromDescription("초코케이크"), 2);
        menus.put(Menu.fromDescription("제로콜라"), 1);

        Order order = new Order(menus);

        assertThat(order.totalPrice())
                .isEqualTo(142000);
    }
}
