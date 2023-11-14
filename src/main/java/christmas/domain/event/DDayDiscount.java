package christmas.domain.event;

import christmas.domain.Date;
import christmas.domain.Menus;

public class DDayDiscount implements EventPolicy {
    private static final int FIRST_DATE = 1;
    private static final int LAST_DATE = 25;
    private static final int DISCOUNT_UNIT = -100;
    private static final int BOTTOM_DISCOUNT_AMOUNT = -1_000;

    @Override
    public boolean canBeApplied(Date date, Menus menus) {
        return isApplicableMenus(menus) && date.isInRange(FIRST_DATE, LAST_DATE);
    }

    @Override
    public int amount(Date date, Menus menus) {
        if (canBeApplied(date, menus)) {
            return BOTTOM_DISCOUNT_AMOUNT + date.dayFromDate(FIRST_DATE) * DISCOUNT_UNIT;
        }
        return 0;
    }
}
