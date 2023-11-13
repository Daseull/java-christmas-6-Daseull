package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Date;
import christmas.domain.Order;
import christmas.domain.menu.Menu;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
