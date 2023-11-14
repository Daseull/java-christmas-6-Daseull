package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.controller.MenuCount;
import christmas.domain.Date;
import christmas.domain.Order;
import christmas.domain.menu.Menu;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EventTest {

    @DisplayName("할인이 적용되는 항목들만 반환한다.")
    @Test
    void benefitDetails() {
        Date specialWeekday = new Date(31);
        Map<Menu, Integer> menus = new EnumMap<Menu, Integer>(Menu.class);
        menus.put(Menu.fromDescription("양송이수프"), 1);
        menus.put(Menu.fromDescription("아이스크림"), 1);

        Map<Event, Integer> result = Event.benefitDetails(specialWeekday, new Order(menus));

        assertThat(result.keySet()).containsOnly(Event.WEEKDAY_DISCOUNT, Event.SPECIAL_DISCOUNT);
    }

    @DisplayName("증정품 증정 테스트")
    @Test
    void giveGiveaway() {
        Date date = new Date(1);
        Map<Menu, Integer> menus = new EnumMap<Menu, Integer>(Menu.class);
        menus.put(Menu.fromDescription("양송이수프"), 2);

        Optional<MenuCount> menuCount = Event.giveGiveaway(date, new Order(menus));

        assertThat(menuCount).isPresent();
        assertThat(menuCount.get().menu()).isEqualTo(Menu.CHAMPAGNE.description());
        assertThat(menuCount.get().count()).isEqualTo(1);
    }

    @DisplayName("증정품 증정 테스트 - 증정품이 없는 경우")
    @Test
    void giveNoGiveaway() {
        Date date = new Date(1);
        Map<Menu, Integer> menus = new EnumMap<Menu, Integer>(Menu.class);
        menus.put(Menu.fromDescription("타파스"), 2);

        Optional<MenuCount> menuCount = Event.giveGiveaway(date, new Order(menus));

        assertThat(menuCount).isEmpty();
    }

    @DisplayName("할인 금액 테스트")
    @ParameterizedTest
    @CsvSource(value = {"25,2,3,-10469", "1,4,1,-9092"})
    void totalDiscount(int dateSource, int numMain, int numDessert, int expectedAmount) {
        Date date = new Date(dateSource);
        Map<Menu, Integer> menus = new EnumMap<Menu, Integer>(Menu.class);
        menus.put(Menu.fromDescription("티본스테이크"), numMain);
        menus.put(Menu.fromDescription("아이스크림"), numDessert);

        assertThat(Event.totalDiscount(date, new Order(menus))).isEqualTo(expectedAmount);
    }

    @DisplayName("혜택 금액은 총 할인 금액과 증정품 금액을 합친 금액이다.")
    @ParameterizedTest
    @CsvSource(value = {"25,2,3,-35469", "1,4,1,-34092"})
    void totalBenefit(int dateSource, int numMain, int numDessert, int expectedAmount) {
        Date date = new Date(dateSource);
        Map<Menu, Integer> menus = new EnumMap<Menu, Integer>(Menu.class);
        menus.put(Menu.fromDescription("티본스테이크"), numMain);
        menus.put(Menu.fromDescription("아이스크림"), numDessert);

        assertThat(Event.totalBenefit(date, new Order(menus))).isEqualTo(expectedAmount);
    }
}
