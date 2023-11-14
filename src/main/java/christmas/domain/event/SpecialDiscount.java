package christmas.domain.event;

import christmas.domain.Date;
import christmas.domain.Menus;
import java.util.List;

public class SpecialDiscount implements EventPolicy {
    private static final List<Integer> starredDates = List.of(3, 10, 17, 24, 25, 31);
    private static final int DISCOUNT_UNIT = -1_000;

    @Override
    public boolean canBeApplied(Date date, Menus menus) {
        return isApplicableMenus(menus) && isStarredDate(date);
    }

    @Override
    public int amount(Date date, Menus menus) {
        if (canBeApplied(date, menus)) {
            return DISCOUNT_UNIT;
        }
        return 0;
    }

    private boolean isStarredDate(Date date) {
        return date.isIncluded(starredDates);
    }
}
