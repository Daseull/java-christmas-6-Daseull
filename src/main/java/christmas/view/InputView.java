package christmas.view;

import static christmas.view.Parser.parseDate;
import static christmas.view.Parser.parseMenus;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;

public class InputView {

    public int readVisitDate() {
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        return parseDate(Console.readLine());
    }

    public List<String> readMenus() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        return parseMenus(Console.readLine(), ",");
    }
}
