package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.dto.MenuCount;
import christmas.domain.event.Event;
import christmas.domain.menu.Menu;
import christmas.exception.ErrorMessage;
import christmas.exception.PromotionException;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OrderTest {
    Menus menus = new Menus();

    @DisplayName("주문 도메인 생성 테스트")
    @Test
    void create() {
        menus.add("타파스", 3);
        menus.add("초코케이크", 7);
        menus.add("레드와인", 1);

        assertDoesNotThrow(() -> new Order(new Date(1), menus));
    }

    @DisplayName("음료만 주문 시 예외 발생")
    @Test
    void onlyDessert() {
        menus.add(("샴페인"), 3);
        menus.add(("레드와인"), 7);

        assertThatThrownBy(() -> new Order(new Date(1), menus))
                .isInstanceOf(PromotionException.class)
                .hasMessage(ErrorMessage.INVALID_ORDER_MESSAGE.message());
    }

    @DisplayName("할인이 적용되는 항목들만 반환한다.")
    @Test
    void benefitDetails() {
        Date specialWeekday = new Date(31);
        Menus menus = new Menus();
        menus.add(("양송이수프"), 1);
        menus.add(("아이스크림"), 1);
        Order order = new Order(specialWeekday, menus);

        Map<Event, Integer> result = order.benefitDetails();

        assertThat(result.keySet()).containsOnly(Event.WEEKDAY_DISCOUNT, Event.SPECIAL_DISCOUNT);
    }

    @DisplayName("증정품 증정 테스트")
    @Test
    void giveGiveaway() {
        Date date = new Date(1);
        Menus menus = new Menus();
        menus.add(("양송이수프"), 2);
        Order order = new Order(date, menus);

        Optional<MenuCount> menuCount = order.giveGiveaway();

        assertThat(menuCount).isPresent();
        assertThat(menuCount.get().menu()).isEqualTo(Menu.CHAMPAGNE.description());
        assertThat(menuCount.get().count()).isEqualTo(1);
    }

    @DisplayName("증정품 증정 테스트 - 증정품이 없는 경우")
    @Test
    void giveNoGiveaway() {
        Date date = new Date(1);
        Menus menus = new Menus();
        menus.add(("타파스"), 2);
        Order order = new Order(date, menus);

        Optional<MenuCount> menuCount = order.giveGiveaway();

        assertThat(menuCount).isEmpty();
    }

    @DisplayName("할인 금액 테스트")
    @ParameterizedTest
    @CsvSource(value = {"25,2,3,-10469", "1,4,1,-9092"})
    void totalDiscount(int dateSource, int numMain, int numDessert, int expectedAmount) {
        Date date = new Date(dateSource);
        Menus menus = new Menus();
        menus.add(("티본스테이크"), numMain);
        menus.add(("아이스크림"), numDessert);

        Order order = new Order(date, menus);

        assertThat(order.totalDiscount()).isEqualTo(expectedAmount);
    }

    @DisplayName("혜택 금액은 총 할인 금액과 증정품 금액을 합친 금액이다.")
    @ParameterizedTest
    @CsvSource(value = {"25,2,3,-35469", "1,4,1,-34092"})
    void totalBenefit(int dateSource, int numMain, int numDessert, int expectedAmount) {
        Date date = new Date(dateSource);
        Menus menus = new Menus();
        menus.add(("티본스테이크"), numMain);
        menus.add(("아이스크림"), numDessert);

        Order order = new Order(date, menus);

        assertThat(order.totalBenefit()).isEqualTo(expectedAmount);
    }
}
