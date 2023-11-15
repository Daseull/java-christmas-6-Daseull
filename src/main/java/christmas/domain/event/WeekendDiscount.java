package christmas.domain.event;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;

import christmas.domain.Date;
import christmas.domain.Menus;
import christmas.domain.menu.Category;
import java.time.DayOfWeek;
import java.util.List;

public class WeekendDiscount implements EventPolicy {
    private static final int DISCOUNT_UNIT = -2_023;

    @Override
    public boolean canBeApplied(Date date, Menus menus) {
        return isApplicableMenus(menus) && isWeekend(date.dayOfWeek());
    }

    @Override
    public int amount(Date date, Menus menus) {
        if (canBeApplied(date, menus)) {
            return menus.countByCategory(Category.MAIN) * DISCOUNT_UNIT;
        }
        return NONE;
    }

    private boolean isWeekend(DayOfWeek dayOfWeek) {
        List<DayOfWeek> weekend = List.of(FRIDAY, SATURDAY);
        return weekend.contains(dayOfWeek);
    }
}
