package christmas.domain.event;

import christmas.controller.MenuCount;
import christmas.domain.Date;
import christmas.domain.Order;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Event {
    D_DAY_DISCOUNT(new DDayDiscount(), "크리스마스 디데이 할인"),
    WEEKDAY_DISCOUNT(new WeekdayDiscount(), "평일 할인"),
    WEEKEND_DISCOUNT(new WeekendDiscount(), "주말 할인"),
    SPECIAL_DISCOUNT(new SpecialDiscount(), "특별 할인"),
    GIVEAWAY(new Giveaway(), "증정 이벤트");

    private final EventPolicy policy;
    private final String description;

    Event(EventPolicy policy, String description) {
        this.policy = policy;
        this.description = description;
    }

    public static Optional<MenuCount> giveGiveaway(Date date, Order order) {
        Giveaway giveaway = (Giveaway) GIVEAWAY.policy;
        return giveaway.giveGiveaway(date, order);
    }

    public static Map<Event, Integer> benefitDetails(Date date, Order order) {
        return Arrays.stream(values())
                .map(event -> new SimpleEntry<>(event, event.policy.amount(date, order)))
                .filter(entry -> entry.getValue() < 0)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (x, y) -> y, LinkedHashMap::new));
    }

    public static int totalBenefit(Date date, Order order) {
        return Arrays.stream(values())
                .mapToInt(event -> event.policy.amount(date, order))
                .sum();
    }

    public static int totalDiscount(Date date, Order order) {
        return Arrays.stream(values())
                .filter(event -> event != GIVEAWAY)
                .mapToInt(event -> event.policy.amount(date, order))
                .sum();
    }

    public String description() {
        return description;
    }
}
