package christmas.view;

import christmas.dto.DiscountAmount;
import christmas.dto.MenuCount;
import christmas.exception.PromotionException;
import java.util.List;
import java.util.Optional;

public class OutputView {

    public void printPlanHeader(int date) {
        System.out.printf("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n", date);
    }

    public void printOrder(List<MenuCount> order) {
        System.out.println("\n<주문 메뉴>");
        order.forEach(this::printMenuCount);
    }

    public void printTotalAmount(int amount) {
        System.out.println("\n<할인 전 총주문 금액>");
        System.out.printf("%,d원\n", amount);
    }

    public void printGiveaway(Optional<MenuCount> giveaway) {
        System.out.println("\n<증정 메뉴>");
        if (giveaway.isPresent()) {
            printMenuCount(giveaway.get());
            return;
        }
        printNone();
    }

    public void printBenefitDetails(List<DiscountAmount> discountAmounts) {
        System.out.println("\n<혜택 내역>");
        if (discountAmounts.isEmpty()) {
            printNone();
            return;
        }
        discountAmounts.forEach(da -> System.out.printf("%s: %,d원\n", da.name(), da.amount()));
    }

    public void printTotalBenefit(int totalBenefit) {
        System.out.println("\n<총혜택 금액>");
        printAmount(totalBenefit);
    }

    public void printFinalAmount(int finalAMount) {
        System.out.println("\n<할인 후 예상 결제 금액>");
        printAmount(finalAMount);
    }

    public void printBadge(String displayName) {
        System.out.println("\n<12월 이벤트 배지>");
        System.out.println(displayName);
    }

    public void printErrorMessage(PromotionException e){
        System.out.println(e.getMessage());
    }


    private void printNone() {
        System.out.println("없음");
    }

    private void printMenuCount(MenuCount menuCount) {
        System.out.printf("%s %d개\n", menuCount.menu(), menuCount.count());
    }

    private void printAmount(int amount) {
        System.out.printf("%,d원\n", amount);
    }
}
