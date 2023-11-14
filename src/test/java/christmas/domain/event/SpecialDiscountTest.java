package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Date;
import christmas.domain.Menus;
import christmas.domain.Order;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpecialDiscountTest {
    SpecialDiscount specialDiscount = new SpecialDiscount();
    List<Integer> starredDates = List.of(3, 10, 17, 24, 25, 31);

    @DisplayName("10000원 미만인 경우 할인 적용되지 않는다.")
    @Test
    void notApplicableOrder() {

        Menus menus = new Menus();
        menus.add(("타파스"), 1);

        assertThat(specialDiscount.canBeApplied(new Date(starredDates.stream().findAny().get()), new Order(menus)))
                .isEqualTo(false);
    }

    @DisplayName("정해진 날짜가 아니면 할인이 적용되지 않는다.")
    @Test
    void notApplicableDate() {
        assertThat(specialDiscount.canBeApplied(new Date(3), createOrder())).isEqualTo(true);
        assertThat(specialDiscount.canBeApplied(new Date(1), createOrder())).isEqualTo(false);
    }

    @DisplayName("할인 금액 테스트")
    @Test
    void amount() {
        assertThat(specialDiscount.amount(new Date(starredDates.stream().findAny().get()), createOrder()))
                .isEqualTo(-1000);
    }

    Order createOrder() {
        Menus menus = new Menus();
        menus.add(("타파스"), 2);
        menus.add(("시저샐러드"), 1);
        menus.add(("해산물파스타"), 2);
        menus.add(("초코케이크"), 2);
        menus.add(("제로콜라"), 1);
        menus.add(("샴페인"), 1);
        return new Order(menus);
    }
}
