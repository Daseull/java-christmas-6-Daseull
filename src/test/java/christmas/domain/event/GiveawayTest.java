package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Date;
import christmas.domain.Order;
import christmas.domain.menu.Menu;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GiveawayTest {
    Giveaway giveaway = new Giveaway();

    @DisplayName("주문 금액이 12000원 이상인 경우에만 할인이 적용된다.")
    @ParameterizedTest
    @CsvSource({"양송이수프,true", "타파스,false"})
    void notApplicableOrder(String menu, boolean expected) {
        Map<Menu, Integer> menus = new EnumMap<Menu, Integer>(Menu.class);
        menus.put(Menu.fromDescription(menu), 2);

        assertThat(giveaway.canBeApplied(new Date(1), new Order(menus)))
                .isEqualTo(expected);
    }

    @DisplayName("할인 금액 테스트")
    @Test
    void amount() {
        Map<Menu, Integer> menus = new EnumMap<Menu, Integer>(Menu.class);
        menus.put(Menu.fromDescription("샴페인"), 2);

        assertThat(giveaway.amount(new Date(1), new Order(menus)))
                .isEqualTo(-25000);
    }
}
