package christmas.domain;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import christmas.domain.menu.Category;
import java.time.DayOfWeek;
import java.util.List;

public class WeekdayDiscount implements EventPolicy {
    private static final int DISCOUNT_UNIT = -2_023;

    @Override
    public boolean canBeApplied(Date date, Order order) {
        return isApplicableOrder(order) && isWeekDay(date);
    }

    @Override
    public int amount(Date date, Order order) {
        if (canBeApplied(date, order)) {
            return order.countByCategory(Category.DESSERT) * DISCOUNT_UNIT;
        }
        return 0;
    }

    private boolean isWeekDay(Date date) {
        List<DayOfWeek> weekDay = List.of(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY);
        return weekDay.contains(date.dayOfWeek());
    }
}
