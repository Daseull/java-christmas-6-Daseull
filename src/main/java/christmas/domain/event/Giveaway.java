package christmas.domain.event;

import christmas.domain.Date;
import christmas.domain.Menus;
import christmas.domain.menu.Menu;
import christmas.dto.MenuCount;
import java.util.Optional;


public class Giveaway implements EventPolicy {
    private static final int MIN_TOTAL_AMOUNT = 120_000; //shadowing
    private static final Menu giveaway = Menu.CHAMPAGNE;
    private static final int numGiveaway = 1;

    @Override
    public boolean canBeApplied(Date date, Menus menus) {
        return menus.totalAmount() >= MIN_TOTAL_AMOUNT;
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
