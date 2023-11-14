package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Date;
import christmas.domain.Menus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GiveawayTest {
    Giveaway giveaway = new Giveaway();

    @DisplayName("주문 금액이 120000원 이상인 경우에만 증정이 적용된다.")
    @ParameterizedTest
    @CsvSource({"20,true", "19,false"})
    void notApplicableOrder(int count, boolean expected) {
        Menus menus = new Menus();
        menus.add(("양송이수프"), count);

        assertThat(giveaway.canBeApplied(new Date(1), menus))
                .isEqualTo(expected);
    }

    @DisplayName("할인 금액 테스트")
    @ParameterizedTest
    @CsvSource({"티본스테이크,-25000", "타파스,0"})
    void amount(String menu, int expectedAmount) {
        Menus menus = new Menus();
        menus.add((menu), 3);

        assertThat(giveaway.amount(new Date(1), menus))
                .isEqualTo(expectedAmount);
    }

    @DisplayName("증정품 정보 - 주문 금액이 120000원 이상인 경우에만 증정품을 받을 수 있다.")
    @Test
    void receiveGiveaway() {
        Menus menus = new Menus();
        menus.add(("바비큐립"), 3);

        assertThat(giveaway.giveGiveaway(new Date(1), menus))
                .isPresent();
    }

    @DisplayName("증정품 정보 - 주문 금액이 120000원 미만이면 빈 객체를 반환한다.")
    @Test
    void receiveNoGiveaway() {
        Menus menus = new Menus();
        menus.add(("바비큐립"), 2);

        assertThat(giveaway.giveGiveaway(new Date(1), menus))
                .isEmpty();
    }
}
