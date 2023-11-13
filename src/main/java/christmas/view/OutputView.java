package christmas.view;

import christmas.controller.MenuCount;
import java.util.List;

public class OutputView {

    public void printPlanHeader() {
        System.out.println("12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!");
    }

    public void printOrder(List<MenuCount> order) {
        System.out.println("\n<주문 메뉴>");
        order.forEach(o -> System.out.printf("%s %d개\n", o.menu(), o.count()));
    }
}
