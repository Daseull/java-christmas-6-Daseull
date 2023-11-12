package christmas.domain;

public interface EventPolicy {
    int MIN_TOTAL_PRICE = 10_000;

    default boolean isApplicableOrder(Order order) {
        return order.totalPrice() >= MIN_TOTAL_PRICE;
    }

    boolean canBeApplied(Date date, Order order);

    int amount(Date date, Order order);
}
