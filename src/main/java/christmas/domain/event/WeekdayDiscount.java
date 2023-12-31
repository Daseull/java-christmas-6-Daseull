package christmas.domain.event;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import christmas.domain.Date;
import christmas.domain.Menus;
import christmas.domain.menu.Category;
import java.time.DayOfWeek;
import java.util.List;

public class WeekdayDiscount implements EventPolicy {
    private static final int DISCOUNT_UNIT = -2_023;

    @Override
    public boolean canBeApplied(Date date, Menus menus) {
        return isApplicableMenus(menus) && isWeekday(date.dayOfWeek());
    }

    @Override
    public int amount(Date date, Menus menus) {
        if (canBeApplied(date, menus)) {
            return menus.countByCategory(Category.DESSERT) * DISCOUNT_UNIT;
        }
        return NONE;
    }

    private boolean isWeekday(DayOfWeek dayOfWeek) {
        List<DayOfWeek> weekday = List.of(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY);
        return weekday.contains(dayOfWeek);
    }
}
