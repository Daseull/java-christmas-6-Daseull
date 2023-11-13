package christmas.view;

import static christmas.view.Parser.parseDate;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public int readVisitDate() {
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        return parseDate(Console.readLine());
    }
}
