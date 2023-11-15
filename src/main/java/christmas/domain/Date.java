package christmas.domain;

import christmas.exception.ErrorMessage;
import christmas.exception.PromotionException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class Date {
    public static final int EVENT_YEAR = 2023;
    public static final int EVENT_MONTH = 12;
    public static final int MIN_DATE = 1;
    public static final int MAX_DATE = 31;
    private final int date;

    public Date(int date) {
        validate(date);
        this.date = date;
    }

    public int getDate() {
        return date;
    }

    private void validate(int date) {
        if (date < MIN_DATE || date > MAX_DATE) {
            throw new PromotionException(ErrorMessage.INVALID_DATE_MESSAGE);
        }
    }

    public int dayFromDate(int other) {
        return date - other;
    }

    public DayOfWeek dayOfWeek() {
        return LocalDate.of(EVENT_YEAR, EVENT_MONTH, date).getDayOfWeek();
    }

    public boolean isIncluded(List<Integer> dates) {
        return dates.contains(date);
    }

    public boolean isInRange(int startInclusive, int endInclusive) {
        return startInclusive <= date && date <= endInclusive;
    }
}
