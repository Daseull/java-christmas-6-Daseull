package christmas.domain.event;

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

    public String description() {
        return description;
    }

    public EventPolicy policy() {
        return policy;
    }
}
