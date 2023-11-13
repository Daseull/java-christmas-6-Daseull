package christmas.domain.event;

import christmas.domain.Date;
import christmas.domain.Order;
import christmas.domain.menu.Menu;

public class Giveaway implements EventPolicy {
    private static final int MIN_TOTAL_PRICE = 12_000; //shadowing
    private static final Menu giveaway = Menu.CHAMPAGNE;
    private static final int numGiveaway = 1;

    @Override
    public boolean canBeApplied(Date date, Order order) {
        return order.totalPrice() >= MIN_TOTAL_PRICE;
    }

    @Override
    public int amount(Date date, Order order) {
        if (canBeApplied(date, order)) {
            return -1 * numGiveaway * giveaway.price();
        }
        return 0;
    }
}
