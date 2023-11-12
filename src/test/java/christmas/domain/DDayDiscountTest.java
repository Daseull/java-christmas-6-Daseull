package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.menu.Menu;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DDayDiscountTest {
    EventPolicy dDayDiscount = new DDayDiscount();

    @DisplayName("10000원 미만인 경우 할인 적용되지 않는다.")
    @Test
    void notApplicableOrder() {
        Map<Menu, Integer> menus = new EnumMap<Menu, Integer>(Menu.class);
        menus.put(Menu.fromDescription("타파스"), 1);

        assertThat(dDayDiscount.canBeApplied(new Date(25), new Order(menus)))
                .isEqualTo(false);
        assertThat(dDayDiscount.amount(new Date(25), new Order(menus)))
                .isEqualTo(0);
    }

    @DisplayName("할인 기간이 아니면 적용되지 않는다.")
    @ParameterizedTest
    @CsvSource({"25,true", "26,false"})
    void notApplicableDate(int dateSource, boolean expected) {
        assertThat(dDayDiscount.canBeApplied(new Date(dateSource), createOrder()))
                .isEqualTo(expected);
    }

    @DisplayName("할인 금액 테스트")
    @ParameterizedTest
    @CsvSource({"1,-1000", "25,-3400"})
    void amount(int dateSource, int expectedAmount) {
        assertThat(dDayDiscount.amount(new Date(dateSource), createOrder()))
                .isEqualTo(expectedAmount);
    }

    Order createOrder() {
        Map<Menu, Integer> menus = new EnumMap<Menu, Integer>(Menu.class);
        menus.put(Menu.fromDescription("타파스"), 2);
        menus.put(Menu.fromDescription("시저샐러드"), 1);
        menus.put(Menu.fromDescription("해산물파스타"), 2);
        menus.put(Menu.fromDescription("초코케이크"), 1);
        menus.put(Menu.fromDescription("제로콜라"), 1);
        menus.put(Menu.fromDescription("샴페인"), 1);
        return new Order(menus);
    }
}
