package christmas.controller;

import christmas.domain.Date;
import christmas.domain.Order;
import christmas.domain.OrderMenu;
import christmas.domain.event.Giveaway;
import christmas.exception.PlannerException;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.Parser;
import java.util.List;

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

    //TODO
    private void showPlan(Date date, Order order) {
        outputView.printPlanHeader();
        showOrder(order);
        outputView.printTotalAmount(order.totalPrice());
        showGiveaway(date, order);
    }

    private void showOrder(Order order) {
        List<MenuCount> menuCounts = toMenuCount(order);
        outputView.printOrder(menuCounts);
    }

    private void showGiveaway(Date date, Order order) {
        Giveaway giveaway = new Giveaway();
        outputView.printGiveaway(giveaway.receiveGiveaway(date, order));
    }

    private List<MenuCount> toMenuCount(Order order) {
        return order.getOrderMenu().entrySet()
                .stream()
                .map(entry -> new MenuCount(entry.getKey().description(), entry.getValue()))
                .toList();
    }


}
