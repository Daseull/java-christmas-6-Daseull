package christmas.domain;

import java.util.List;

public class SpecialDiscount implements EventPolicy {
    private static final List<Integer> starredDates = List.of(3, 10, 17, 24, 25, 31);
    private static final int DISCOUNT_UNIT = -1_000;

    @Override
    public boolean canBeApplied(Date date, Order order) {
        return isApplicableOrder(order) && isStarredDate(date);
    }

    @Override
    public int amount(Date date, Order order) {
        if (canBeApplied(date, order)) {
            return DISCOUNT_UNIT;
        }
        return 0;
    }

    private boolean isStarredDate(Date date) {
        return date.isIncluded(starredDates);
    }
}
