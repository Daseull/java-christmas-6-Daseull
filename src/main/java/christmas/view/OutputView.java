package christmas.view;

import christmas.controller.DiscountAmount;
import christmas.controller.MenuCount;
import java.util.List;
import java.util.Optional;

public class OutputView {

    public void printPlanHeader() {
        System.out.println("12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!");
    }

    public void printOrder(List<MenuCount> order) {
        System.out.println("\n<주문 메뉴>");
        order.forEach(o -> printMenuCount(o.menu(), o.count()));
    }

    public void printTotalAmount(int amount) {
        System.out.println("\n<할인 전 총주문 금액>");
        System.out.printf("%,d원\n", amount);
    }

    public void printGiveaway(Optional<MenuCount> giveaway) {
        System.out.println("\n<증정 메뉴>");
        if (giveaway.isPresent()) {
            MenuCount menuCount = giveaway.get();
            printMenuCount(menuCount.menu(), menuCount.count());
            return;
        }
        System.out.println("없음");
    }

    public void printBenefitDetails(List<DiscountAmount> discountAmounts) {
        System.out.println("\n<혜택 내역>");
        if (discountAmounts.isEmpty()) {
            System.out.println("없음");
            return;
        }
        discountAmounts.forEach(da -> System.out.printf("%s: %,d원\n", da.name(), da.amount()));
    }

    public void printTotalBenefit(int totalBenefit) {
        System.out.println("\n<총혜택 금액>");
        System.out.printf("%,d원\n", totalBenefit);
    }

    private void printMenuCount(String menu, int count) {
        System.out.printf("%s %d개\n", menu, count);
    }
}
