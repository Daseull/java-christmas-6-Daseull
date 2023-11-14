package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Date;
import christmas.domain.Menus;
import christmas.domain.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GiveawayTest {
    Giveaway giveaway = new Giveaway();
    Menus menus = new Menus();

    @DisplayName("주문 금액이 12000원 이상인 경우에만 할인이 적용된다.")
    @ParameterizedTest
    @CsvSource({"양송이수프,true", "타파스,false"})
    void notApplicableOrder(String menu, boolean expected) {
        menus.add((menu), 2);

        assertThat(giveaway.canBeApplied(new Date(1), new Order(menus)))
                .isEqualTo(expected);
    }

    @DisplayName("할인 금액 테스트")
    @ParameterizedTest
    @CsvSource({"양송이수프,-25000", "타파스,0"})
    void amount(String menu, int expectedAmount) {
        menus.add((menu), 2);

        assertThat(giveaway.amount(new Date(1), new Order(menus)))
                .isEqualTo(expectedAmount);
    }

    @DisplayName("증정품 정보 - 주문 금액이 12000원 이상인 경우에만 증정품을 받을 수 있다.")
    @Test
    void receiveGiveaway() {
        menus.add(("바비큐립"), 2);

        assertThat(giveaway.giveGiveaway(new Date(1), new Order(menus)))
                .isPresent();
    }

    @DisplayName("증정품 정보 - 주문 금액이 12000원 미만이면 빈 객체를 반환한다.")
    @Test
    void receiveNoGiveaway() {
        menus.add(("타파스"), 2);

        assertThat(giveaway.giveGiveaway(new Date(1), new Order(menus)))
                .isEmpty();
    }
}
