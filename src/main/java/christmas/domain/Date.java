package christmas.domain;

import christmas.Constant;
import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;

public class Date {
    private int date;

    public Date(int date) {
        validate(date);
        this.date = date;
    }

    private void validate(int date){
        if(date < Constant.MIN_DATE || date > Constant.MAX_DATE){
            throw new PlannerException(ErrorMessage.INVALID_DATE_MESSAGE);
        }
    }
}
