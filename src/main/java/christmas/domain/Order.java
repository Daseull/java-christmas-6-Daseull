package christmas.domain;

import christmas.domain.event.Event;
import christmas.domain.event.Giveaway;
import christmas.domain.menu.Category;
import christmas.domain.menu.Menu;
import christmas.dto.MenuCount;
import christmas.exception.ErrorMessage;
import christmas.exception.PromotionException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class Order {
    private final Menus menus;
    private final Date date;

    public Order(Date date, Menus menus) {
        validateCategory(menus);
        this.date = date;
        this.menus = menus;
    }

    public int getDate() {
        return date.getDate();
    }

    public Map<Menu, Integer> getOrderMenu() {
        return menus.getMenus();
    }

    private void validateCategory(Menus menus) {
        if (menus.isAllInCategory(Category.BEVERAGE)) {
            throw new PromotionException(ErrorMessage.INVALID_ORDER_MESSAGE);
        }
    }

    public int totalAmount() {
        return menus.totalAmount();
    }

    public Optional<MenuCount> giveGiveaway() {
        Giveaway giveaway = (Giveaway) Event.GIVEAWAY.policy();
        return giveaway.giveGiveaway(date, menus);
    }

    public Map<Event, Integer> benefitDetails() {
        return Arrays.stream(Event.values())
                .map(event -> new SimpleEntry<>(event, event.policy().amount(date, menus)))
                .filter(entry -> entry.getValue() < 0)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (x, y) -> y, LinkedHashMap::new));
    }

    public int totalBenefit() {
        return Arrays.stream(Event.values())
                .mapToInt(event -> event.policy().amount(date, menus))
                .sum();
    }

    public int totalDiscount() {
        return Arrays.stream(Event.values())
                .filter(event -> event != Event.GIVEAWAY)
                .mapToInt(event -> event.policy().amount(date, menus))
                .sum();
    }

    public int finalAmount() {
        return totalAmount() - totalDiscount();
    }
}
