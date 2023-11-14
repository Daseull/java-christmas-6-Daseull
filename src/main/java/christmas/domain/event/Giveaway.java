package christmas.domain.event;

import christmas.controller.MenuCount;
import christmas.domain.Date;
import christmas.domain.Menus;
import christmas.domain.menu.Menu;
import java.util.Optional;


public class Giveaway implements EventPolicy {
    private static final int MIN_TOTAL_PRICE = 12_000; //shadowing
    private static final Menu giveaway = Menu.CHAMPAGNE;
    private static final int numGiveaway = 1;

    @Override
    public boolean canBeApplied(Date date, Menus menus) {
        return menus.totalPrice() >= MIN_TOTAL_PRICE;
    }

    @Override
    public int amount(Date date, Menus menus) {
        if (canBeApplied(date, menus)) {
            return -1 * numGiveaway * giveaway.price();
        }
        return 0;
    }

    public Optional<MenuCount> giveGiveaway(Date date, Menus menus) {
        if (canBeApplied(date, menus)) {
            return Optional.of(new MenuCount(giveaway.description(), numGiveaway));
        }
        return Optional.empty();
    }
}
