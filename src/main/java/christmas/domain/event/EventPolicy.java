package christmas.domain.event;

import christmas.domain.Date;
import christmas.domain.Menus;

public interface EventPolicy {
    int MIN_TOTAL_PRICE = 10_000;

    default boolean isApplicableMenus(Menus menus) {
        return menus.totalPrice() >= MIN_TOTAL_PRICE;
    }

    boolean canBeApplied(Date date, Menus menus);

    int amount(Date date, Menus menus);
}
