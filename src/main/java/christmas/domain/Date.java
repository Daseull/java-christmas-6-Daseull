package christmas.domain;

import christmas.Constant;
import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Date {
    private int date;

    public Date(int date) {
        validate(date);
        this.date = date;
    }

    private void validate(int date) {
        if (date < Constant.MIN_DATE || date > Constant.MAX_DATE) {
            throw new PlannerException(ErrorMessage.INVALID_DATE_MESSAGE);
        }
    }

    public DayOfWeek dayOfWeek() {
        return LocalDate.of(Constant.EVENT_YEAR, Constant.EVENT_MONTH, date).getDayOfWeek();
    }
}
