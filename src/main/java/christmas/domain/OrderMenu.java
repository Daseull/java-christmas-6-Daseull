package christmas.domain;

import christmas.Constant;
import christmas.domain.menu.Menu;
import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;
import java.util.EnumMap;
import java.util.Map;

public class OrderMenu {
    private final Map<Menu, Integer> orderMenu = new EnumMap<>(Menu.class);

    public void add(String menu, int count) {
        validate(menu, count);
        orderMenu.put(Menu.fromDescription(menu), count);
    }

    private void validate(String menu, int count) {
        validateMenuExists(menu);
        validateCount(count);
        validateMenuNotDuplicated(menu);
    }

    private void validateMenuExists(String menu) {
        if (Menu.exists(menu)) {
            return;
        }
        throw new PlannerException(ErrorMessage.INVALID_ORDER_MESSAGE);
    }

    private void validateCount(int count) {
        if (count < Constant.MIN_MENU_COUNT) {
            throw new PlannerException(ErrorMessage.INVALID_ORDER_MESSAGE);
        }
    }

    private void validateMenuNotDuplicated(String menu) {
        if (orderMenu.containsKey(Menu.fromDescription(menu))) {
            throw new PlannerException(ErrorMessage.INVALID_ORDER_MESSAGE);
        }
    }
}
