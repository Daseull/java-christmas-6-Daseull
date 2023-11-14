package christmas.view;

import static christmas.view.Parser.parseDate;
import static christmas.view.Parser.parseMenus;

import camp.nextstep.edu.missionutils.Console;
import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;
import java.util.List;

public class InputView {
    private static final String DATE_PATTERN = "[\\d]{1,2}";
    private static final String MENUS_PATTERN = "(([가-힣]+)-([\\d]{1,2}),)*([가-힣]+)-([\\d]{1,2})";

    public int readVisitDate() {
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        String date = Console.readLine();
        validateDateInput(date);
        return parseDate(date);
    }

    private void validateDateInput(String input) {
        if (input.matches(DATE_PATTERN)) {
            return;
        }
        throw new PlannerException(ErrorMessage.INVALID_DATE_MESSAGE);
    }

    public List<String> readMenus() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        String menus = Console.readLine().trim();
        validateMenuInput(menus);
        return parseMenus(menus, ",");
    }

    private void validateMenuInput(String menus) {
        if (menus.matches(MENUS_PATTERN)) {
            return;
        }
        throw new PlannerException(ErrorMessage.INVALID_ORDER_MESSAGE);
    }
}
