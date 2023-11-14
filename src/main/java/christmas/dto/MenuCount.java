package christmas.dto;

import christmas.domain.menu.Menu;
import java.util.Map.Entry;

public record MenuCount(String menu, int count) {
    public static MenuCount apply(Entry<Menu, Integer> entry) {
        return new MenuCount(entry.getKey().description(), entry.getValue());
    }
}
