package christmas.domain.event;

import christmas.domain.Date;
import christmas.domain.Menus;

public interface EventPolicy {
    int MIN_TOTAL_AMOUNT = 10_000;
    int NONE = 0;


    default boolean isApplicableMenus(Menus menus) {
        return menus.totalAmount() >= MIN_TOTAL_AMOUNT;
    }

    boolean canBeApplied(Date date, Menus menus);

    int amount(Date date, Menus menus);
}
