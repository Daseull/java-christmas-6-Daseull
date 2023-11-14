package christmas.controller;

import christmas.domain.Badge;
import christmas.domain.Date;
import christmas.domain.Menus;
import christmas.domain.Order;
import christmas.domain.event.Event;
import christmas.dto.DiscountAmount;
import christmas.dto.MenuCount;
import christmas.exception.PlannerException;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.Parser;
import java.util.List;
import java.util.Map;

public class PromotionController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private Order order;

    public void run() {
        this.order = createOrder();
        showPlan();
        showBadge();
    }

    private Order createOrder() {
        Date date = askVisitDate();
        while (true) {
            try {
                Menus menus = askMenus();
                return new Order(date, menus);
            } catch (PlannerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Date askVisitDate() {
        while (true) {
            try {
                return new Date(inputView.readVisitDate());
            } catch (PlannerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Menus askMenus() {
        while (true) {
            try {
                List<String> input = inputView.readMenus();
                Menus menus = new Menus();
                input.stream()
                        .map(menu -> Parser.parseMenu(menu, "-"))
                        .forEach(menuCount -> menus.add(menuCount.menu(), menuCount.count()));
                return menus;
            } catch (PlannerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showPlan() {
        outputView.printPlanHeader();
        showOrder();
        showTotalAmount();
        showGiveaway();
        showBenefitDetails();
        showTotalBenefit();
        showFinalAmount();
    }

    private void showTotalAmount() {
        outputView.printTotalAmount(order.totalAmount());
    }

    private void showOrder() {
        List<MenuCount> menuCounts = order.getOrderMenu().entrySet()
                .stream()
                .map(MenuCount::apply)
                .toList();
        outputView.printOrder(menuCounts);
    }

    private void showGiveaway() {
        outputView.printGiveaway(order.giveGiveaway());
    }

    private void showBenefitDetails() {
        Map<Event, Integer> details = order.benefitDetails();

        List<DiscountAmount> discountAmounts = details.entrySet().stream()
                .map(DiscountAmount::apply)
                .toList();
        outputView.printBenefitDetails(discountAmounts);
    }

    private void showTotalBenefit() {
        int totalBenefit = order.totalBenefit();
        outputView.printTotalBenefit(totalBenefit);
    }

    private void showFinalAmount() {
        outputView.printFinalAmount(order.finalAmount());
    }

    private void showBadge() {
        Badge badge = Badge.fromBenefit(order.totalBenefit());
        outputView.printBadge(badge.displayName());
    }
}
