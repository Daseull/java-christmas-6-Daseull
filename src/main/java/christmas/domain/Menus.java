package christmas.domain;

import christmas.domain.menu.Category;
import christmas.domain.menu.Menu;
import christmas.exception.ErrorMessage;
import christmas.exception.PromotionException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

public class Menus {
    public static final int MIN_MENU_COUNT = 1;
    public static final int MAX_ORDER_COUNT = 20;
    private final Map<Menu, Integer> menus = new EnumMap<>(Menu.class);

    public Map<Menu, Integer> getMenus() {
        return menus;
    }

    public void add(String menu, int count) {
        validate(menu, count);
        menus.put(Menu.fromDescription(menu), count);
    }

    private void validate(String menu, int count) {
        validateMenuExists(menu);
        validateMenuNotDuplicated(menu);
        validateCount(count);
        validateTotalCount(count);
    }

    public boolean isAllInCategory(Category category) {
        return menus.keySet().stream()
                .map(Menu::category)
                .allMatch(c -> c.equals(category));
    }

    public int countByCategory(Category category) {
        return menus.keySet().stream()
                .filter(key -> category.equals(key.category()))
                .mapToInt(menus::get)
                .sum();
    }

    public int totalCount() {
        return menus.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int totalAmount() {
        return menus.entrySet().stream()
                .mapToInt(this::itemPrice)
                .sum();
    }

    private int itemPrice(Entry<Menu, Integer> item) {
        return item.getKey().price() * item.getValue();
    }

    private void validateMenuExists(String menu) {
        if (Menu.exists(menu)) {
            return;
        }
        throw new PromotionException(ErrorMessage.INVALID_ORDER_MESSAGE);
    }

    private void validateCount(int count) {
        if (count < MIN_MENU_COUNT) {
            throw new PromotionException(ErrorMessage.INVALID_ORDER_MESSAGE);
        }
    }

    private void validateMenuNotDuplicated(String menu) {
        if (menus.containsKey(Menu.fromDescription(menu))) {
            throw new PromotionException(ErrorMessage.INVALID_ORDER_MESSAGE);
        }
    }

    private void validateTotalCount(int count) {
        if (totalCount() + count > MAX_ORDER_COUNT) {
            throw new PromotionException(ErrorMessage.INVALID_ORDER_MESSAGE);
        }
    }
}
