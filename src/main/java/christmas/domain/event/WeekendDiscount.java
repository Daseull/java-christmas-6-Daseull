package christmas.domain.event;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;

import christmas.domain.Date;
import christmas.domain.Order;
import christmas.domain.menu.Category;
import java.time.DayOfWeek;
import java.util.List;

public class WeekendDiscount implements EventPolicy {
    private static final int DISCOUNT_UNIT = -2_023;

    @Override
    public boolean canBeApplied(Date date, Order order) {
        return isApplicableOrder(order) && isWeekend(date.dayOfWeek());
    }

    @Override
    public int amount(Date date, Order order) {
        if(canBeApplied(date, order)){
            return order.countByCategory(Category.MAIN) * DISCOUNT_UNIT;
        }
        return 0;
    }

    private boolean isWeekend(DayOfWeek dayOfWeek) {
        List<DayOfWeek> weekend = List.of(FRIDAY, SATURDAY);
        return weekend.contains(dayOfWeek);
    }
}
