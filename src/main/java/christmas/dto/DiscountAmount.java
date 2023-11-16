package christmas.dto;

import christmas.domain.event.Event;
import java.util.Map.Entry;

public record DiscountAmount(String name, int amount) {

    public static DiscountAmount apply(Entry<Event, Integer> entry) {
        return new DiscountAmount(entry.getKey().description(), entry.getValue());
    }
}
