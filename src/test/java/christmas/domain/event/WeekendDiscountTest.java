package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Date;
import christmas.domain.Menus;
import christmas.domain.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WeekendDiscountTest {
    WeekendDiscount weekendDiscount = new WeekendDiscount();
    Date weekendDate = new Date(1);
    Date weekdayDate = new Date(25);

    @DisplayName("10000원 미만인 경우 할인 적용되지 않는다.")
    @Test
    void notApplicableOrder() {
        Menus menus = new Menus();
        menus.add(("타파스"), 1);

        assertThat(weekendDiscount.canBeApplied(weekdayDate, new Order(menus)))
                .isEqualTo(false);
    }

    @DisplayName("주말이 아니면 적용되지 않는다.")
    @Test
    void notApplicableDate() {
        assertThat(weekendDiscount.canBeApplied(weekendDate, createOrder())).isEqualTo(true);
        assertThat(weekendDiscount.canBeApplied(weekdayDate, createOrder())).isEqualTo(false);
    }

    @DisplayName("할인 금액 테스트")
    @Test
    void amount() {
        assertThat(weekendDiscount.amount(weekendDate, createOrder())).isEqualTo(-6069);
    }

    Order createOrder() {
        Menus menus = new Menus();
        menus.add(("타파스"), 2);
        menus.add(("티본스테이크"), 1);
        menus.add(("해산물파스타"), 2);
        menus.add(("초코케이크"), 2);
        menus.add(("제로콜라"), 1);
        menus.add(("샴페인"), 1);
        return new Order(menus);
    }
}
