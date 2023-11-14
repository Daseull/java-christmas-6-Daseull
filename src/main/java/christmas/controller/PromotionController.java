package christmas.controller;

import christmas.domain.Badge;
import christmas.domain.Date;
import christmas.domain.Order;
import christmas.domain.OrderMenu;
import christmas.domain.event.Event;
import christmas.exception.PlannerException;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.Parser;
import java.util.List;
import java.util.Map;

public class PromotionController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private Date date;
    private Order order;

    public void run() {
        date = askVisitDate();
        order = askMenus();
        showPlan();
        showBadge();
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

    private Order askMenus() {
        while (true) {
            try {
                return createOrder(inputView.readMenus());
            } catch (PlannerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Order createOrder(List<String> menus) {
        OrderMenu orderMenu = new OrderMenu();
        menus.stream()
                .map(menu -> Parser.parseMenu(menu, "-"))
                .forEach(menuCount -> orderMenu.add(menuCount.menu(), menuCount.count()));
        return new Order(orderMenu.getOrderMenu());
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
        outputView.printTotalAmount(order.totalPrice());
    }

    private void showOrder() {
        List<MenuCount> menuCounts = toMenuCount();
        outputView.printOrder(menuCounts);
    }

    private void showGiveaway() {
        outputView.printGiveaway(Event.giveGiveaway(date, order));
    }

    private void showBenefitDetails() {
        Map<Event, Integer> details = Event.benefitDetails(date, order);

        List<DiscountAmount> discountAmounts = details.entrySet().stream()
                .map(DiscountAmount::apply)
                .toList();
        outputView.printBenefitDetails(discountAmounts);
    }

    private void showTotalBenefit() {
        int totalBenefit = Event.totalBenefit(date, order);
        outputView.printTotalBenefit(totalBenefit);
    }

    private void showFinalAmount() {
        int discountAmount = Event.totalDiscount(date, order);
        outputView.printFinalAmount(order.totalPrice() + discountAmount);
    }

    private List<MenuCount> toMenuCount() {
        return order.getOrderMenu().entrySet()
                .stream()
                .map(entry -> new MenuCount(entry.getKey().description(), entry.getValue()))
                .toList();
    }

    private void showBadge() {
        Badge badge = Badge.fromBenefit(Event.totalBenefit(date, order));
        outputView.printBadge(badge.displayName());
    }
}
