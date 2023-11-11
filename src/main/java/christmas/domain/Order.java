package christmas.domain;

import christmas.Constant;
import christmas.domain.menu.Category;
import christmas.domain.menu.Menu;
import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;
import java.util.Map;

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
                .allMatch(Category::isDessert);
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
}
