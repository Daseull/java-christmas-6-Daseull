package christmas.controller;

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

    public void run() {
        Date date = askVisitDate();
        Order order = askMenus();
        showPlan(date, order);
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

    private void showPlan(Date date, Order order) {
        outputView.printPlanHeader();
        showOrder(order);
        showTotalAmount(order);
        showGiveaway(date, order);
        showBenefitDetails(date, order);
        showTotalBenefit(date, order);
        showFinalAmount(date, order);
    }

    private void showTotalAmount(Order order){
        outputView.printTotalAmount(order.totalPrice());
    }

    private void showOrder(Order order) {
        List<MenuCount> menuCounts = toMenuCount(order);
        outputView.printOrder(menuCounts);
    }

    private void showGiveaway(Date date, Order order) {
        outputView.printGiveaway(Event.giveGiveaway(date, order));
    }

    private void showBenefitDetails(Date date, Order order) {
        Map<Event, Integer> details = Event.benefitDetails(date, order);

        List<DiscountAmount> discountAmounts = details.entrySet().stream()
                .map(DiscountAmount::apply)
                .toList();
        outputView.printBenefitDetails(discountAmounts);
    }

    private void showTotalBenefit(Date date, Order order) {
        int totalBenefit = Event.totalBenefit(date, order);
        outputView.printTotalBenefit(totalBenefit);
    }

    private void showFinalAmount(Date date, Order order) {
        int discountAmount = Event.totalDiscount(date, order);
        outputView.printFinalAmount(order.totalPrice() + discountAmount);
    }

    private List<MenuCount> toMenuCount(Order order) {
        return order.getOrderMenu().entrySet()
                .stream()
                .map(entry -> new MenuCount(entry.getKey().description(), entry.getValue()))
                .toList();
    }
}
