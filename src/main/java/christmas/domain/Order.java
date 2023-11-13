package christmas.domain;

import christmas.Constant;
import christmas.domain.menu.Category;
import christmas.domain.menu.Menu;
import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;
import java.util.Map;
import java.util.Map.Entry;

public class Order {
    private final Map<Menu, Integer> orderMenu;

    public Order(Map<Menu, Integer> orderMenu) {
        validate(orderMenu);
        this.orderMenu = orderMenu;
    }

    private void validate(Map<Menu, Integer> orderMenu) {
        validateCategory(orderMenu);
        validateTotalCount(orderMenu);
    }

    private void validateCategory(Map<Menu, Integer> orderMenu) {
        boolean onlyDessert = orderMenu.keySet()
                .stream()
                .map(Menu::category)
                .allMatch(Category::isBeverage);
        if (onlyDessert) {
            throw new PlannerException(ErrorMessage.INVALID_ORDER_MESSAGE);
        }
    }

    private void validateTotalCount(Map<Menu, Integer> orderMenu) {
        int sum = orderMenu.values().stream().mapToInt(Integer::intValue).sum();
        if (sum > Constant.MAX_ORDER_COUNT) {
            throw new PlannerException(ErrorMessage.INVALID_ORDER_MESSAGE);
        }
    }

    public int totalPrice() {
        return orderMenu.entrySet().stream()
                .mapToInt(this::itemPrice)
                .sum();
    }

    private int itemPrice(Entry<Menu, Integer> item) {
        return item.getKey().price() * item.getValue();
    }

    public int countByCategory(Category category) {
        return (int) orderMenu.keySet().stream()
                .filter(key -> category.equals(key.category()))
                .mapToInt(orderMenu::get)
                .sum();
    }
}
